package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.CheckBoxAdapter;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.wifi_probe.WifiProbeManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2018/8/29.
 * 添加设备
 */

public class ChooseEquipmentActivity extends BaseActivity implements View.OnClickListener {
    private String m_mac = "";
    private MyDialog dialog;
    private ListView mWifi_list;
    //wifi探针
    private int WiFi_time = 30 * 1000;//30秒扫描一次
    private List<HashMap<String, Object>> mMacList = new ArrayList<>(); //存放mac的集合
    private List<String> listStr = new ArrayList<>();
    private CheckBoxAdapter cbAdapter;
    private WifiProbeManager mProbe;
    private Timer mTimer;
    private TextView tv_equipment, tv_num, tv_choose;
    private Button btn_commit;
    private boolean isChoose = true;
    private final int FLAG = 4322;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_equipment);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        dialog = MyDialog.showDialog(this);
        tv_equipment = findViewById(R.id.tv_equipment);
        tv_choose = findViewById(R.id.tv_choose);
        mProbe = new WifiProbeManager();
        btn_commit = findViewById(R.id.btn_commit);
        tv_num = findViewById(R.id.tv_num);
        btn_commit.setOnClickListener(this);
        tv_choose.setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);

        mWifi_list = (ListView) findViewById(R.id.list_wifi);
        cbAdapter = new CheckBoxAdapter(ChooseEquipmentActivity.this, mMacList);
        mWifi_list.setAdapter(cbAdapter);
        //点击列表
        mWifi_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBoxAdapter.ViewCache viewCache = (CheckBoxAdapter.ViewCache) view.getTag();
                viewCache.cb.toggle();
                mMacList.get(position).put("boolean", viewCache.cb.isChecked());
                cbAdapter.notifyDataSetChanged();
                if (viewCache.cb.isChecked()) {//被选中状态
                    listStr.add(mMacList.get(position).get("name").toString());
                } else {//从选中状态转化为未选中
                    listStr.remove(mMacList.get(position).get("name").toString());
                }
                tv_equipment.setText("已选择设备：" + listStr.size() + "个");
            }
        });

        if (!isWifiConnected(this)) {
            ToastUtils.showShort(this, "请连接wifi");
        } else {
            initScan();
        }
    }

    /**
     * 在一定时间内进行扫描（也就是读取文件夹内的mac地址）
     */
    private void initScan() {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                startScan();
            }
        }, 0, WiFi_time);
    }

    //获取SSID
    public String getSsid() {
        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        if (info != null && info.getMacAddress() != null) {
            return info.getSSID().replace("\"", "");//得到字符串去掉双引号
        }
        return "";
    }

    /**
     * 开始扫描 ， 如果wifi断开了数据就会没有了 ， 如果wifi地址变了内容也会变的
     */
    private void startScan() {
        mProbe.startScan(new WifiProbeManager.MacListListener() {
            @Override
            public void macList(final List<String> macList) {
                //因为在线程中进行扫描的，所以要切换到主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            List list = new ArrayList();
                            list.addAll(getNewList(macList, mMacList));
                            mMacList.clear();
                            if (!isChoose) {
                                listStr.clear();
                                listStr.addAll(list);
                            }
                            for (int i = 0; i < list.size(); i++) {
                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("name", list.get(i));
                                if (isChoose) {
                                    hashMap.put("boolean", false);
                                } else {
                                    hashMap.put("boolean", true);
                                }
                                mMacList.add(hashMap);
                            }
                            cbAdapter.notifyDataSetChanged();
                            Message msg = new Message();
                            msg.what = 1;
                            msg.obj = mMacList.size();
                            handler.sendMessage(msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    //去重,第一个参数是存在重复值的集合，第二个参数是得到的新的不存在重复的集合
    public List getNewList(List<String> list, List<HashMap<String, Object>> arr) {
        List ll = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (!arr.contains(list.get(i))) {
                ll.add(list.get(i));
            }
        }
        return ll;
    }


    public String getMac() {
        m_mac = "";
        for (int i = 0; i < mMacList.size(); i++) {
            m_mac += mMacList.get(i) + ",";
        }
        return m_mac;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    try {
                        tv_num.setText("已扫描设备：" + msg.obj + "个");
                        if (!isChoose) {
                            tv_equipment.setText("已选择设备：" + msg.obj + "个");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (!isWifiConnected(this)) {
            ToastUtils.showShort(this, "请连接wifi");
        }
    }

    //参数为true表示全选，为false表示全不选
    public void choose(boolean b) {
        listStr.clear();//无论全选还是全不选都要清空
        for (int i = 0; i < mMacList.size(); i++) {
            mMacList.get(i).put("boolean", b);
            if (b) {//如果是全选，将所有mac地址都添加进来
                listStr.add(mMacList.get(i).get("name").toString());
            }
        }
        tv_equipment.setText("已选择设备：" + listStr.size() + "个");
        cbAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_choose:
                if (isChoose) {
                    isChoose = false;
                    tv_choose.setText("全不选");
                    choose(true);
                } else {
                    isChoose = true;
                    tv_choose.setText("全选");
                    choose(false);
                }
                break;
            case R.id.iv_back:
                toFinish();
                finish();
                break;
            case R.id.btn_commit:
                if (isWifiConnected(this)) {//判断是否连接wifi
                    try {
                        if (listStr.isEmpty()) {
                            ToastUtils.showShort(this, "请选择设备");
                        } else {
                            Intent intent = new Intent();
                            intent.putStringArrayListExtra("list", (ArrayList<String>) listStr);
                            setResult(FLAG, intent);
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                } else {
                    ToastUtils.showShort(this, "请连接wifi");
                }
                break;
        }
    }

    //是否连接WIFI
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            mTimer.cancel();
        } catch (Exception e) {
            e.printStackTrace();
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
                    mMacList.clear();
                    m_mac = "";
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
