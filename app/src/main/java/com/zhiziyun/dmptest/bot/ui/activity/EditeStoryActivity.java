package com.zhiziyun.dmptest.bot.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.umeng.analytics.MobclickAgent;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.NoDoubleClickListener;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/11/15.
 * 编辑门店
 */

public class EditeStoryActivity extends BaseActivity implements View.OnClickListener, OnGetGeoCoderResultListener {
    private ImageView iv_back;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private ImageView requestLocButton;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private BitmapDescriptor mCurrentMarker;
    private float lon = 0;//经度
    private float lat = 0;//纬度
    private EditText et_floorArea, et_storeId;
    private LinearLayout traceroute_rootview;
    private SharedPreferences share;
    private Intent intent;
    GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
    private EditText et_text;
    private MyDialog dialog;
    private String m_city = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_edite_story);
        getPermissions();
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        intent = getIntent();
        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        traceroute_rootview = (LinearLayout) findViewById(R.id.traceroute_rootview);
        traceroute_rootview.setOnClickListener(this);
        lon = Float.parseFloat(intent.getStringExtra("lon"));
        lat = Float.parseFloat(intent.getStringExtra("lat"));
        et_storeId = (EditText) findViewById(R.id.et_storeId);
        et_floorArea = (EditText) findViewById(R.id.et_floorArea);
        String name = intent.getStringExtra("name");
        String area = intent.getStringExtra("area");
        et_storeId.setText(name);
        et_floorArea.setText("" + area);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        requestLocButton = (ImageView) findViewById(R.id.iv_localize);

        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;

        // 初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);

        et_text = (EditText) findViewById(R.id.et_text);
        //点击搜索键的监听
        et_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) et_text.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    EditeStoryActivity.this
                                            .getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    //实现自己的搜索逻辑
                    SearchButtonProcess();
                    return true;
                }
                return false;
            }
        });

        //按钮的一个监听，改变改变定位图标的模式
        View.OnClickListener btnClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                mBaiduMap
                        .setMyLocationConfigeration(new MyLocationConfiguration(
                                mCurrentMode, true, mCurrentMarker));

                mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
                mBaiduMap
                        .setMyLocationConfigeration(new MyLocationConfiguration(
                                mCurrentMode, true, mCurrentMarker));

            }
        };
        requestLocButton.setOnClickListener(btnClickListener);

        mMapView = (MapView) findViewById(R.id.bmapView); //找到我们的地图控件
        mBaiduMap = mMapView.getMap(); //获得地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL); //设置为普通模式的地图
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mLocClient = new LocationClient(this);  //定位用到的一个类
        mLocClient.registerLocationListener(myListener); //注册监听

        ///LocationClientOption类用来设置定位SDK的定位方式，
        LocationClientOption option = new LocationClientOption(); //以下是给定位设置参数
        option.setOpenGps(true); // 打开gps
        //就是这个方法设置为true，才能获取当前的位置信息
        option.setIsNeedAddress(true);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        //百度地图点击事件，点击可以获取坐标
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                lat = (float) latLng.latitude;
                lon = (float) latLng.longitude;
                //为了保证只能标记一个点，在标点之前先删除原先的点
                mBaiduMap.clear();
                //定义Maker坐标点
                LatLng point = new LatLng(latLng.latitude, latLng.longitude);
                //构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icom);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
                //在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
        // 隐藏百度地图logo
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }

        //点击完成
        findViewById(R.id.btn_complete).setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onMultiClick(View v) {
                complete();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                toFinish();
                finish();
                break;
            case R.id.traceroute_rootview:
                //让软键盘隐藏
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
                break;
        }
    }

    //点击完成
    public void complete() {
        if (lat != 0 && lon != 0 && !TextUtils.isEmpty(et_floorArea.getText().toString())
                && !TextUtils.isEmpty(et_storeId.getText().toString())) {
            //半径必须大于3米小于100米
            if ((double) Float.parseFloat(et_floorArea.getText().toString()) >= 3 && (double) Float.parseFloat(et_floorArea.getText().toString()) <= 100) {
                dialog = MyDialog.showDialog(this);
                dialog.show();
                //修改门店接口
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final JSONObject json = new JSONObject();
                            json.put("siteId", share.getString("siteid", ""));
                            json.put("id", intent.getStringExtra("id"));
                            json.put("name", et_storeId.getText().toString());
                            //先根据半径计算出面积
                            double area = Math.PI * Double.parseDouble(et_floorArea.getText().toString()) * Double.parseDouble(et_floorArea.getText().toString());
                            json.put("area", (int) Math.round(area));//将面积四舍五入取整
                            json.put("longitude", Float.parseFloat(new DecimalFormat(".00").format(lon)));
                            json.put("latitude", Float.parseFloat(new DecimalFormat(".00").format(lat)));
                            OkHttpClient client = new OkHttpClient();
                            String url = null;
                            try {
                                url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                            RequestBody body = RequestBody.create(mediaType, url);
                            final Request request = new Request.Builder()
                                    .url(BaseUrl.BaseTest + "store/edit")
                                    .post(body)
                                    .addHeader("content-type", "application/x-www-form-urlencoded")
                                    .build();

                            client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {

                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.body().string());
                                        if (jsonObject.get("msg").equals("编辑门店成功")) {
                                            handler.sendEmptyMessage(1);
                                            toFinish();
                                            finish();
                                        } else {
                                            handler.sendEmptyMessage(2);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } else {
                ToastUtils.showShort(this, "半径必须在3米到100米之间");
            }
        } else {
            ToastUtils.showShort(this, "请将数据填写完整");
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    ToastUtils.showShort(EditeStoryActivity.this, "编辑成功");
                    dialog.dismiss();
                    break;
                case 2:
                    ToastUtils.showShort(EditeStoryActivity.this, "编辑失败");
                    dialog.dismiss();
                    break;
            }
        }
    };

    boolean isFirstLoc = true; // 是否首次定位

    /**
     * 地址搜索返回结果
     */
    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            ToastUtils.showShort(this, "抱歉，未能找到结果");
            return;
        }
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
                .getLocation()));
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

    }

    /**
     * 发起地址搜索
     */
    public void SearchButtonProcess() {
        // Geo搜索
        mSearch.geocode(new GeoCodeOption().city(
                m_city).address(
                et_text.getText().toString()));
    }

    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            try {
                // map view 销毁后不在处理新接收的位置
                if (location == null || mMapView == null) {
                    return;
                }
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);
                if (isFirstLoc) {
                    isFirstLoc = false;
                    LatLng ll = new LatLng(Double.parseDouble(intent.getStringExtra("lat")),
                            Double.parseDouble(intent.getStringExtra("lon")));
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(ll).zoom(18.0f);
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                    //定义Maker坐标点
                    LatLng point = new LatLng(Double.parseDouble(intent.getStringExtra("lat")), Double.parseDouble(intent.getStringExtra("lon")));
                    //构建Marker图标
                    BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icom);
                    //构建MarkerOption，用于在地图上添加Marker
                    OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
                    //在地图上添加Marker，并显示
                    mBaiduMap.addOverlay(option);
                    m_city = location.getCity().toLowerCase();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            // 退出时销毁定位
            mLocClient.stop();
            mSearch.destroy();
            // 关闭定位图层
            mBaiduMap.setMyLocationEnabled(false);
            mMapView.onDestroy();
            mMapView = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        MobclickAgent.onResume(this);
    }

    public void getPermissions() {
        try {
            //判断是否为android6.0系统版本，如果是，需要动态添加权限
            if (Build.VERSION.SDK_INT >= 23) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ToastUtils.showShort(this, "没有权限,请手动开启定位权限");
                    // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
                    ActivityCompat.requestPermissions(EditeStoryActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE}, 2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                } else {
                    // 没有获取到权限，做特殊处理
                    ToastUtils.showShort(this, "获取位置权限失败，请手动开启");
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onBackPressed() {
        toFinish();
        finish();
    }

    //清空内存
    private void toFinish() {
        try {
            mLocationClient = null;
            requestLocButton = null;
            mCurrentMode = null;
            myListener = null;
            mCurrentMarker = null;
            lon = 0;
            lat = 0;
            et_floorArea = null;
            et_storeId = null;
            traceroute_rootview = null;
            share = null;
            intent = null;
            et_text = null;
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
