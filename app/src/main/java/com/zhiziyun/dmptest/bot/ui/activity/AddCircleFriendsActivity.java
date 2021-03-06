package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.RangeSeekBar;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;
import com.zhiziyun.dmptest.bot.view.PopWin_choose_city;
import com.zhiziyun.dmptest.bot.view.PopWin_industry;
import com.zhiziyun.dmptest.bot.wheelview.WheelView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//添加朋友圈活动（2）
public class AddCircleFriendsActivity extends BaseActivity implements View.OnClickListener {
    public static AddCircleFriendsActivity addCircleFriendsActivity;
    private PopWin_industry popWin_industry;
    private List<String> list_value = new ArrayList<>();
    private List<String> list_key = new ArrayList<>();
    public TextView tv_age, tv_sex, tv_educationTypes, tv_relationshipStatusTypes, tv_crowd, tv_approximateCount, tv_impression, tv_city, tv_scope;
    private JSONObject json;
    private String sex;
    private JSONArray json_educationTypes = new JSONArray();
    private JSONArray json_relationshipStatus = new JSONArray();
    private List<String> list_corwd = new ArrayList<>();
    private List<String> list_city = new ArrayList<>();
    private List<String> list_wifi_crowd = new ArrayList<>();
    private List<String> list_click_crowd = new ArrayList<>();
    public ArrayList<String> list_education = new ArrayList<>();
    public ArrayList<String> list_relationshipStatus = new ArrayList<>();
    private final int Flag_corwd = 1702;
    private JSONArray json_corwd = new JSONArray();
    private JSONArray json_city;
    private SharedPreferences share;
    private RangeSeekBar rangeSeekBar;
    private final int Flag_city = 8243;
    private final int Flag_educationTypes = 3827;
    private final int Flag_relationshipStatus = 8277;
    public JSONObject json_conditions;
    private EditText et_offer;
    private MyDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_circle_friends);
        addCircleFriendsActivity = this;
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        dialog = MyDialog.showDialog(this);
        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        tv_age = findViewById(R.id.tv_age);
        tv_sex = findViewById(R.id.tv_sex);
        tv_educationTypes = findViewById(R.id.tv_educationTypes);
        tv_relationshipStatusTypes = findViewById(R.id.tv_relationshipStatusTypes);
        tv_crowd = findViewById(R.id.tv_crowd);
        tv_approximateCount = findViewById(R.id.tv_approximateCount);
        tv_impression = findViewById(R.id.tv_impression);
        tv_city = findViewById(R.id.tv_city);
        et_offer = findViewById(R.id.et_offer);
        tv_scope = findViewById(R.id.tv_scope);

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);
        findViewById(R.id.rl_sex).setOnClickListener(this);
        findViewById(R.id.rl_educationTypes).setOnClickListener(this);
        findViewById(R.id.rl_relationshipStatusTypes).setOnClickListener(this);
        findViewById(R.id.rl_crowd).setOnClickListener(this);
        findViewById(R.id.rl_AudienceNum).setOnClickListener(this);
        findViewById(R.id.rl_city).setOnClickListener(this);
        getData();

        rangeSeekBar = (RangeSeekBar) findViewById(R.id.rangeSeekBar);
        rangeSeekBar.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {

            @Override
            public void onRangeChanged(float lowerRange, float upperRange) {
                tv_age.setText((int) lowerRange + "~" + (int) upperRange);
            }
        });

        if (getIntent().getIntExtra("flag", 0) != 0) {
            //如果数据是传递过来的就显示出来
            try {
                try {
                    if (!TextUtils.isEmpty(AddFriendsClubActivity.activity.friendInfo.getResponse().getTabItem2().getTargeting().getCityLevel())) {
                        tv_city.setText(getCity(AddFriendsClubActivity.activity.friendInfo.getResponse().getTabItem2().getTargeting().getCityLevel()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (AddFriendsClubActivity.activity.friendInfo.getResponse().getTabItem2().getTargeting().getAge().size() != 0) {
                        tv_age.setText(AddFriendsClubActivity.activity.friendInfo.getResponse().getTabItem2().getTargeting().getAge().get(0));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (AddFriendsClubActivity.activity.friendInfo.getResponse().getTabItem2().getTargeting().getGender().size() != 0) {
                        tv_sex.setText(getGender(AddFriendsClubActivity.activity.friendInfo.getResponse().getTabItem2().getTargeting().getGender().get(0)));
                        sex = AddFriendsClubActivity.activity.friendInfo.getResponse().getTabItem2().getTargeting().getGender().get(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (AddFriendsClubActivity.activity.friendInfo.getResponse().getTabItem2().getTargeting().getEducation().size() != 0) {
                        list_education.addAll(AddFriendsClubActivity.activity.friendInfo.getResponse().getTabItem2().getTargeting().getEducation());
                        tv_educationTypes.setText(list_education.size() + "个");
                        for (int i = 0; i < list_education.size(); i++) {
                            json_educationTypes.put(list_education.get(i));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (AddFriendsClubActivity.activity.friendInfo.getResponse().getTabItem2().getTargeting().getRelationshipStatus().size() != 0) {
                        list_relationshipStatus.addAll(AddFriendsClubActivity.activity.friendInfo.getResponse().getTabItem2().getTargeting().getRelationshipStatus());
                        tv_relationshipStatusTypes.setText(list_relationshipStatus.size() + "个");
                        for (int i = 0; i < list_relationshipStatus.size(); i++) {
                            json_relationshipStatus.put(list_relationshipStatus.get(i));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (!TextUtils.isEmpty(AddFriendsClubActivity.activity.friendInfo.getResponse().getTabItem2().getBidAmount() + "")) {
                        et_offer.setText(AddFriendsClubActivity.activity.friendInfo.getResponse().getTabItem2().getBidAmount() + "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    //city
                    List<Integer> list = AddFriendsClubActivity.activity.friendInfo.getResponse().getTabItem2().getTargeting().getGeoLocation();
                    json_city = new JSONArray();
                    for (int i = 0; i < list.size(); i++) {
                        json_city.put(list.get(i) + "");
                        list_city.add(list.get(i) + "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    //人群
                    List<String> list_corwds = AddFriendsClubActivity.activity.friendInfo.getResponse().getTabItem2().getTargeting().getCustomeAudiences();
                    tv_crowd.setText(list_corwds.size() + "个");
                    json_corwd = new JSONArray();
                    for (int i = 0; i < list_corwds.size(); i++) {
                        json_corwd.put(list_corwds.get(i));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //到店人群
                try {
                    list_corwd = AddFriendsClubActivity.activity.friendInfo.getResponse().getTabItem2().getTargeting().getProbeTagIds();
                    list_corwd.isEmpty();
                } catch (Exception e) {
                    list_corwd = new ArrayList<>();
                    e.printStackTrace();
                }
                //点击人群
                try {
                    list_click_crowd = AddFriendsClubActivity.activity.friendInfo.getResponse().getTabItem2().getTargeting().getAdclickTagIds();
                    list_click_crowd.isEmpty();
                } catch (Exception e) {
                    list_click_crowd = new ArrayList<>();
                    e.printStackTrace();
                }
                //wifi人群
                try {
                    list_wifi_crowd = AddFriendsClubActivity.activity.friendInfo.getResponse().getTabItem2().getTargeting().getWifiTagIds();
                    list_wifi_crowd.isEmpty();
                } catch (Exception e) {
                    list_wifi_crowd = new ArrayList<>();
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getGender(String str) {
        switch (str) {
            case "FEMALE":
                return "女性";
            case "MALE":
                return "男性";
        }
        return "";
    }

    public String getCity(String str) {
        switch (str) {
            case "CITY_LEVEL_CORE":
                tv_scope.setText("可出价范围：100-300元");
                return "核心城市";
            case "CITY_LEVEL_IMPORTANT":
                tv_scope.setText("可出价范围：60-200元");
                return "重点城市";
            case "CITY_LEVEL_OTHER":
                tv_scope.setText("可出价范围：30-200元");
                return "其他城市";
        }
        return "";
    }

    //页面跳转
    public void jump(String str) {
        if (list_city.isEmpty()) {//如果集合为空，说明是创建进来的；否则说明是编辑进来的
            Intent intent = new Intent(this, ChooseCityActivity.class);
            intent.putExtra("type", str);
            startActivityForResult(intent, Flag_city);
        } else {
            Intent intent = new Intent(this, ChooseCityActivity.class);
            intent.putExtra("type", str);
            intent.putExtra("flag", 123);
            intent.putStringArrayListExtra("list", (ArrayList<String>) list_city);
            startActivityForResult(intent, Flag_city);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_city:
                try {
                    //隐藏软键盘
                    InputMethodManager imm4 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm4.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    //让背景变暗
                    WindowManager.LayoutParams lp3 = getWindow().getAttributes();
                    lp3.alpha = 0.7f;
                    getWindow().setAttributes(lp3);
                    getWindow().setAttributes(lp3);
                    //弹出pop窗体
                    PopWin_choose_city popWin_city = new PopWin_choose_city(this, null, 0);
                    popWin_city.showAtLocation(findViewById(R.id.main_view), Gravity.BOTTOM, 0, 0);//125
                    //监听popwin是否关闭，关闭的话让背景恢复
                    popWin_city.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            WindowManager.LayoutParams lp3 = getWindow().getAttributes();
                            lp3.alpha = 1f;
                            getWindow().setAttributes(lp3);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_AudienceNum:
                getTargetingAudienceNum();
                break;
            case R.id.rl_crowd:
                if (list_corwd.isEmpty() && list_wifi_crowd.isEmpty() && list_click_crowd.isEmpty()) {//无值
                    Intent it = new Intent(AddCircleFriendsActivity.this, SeedsCrowdActivity.class);
                    startActivityForResult(it, Flag_corwd);
                } else {//有值的话将值传过去显示选中状态
                    Intent it = new Intent(AddCircleFriendsActivity.this, SeedsCrowdActivity.class);
                    it.putExtra("flag", 123);
                    it.putStringArrayListExtra("list", (ArrayList<String>) list_corwd);
                    it.putStringArrayListExtra("list_wifi", (ArrayList<String>) list_wifi_crowd);
                    it.putStringArrayListExtra("list_click", (ArrayList<String>) list_click_crowd);
                    startActivityForResult(it, Flag_corwd);
                }
                break;
            case R.id.rl_relationshipStatusTypes:
                try {
                    //通过判断list_relationshipStatus有没有值来确定要不要将值传入进入显示选中状态
                    if (list_relationshipStatus.isEmpty()) {//无值
                        Intent it_relationshipStatus = new Intent(this, FriendsChooseActivity.class);
                        it_relationshipStatus.putExtra("json", json.get("relationshipStatusTypes").toString());
                        it_relationshipStatus.putExtra("flag", Flag_relationshipStatus);
                        startActivityForResult(it_relationshipStatus, Flag_relationshipStatus);
                    } else {//有值的话将值传过去显示选中状态
                        Intent it_relationshipStatus = new Intent(this, FriendsChooseActivity.class);
                        it_relationshipStatus.putExtra("json", json.get("relationshipStatusTypes").toString());
                        it_relationshipStatus.putExtra("flag", Flag_relationshipStatus);
                        it_relationshipStatus.putExtra("flags", 123);
                        it_relationshipStatus.putStringArrayListExtra("list", (ArrayList<String>) list_relationshipStatus);
                        startActivityForResult(it_relationshipStatus, Flag_relationshipStatus);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_educationTypes:
                try {
                    //通过判断list_education有没有值来确定要不要将值传入进入显示选中状态
                    if (list_education.isEmpty()) {//无值
                        Intent it_educationTypes = new Intent(this, FriendsChooseActivity.class);
                        it_educationTypes.putExtra("json", json.get("educationTypes").toString());
                        it_educationTypes.putExtra("flag", Flag_educationTypes);
                        startActivityForResult(it_educationTypes, Flag_educationTypes);
                    } else {//有值的话将值传过去显示选中状态
                        Intent it_educationTypes = new Intent(this, FriendsChooseActivity.class);
                        it_educationTypes.putExtra("json", json.get("educationTypes").toString());
                        it_educationTypes.putExtra("flag", Flag_educationTypes);
                        it_educationTypes.putExtra("flags", 123);
                        it_educationTypes.putStringArrayListExtra("list", (ArrayList<String>) list_education);
                        startActivityForResult(it_educationTypes, Flag_educationTypes);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_sex:
                try {
                    getJson("genderTypes");
                    View outerView1 = LayoutInflater.from(this).inflate(R.layout.view_industry, null);
                    //滚轮
                    final WheelView wv1 = (WheelView) outerView1.findViewById(R.id.wv1);
                    wv1.setItems(list_value, 0);
                    TextView tv_ok = (TextView) outerView1.findViewById(R.id.tv_ok);
                    TextView tv_cancel = (TextView) outerView1.findViewById(R.id.tv_cancel);
                    //点击确定
                    tv_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            String mSelectDate = wv1.getSelectedItem();
                            tv_sex.setText(mSelectDate);
                            for (int i = 0; i < list_value.size(); i++) {
                                if (mSelectDate.equals(list_value.get(i))) {
                                    sex = list_key.get(i);
                                }
                            }
                            popWin_industry.dismiss();
                        }
                    });
                    //点击取消
                    tv_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            popWin_industry.dismiss();
                        }
                    });
                    //防止弹出两个窗口
                    if (popWin_industry != null && popWin_industry.isShowing()) {
                        return;
                    }

                    popWin_industry = new PopWin_industry(this, R.style.ActionSheetDialogStyle);
                    //将布局设置给Dialog
                    popWin_industry.setContentView(outerView1);
                    popWin_industry.show();//显示对话框
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_next:
                if (tv_city.getText().toString().equals("选择城市")) {
                    ToastUtils.showShort(this, "请选择城市类型");
                } else if (TextUtils.isEmpty(et_offer.getText().toString())) {
                    ToastUtils.showShort(this, "请输入预算");
                } else {
                    Intent intent = new Intent(AddCircleFriendsActivity.this, SubmitFriendActivitty.class);
                    Intent it = getIntent();
                    intent.putExtra("wechatActivityName", it.getStringExtra("wechatActivityName"));
                    intent.putExtra("productType", it.getStringExtra("productType"));
                    intent.putExtra("startDate", it.getStringExtra("startDate"));
                    intent.putExtra("endDate", it.getStringExtra("endDate"));
                    intent.putExtra("timeSeries", it.getStringExtra("timeSeries"));
                    intent.putExtra("dailyBudget", it.getStringExtra("dailyBudget"));
                    intent.putExtra("bidAmount", et_offer.getText().toString());
                    if (getIntent().getIntExtra("flag", 0) != 0) {
                        intent.putExtra("flag", 123);
                        intent.putExtra("wechatActivityId", getIntent().getStringExtra("wechatActivityId"));
                    }
                    startActivity(intent);
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //区分从哪个页面回传的数据
            case Flag_corwd://人群
                try {
                    if (!data.getStringArrayListExtra("list").isEmpty() || !data.getStringArrayListExtra("list_wifi").isEmpty()
                            || !data.getStringArrayListExtra("list_click").isEmpty()) {
                        //用集合来接收这个集合
                        list_corwd = data.getStringArrayListExtra("list");
                        list_wifi_crowd = data.getStringArrayListExtra("list_wifi");
                        list_click_crowd = data.getStringArrayListExtra("list_click");
                        List<String> list = new ArrayList<>();
                        list.addAll(list_corwd);
                        list.addAll(list_wifi_crowd);
                        list.addAll(list_click_crowd);
                        int num = list.size();
                        if (num != 0) {
                            json_corwd = new JSONArray();
                            tv_crowd.setText(num + "个");
                            for (int i = 0; i < num; i++) {
                                json_corwd.put(list.get(i));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Flag_city:
                try {
                    if (!data.getStringArrayListExtra("list").isEmpty()) {
                        //用集合来接收这个集合
                        list_city = data.getStringArrayListExtra("list");
                        json_city = new JSONArray();
                        for (int i = 0; i < list_city.size(); i++) {
                            json_city.put(list_city.get(i));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Flag_educationTypes:
                try {
                    if (!data.getStringArrayListExtra("list").isEmpty()) {
                        json_educationTypes = null;//为了防止第二次进去选中回来后叠加，在这里归零
                        json_educationTypes = new JSONArray();
                        //以后用集合来接收这个集合
                        list_education = data.getStringArrayListExtra("list");
                        int num = list_education.size();
                        tv_educationTypes.setText(num + "个");
                        for (int i = 0; i < num; i++) {
                            json_educationTypes.put(list_education.get(i));
                        }
                    } else {
                        json_educationTypes = new JSONArray();
                        tv_educationTypes.setText("不限");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case Flag_relationshipStatus:
                try {
                    if (!data.getStringArrayListExtra("list").isEmpty()) {
                        json_relationshipStatus = null;//为了防止第二次进去选中回来后叠加，在这里归零
                        json_relationshipStatus = new JSONArray();
                        //以后用集合来接收这个集合
                        list_relationshipStatus = data.getStringArrayListExtra("list");
                        int num = list_relationshipStatus.size();
                        tv_relationshipStatusTypes.setText(num + "个");
                        for (int i = 0; i < num; i++) {
                            json_relationshipStatus.put(list_relationshipStatus.get(i));
                        }
                    } else {
                        json_relationshipStatus = new JSONArray();
                        tv_relationshipStatusTypes.setText("不限");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void getData() {
        //微信朋友圈活动选项信息
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + "";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseZhang + "api/tencentWechatActivityApp/creativeWechatActivitySelectItem")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                json = null;
                                String str = response.body().string();
                                JSONObject jsonObject = new JSONObject(str);
                                json = new JSONObject(jsonObject.get("response").toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getJson(String strs) {
        try {
            list_key.clear();
            list_value.clear();
            list_key.add("不限");//这个值没用，自己写的
            list_value.add("不限");
            Map maps = (Map) JSON.parse(json.get(strs).toString());
            for (Object map : maps.entrySet()) {
                list_key.add(((Map.Entry) map).getKey().toString());
                list_value.add(((Map.Entry) map).getValue().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJson() {
        try {
            json_conditions = new JSONObject();
            if (tv_age.getText().toString().equals("不限")) {
                json_conditions.put("age", new JSONArray());
            } else {
                json_conditions.put("age", getJsonArrary(tv_age.getText().toString()));
            }
            if (tv_sex.getText().toString().equals("不限")) {
                json_conditions.put("gender", new JSONArray());
            } else {
                json_conditions.put("gender", getJsonArrary(sex));
            }
            if (tv_educationTypes.getText().toString().equals("不限")) {//学历
                json_conditions.put("education", new JSONArray());
            } else {
                json_conditions.put("education", json_educationTypes);
            }
            if (tv_relationshipStatusTypes.getText().toString().equals("不限")) {//婚姻状况
                json_conditions.put("relationshipStatus", new JSONArray());
            } else {
                json_conditions.put("relationshipStatus", json_relationshipStatus);
            }
            if (tv_city.getText().toString().equals("选择城市")) {
                json_conditions.put("geoLocation", "");
            } else {
                json_conditions.put("geoLocation", json_city);
            }
            if (tv_crowd.getText().toString().equals("请选择")) {//用户人群
                json_conditions.put("customeAudiences", new JSONArray());
            } else {
                json_conditions.put("customeAudiences", json_corwd);
            }
            if (!tv_city.getText().toString().equals("选择城市")) {
                if (tv_city.getText().toString().equals("核心城市")) {
                    json_conditions.put("cityLevel", "CITY_LEVEL_CORE");
                } else if (tv_city.getText().toString().equals("重点城市")) {
                    json_conditions.put("cityLevel", "CITY_LEVEL_IMPORTANT");
                } else if (tv_city.getText().toString().equals("其他城市")) {
                    json_conditions.put("cityLevel", "CITY_LEVEL_OTHER");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json_conditions;
    }

    public void getTargetingAudienceNum() {
        dialog.show();
        //微信朋友圈活动定向人数预估
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    json_conditions = new JSONObject();
                    if (tv_age.getText().toString().equals("不限")) {
                        json_conditions.put("age", new JSONArray());
                    } else {
                        json_conditions.put("age", getJsonArrary(tv_age.getText().toString()));
                    }
                    if (tv_sex.getText().toString().equals("不限")) {
                        json_conditions.put("gender", new JSONArray());
                    } else {
                        json_conditions.put("gender", getJsonArrary(sex));
                    }
                    if (tv_educationTypes.getText().toString().equals("不限")) {
                        json_conditions.put("education", new JSONArray());
                    } else {
                        json_conditions.put("education", json_educationTypes);
                    }
                    if (tv_relationshipStatusTypes.getText().toString().equals("不限")) {
                        json_conditions.put("relationshipStatus", new JSONArray());
                    } else {
                        json_conditions.put("relationshipStatus", json_relationshipStatus);
                    }
                    if (tv_city.getText().toString().equals("选择城市")) {
                        json_conditions.put("geoLocation", "");
                    } else {
                        json_conditions.put("geoLocation", json_city);
                    }
                    if (tv_crowd.getText().toString().equals("请选择")) {
                        json_conditions.put("customeAudiences", new JSONArray());
                    } else {
                        json_conditions.put("customeAudiences", json_corwd);
                    }
                    if (!tv_city.getText().toString().equals("选择城市")) {
                        if (tv_city.getText().toString().equals("核心城市")) {
                            json_conditions.put("cityLevel", "CITY_LEVEL_CORE");
                        } else if (tv_city.getText().toString().equals("重点城市")) {
                            json_conditions.put("cityLevel", "CITY_LEVEL_IMPORTANT");
                        } else if (tv_city.getText().toString().equals("其他城市")) {
                            json_conditions.put("cityLevel", "CITY_LEVEL_OTHER");
                        }
                    }
                    final JSONObject json = new JSONObject();
                    json.put("targeting", json_conditions);//定向条件
                    json.put("tencentId", share.getString("tencentid", ""));
//                    json.put("tencentId", "100000001");
                    OkHttpClient okHttpClient = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseZhang + "api/tencentWechatActivityApp/getTargetingAudienceNum")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String str = response.body().string();
                                JSONObject json = new JSONObject(str);
                                if (json.get("status").toString().equals("true")) {
                                    Message msg = new Message();
                                    msg.what = 1;
                                    msg.obj = json;
                                    handler.sendMessage(msg);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    try {
                        JSONObject j = (JSONObject) msg.obj;
                        JSONObject json = new JSONObject(j.get("response").toString());
                        tv_approximateCount.setText(json.get("approximateCount").toString());
                        tv_impression.setText(json.get("impression").toString());
                        dialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    public JSONArray getJsonArrary(String str) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(str);
        return jsonArray;
    }
}
