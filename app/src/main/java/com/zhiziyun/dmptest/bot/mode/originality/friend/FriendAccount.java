package com.zhiziyun.dmptest.bot.mode.originality.friend;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.mode.originality.FriendMoneyCase;
import com.zhiziyun.dmptest.bot.network.subscriber.PureSubscriber;
import com.zhiziyun.dmptest.bot.ui.activity.BaseActivity;
import com.zhiziyun.dmptest.bot.ui.activity.TopupCenterActivity;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONObject;

import java.net.URLEncoder;

public class FriendAccount extends BaseActivity implements View.OnClickListener {

    private TextView mBalanceFriend, mMoneyDetail, balance_friend;
    private SharedPreferences share;
    private ImageView mImBack;
    private Button btn_commit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_account);
        mBalanceFriend = findViewById(R.id.balance_friend);
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        mMoneyDetail = findViewById(R.id.money_detail);
        mImBack = findViewById(R.id.im_back);
        balance_friend = findViewById(R.id.balance_friend);
        mMoneyDetail.setOnClickListener(this);
        mImBack.setOnClickListener(this);
        btn_commit = findViewById(R.id.btn_commit);
        btn_commit.setOnClickListener(this);
        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        //如果返回false就隐藏充值按钮
        if (!share.getBoolean("isAllowOnlinePay", false)) {
            btn_commit.setVisibility(View.GONE);
        }
        String id = share.getString("tencentid", "");
        if (!TextUtils.isEmpty(id)) {//有id才执行
            getData(id);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.money_detail:
                startActivity(new Intent(FriendAccount.this, FriendMoneyDetailActivity.class));
                break;
            case R.id.im_back:
                finish();
                break;
            case R.id.btn_commit:
                Intent intent = new Intent(FriendAccount.this, TopupCenterActivity.class);
                intent.putExtra("amount", balance_friend.getText().toString().replaceAll("￥", ""));
                intent.putExtra("flag", 123);//区别从哪里进来
                startActivity(intent);
                break;
        }
    }

    public void getData(String tencentId) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tencentId", tencentId);
            new FriendMoneyCase(1, URLEncoder.encode(Token.gettoken(), "utf-8"), jsonObject.toString()).execute(new PureSubscriber<BMoneyDetailResult>() {
                @Override
                public void onFailure(Throwable throwable) {

                }

                @Override
                public void onSuccess(BMoneyDetailResult data) {
                    mBalanceFriend.setText(data.getResponse().getBalance());
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
