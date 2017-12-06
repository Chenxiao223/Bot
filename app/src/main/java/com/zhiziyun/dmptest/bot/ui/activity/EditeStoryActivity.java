package com.zhiziyun.dmptest.bot.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

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
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.zhiziyun.dmptest.bot.R;
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

public class EditeStoryActivity extends Activity implements View.OnClickListener {
    private ImageView iv_back;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private Button requestLocButton;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_edite_story);
        initView();
    }

    private void initView() {
        intent = getIntent();
        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        traceroute_rootview = findViewById(R.id.traceroute_rootview);
        traceroute_rootview.setOnClickListener(this);
        lon = Float.parseFloat(intent.getStringExtra("lon"));
        lat = Float.parseFloat(intent.getStringExtra("lat"));
        et_storeId = findViewById(R.id.et_storeId);
        et_floorArea = findViewById(R.id.et_floorArea);
        String name = intent.getStringExtra("name");
        String area = intent.getStringExtra("area");
        et_storeId.setText(name);
        et_storeId.setSelection(name.length());
        et_floorArea.setText(area);
        et_floorArea.setSelection(area.length());
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        requestLocButton = (Button) findViewById(R.id.btn);

        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        requestLocButton.setText("普通");

        //按钮的一个监听，改变改变定位图标的模式
        View.OnClickListener btnClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (mCurrentMode) {
                    case NORMAL: //正常模式
                        requestLocButton.setText("跟随");
                        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                        mBaiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker));

                        break;
                    case COMPASS:  //罗盘模式
                        requestLocButton.setText("普通");
                        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
                        mBaiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker));
                        break;
                    case FOLLOWING: //跟随模式
                        requestLocButton.setText("罗盘");
                        mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
                        mBaiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker));
                        break;

                    default:
                        break;
                }

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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
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
    public void complete(View view) {
        if (lat != 0 && lon != 0 && !TextUtils.isEmpty(et_floorArea.getText().toString())
                && !TextUtils.isEmpty(et_storeId.getText().toString())) {
            //面积必须大于100小于三万
            if ((double) Float.parseFloat(et_floorArea.getText().toString()) >= 100 && (double) Float.parseFloat(et_floorArea.getText().toString()) <= 30000) {
                //修改门店接口
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final JSONObject json = new JSONObject();
                            json.put("siteId", share.getString("siteid", ""));
                            json.put("id", intent.getStringExtra("id"));
                            json.put("name", et_storeId.getText().toString());
                            json.put("area", (int) Float.parseFloat(et_floorArea.getText().toString()));
                            json.put("longitude", Float.parseFloat(new DecimalFormat(".000").format(lon)));
                            json.put("latitude", Float.parseFloat(new DecimalFormat(".000").format(lat)));
                            Log.i("jsons", json.toString());
                            OkHttpClient client = new OkHttpClient();
                            String url = null;
                            try {
                                url = "agentId=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                            RequestBody body = RequestBody.create(mediaType, url);
                            final Request request = new Request.Builder()
                                    .url("http://dmptest.zhiziyun.com/api/v1/store/edit.action")
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
                                        if (jsonObject.get("msg").equals("编辑门店成功")){
                                            handler.sendEmptyMessage(1);
                                            finish();
                                        }else{
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
                Toast.makeText(this, "面积必须在1百到3万之间", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(EditeStoryActivity.this, "请将数据填写完整", Toast.LENGTH_SHORT).show();
        }
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Toast.makeText(EditeStoryActivity.this, "编辑成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(EditeStoryActivity.this, "编辑失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    boolean isFirstLoc = true; // 是否首次定位

    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
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
            }
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
