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
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
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
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.umeng.analytics.MobclickAgent;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.baidumaputil.PoiOverlay;
import com.zhiziyun.dmptest.bot.util.CustomDialog;
import com.zhiziyun.dmptest.bot.util.EditDialog;
import com.zhiziyun.dmptest.bot.util.NoDoubleClickListener;
import com.zhiziyun.dmptest.bot.util.ToastUtils;


/**
 * Created by Administrator on 2017/11/15.
 * 添加门店
 */

public class AddStoryActivity extends BaseActivity implements View.OnClickListener, OnGetGeoCoderResultListener, OnGetPoiSearchResultListener {
    public static AddStoryActivity addStoryActivity;
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
    private EditText et_storeId, et_text;
    private LinearLayout traceroute_rootview;
    private SharedPreferences share;
    private String m_city = "";
    private PoiSearch mPoiSearch = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_add_story);
        getPermissions();
        initView();
    }

    private void initView() {
        addStoryActivity = this;
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        traceroute_rootview = (LinearLayout) findViewById(R.id.traceroute_rootview);
        traceroute_rootview.setOnClickListener(this);
        et_storeId = (EditText) findViewById(R.id.et_storeId);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        requestLocButton = (ImageView) findViewById(R.id.iv_localize);

        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;

        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);

        et_text = (EditText) findViewById(R.id.et_text);
        //点击搜索键的监听
        et_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) et_text.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    AddStoryActivity.this
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

        //点击下一步
        findViewById(R.id.btn_next).setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onMultiClick(View v) {
                //申请相机权限
                if (ContextCompat.checkSelfPermission(AddStoryActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(AddStoryActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {//如果有权限就直接执行
                    next();
                }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    next();
                } else {
                    ToastUtils.showShort(this, "请在应用管理中打开“相机”访问权限！");
                }
                break;
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

    //点击下一步
    public void next() {
        if (lat != 0 && lon != 0 && !TextUtils.isEmpty(et_storeId.getText().toString())) {
            //点击弹出对话框
            final CustomDialog customDialog = new CustomDialog(AddStoryActivity.this);
            customDialog.setTitle("消息提示");
            customDialog.setMessage("请选择绑定探针方式");
            customDialog.setYesOnclickListener("扫码", new CustomDialog.onYesOnclickListener() {
                @Override
                public void onYesClick() {
                    Intent it = new Intent();
                    it.setClass(AddStoryActivity.this, CaptureActivity.class);
                    //返回一个二维码的信息
                    startActivityForResult(it, 99);
                    customDialog.dismiss();
                }
            });
            customDialog.setNoOnclickListener("手动输入", new CustomDialog.onNoOnclickListener() {
                @Override
                public void onNoClick() {
                    //点击弹出对话框
                    final EditDialog editDialog = new EditDialog(AddStoryActivity.this);
                    editDialog.setTitle("请输入探针");
                    editDialog.setYesOnclickListener("确定", new EditDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick(String phone) {
                            if (TextUtils.isEmpty(phone)) {
                                ToastUtils.showShort(AddStoryActivity.this, "请输入探针");
                            } else {
                                Intent it = new Intent(AddStoryActivity.this, BindingActivity.class);
                                it.putExtra("lat", lat);
                                it.putExtra("lon", lon);
                                it.putExtra("mac", phone.toLowerCase());//将大写改成小写
                                it.putExtra("name", et_storeId.getText().toString());
                                startActivity(it);
                                toFinish();
                                AddStoryActivity.addStoryActivity.finish();
                                editDialog.dismiss();
                            }
                        }
                    });
                    editDialog.setNoOnclickListener("取消", new EditDialog.onNoOnclickListener() {
                        @Override
                        public void onNoClick() {
                            editDialog.dismiss();
                        }
                    });
                    editDialog.show();
                    customDialog.dismiss();
                }
            });
            customDialog.show();
            customDialog.setCancelable(false);//禁止点击回退键
        } else if (lat == 0 && lon == 0) {
            ToastUtils.showShort(this, "请在地图上标点");
        } else if (TextUtils.isEmpty(et_storeId.getText().toString())) {
            ToastUtils.showShort(this, "门店名称不能为空，且最多40个字符");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == RESULT_OK) {
            try {
                Bundle bundle = data.getExtras();
                //返回二维码扫描的信息
                String result = bundle.get("result").toString();
                Intent it = new Intent(this, BindingActivity.class);
                it.putExtra("lat", lat);
                it.putExtra("lon", lon);
                it.putExtra("mac", result);
                it.putExtra("name", et_storeId.getText().toString());
                startActivity(it);
                toFinish();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

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
        // Poi搜索
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city(m_city).keyword(et_text.getText().toString()).pageNum(0));
    }

    @Override
    public void onGetPoiResult(PoiResult result) {
        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(AddStoryActivity.this, "未找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            mBaiduMap.clear();
            PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result);
            overlay.addToMap();
            overlay.zoomToSpan();
            return;
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";
            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "找到结果";
            Toast.makeText(AddStoryActivity.this, strInfo, Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(this, poiDetailResult.getName() + ": " + poiDetailResult.getAddress(), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

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
                    LatLng ll = new LatLng(location.getLatitude(),
                            location.getLongitude());
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(ll).zoom(18.0f);
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                }
                m_city = location.getCity().toLowerCase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    ToastUtils.showShort(AddStoryActivity.this, String.valueOf(msg.obj));
                    break;
                case 2:

                    break;
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            // 退出时销毁定位
            mLocClient.stop();
            mPoiSearch.destroy();
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
        try {
            // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
            mMapView.onResume();
            MobclickAgent.onResume(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
            mMapView.onPause();
            MobclickAgent.onPause(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    ActivityCompat.requestPermissions(AddStoryActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE}, 2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        toFinish();
        finish();
    }

    //清空内存
    private void toFinish() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    mLocationClient = null;
                    mCurrentMode = null;
                    myListener = null;
                    mCurrentMarker = null;
                    lon = 0;
                    lat = 0;
                    et_storeId = null;
                    et_text = null;
                    traceroute_rootview = null;
                    share = null;
                    requestLocButton = null;
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
