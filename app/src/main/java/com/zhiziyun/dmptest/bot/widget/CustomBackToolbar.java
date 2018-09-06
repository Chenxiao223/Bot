package com.zhiziyun.dmptest.bot.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.util.IsEmpty;


/**
 * Created by MasterC on 2017/9/16.
 * Shanghai Deaon Information Technology Co.,Ltd
 * ***
 * 自定义Toolbar,包含主标题、左侧返回按键、左侧图片、右侧图片、左侧文字、右侧文字
 * ***
 * 图片优先级大于文字 当同一侧同时设置文字和图片时，只显示图片
 * ***
 * 返回按钮优先级最高
 * ***
 * 调用setOnClickListener可将所有控件设置监听，上级页面直接case R.id处理即可
 */

public class CustomBackToolbar extends LinearLayout {

    private Context context;
    private LinearLayout mBackLl;
    private TextView mMainTitleTv, mRightTv, mLeftTv;
    private ImageView mRightIv, mLeftIv;

    //左右两侧文字大小
    private int leftTvTextSize, rightTvTextSize;
    //左右两侧文字颜色
    private int leftTvTextColor, rightTvTextColor;
    //主标题内容 和  两侧标题内容
    private String titleString, leftString, rightString;
    //左右两侧ImageView图片
    private int leftBackground, rightBackground;
    //是否显示back
    private boolean isBack;


    public CustomBackToolbar(Context context) {
        this(context, null);

    }

    public CustomBackToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.widget_custombacktoolbar, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomBackToolbar);
        leftTvTextSize = typedArray.getDimensionPixelSize(R.styleable.CustomBackToolbar_leftTextSize,
                getResources().getDimensionPixelSize(R.dimen.text_sp_16));
        rightTvTextSize = typedArray.getDimensionPixelSize(R.styleable.CustomBackToolbar_rightTextSize,
                getResources().getDimensionPixelSize(R.dimen.text_sp_16));
        leftTvTextColor = typedArray.getColor(R.styleable.CustomBackToolbar_leftTextColor,
                getResources().getColor(R.color.white));
        rightTvTextColor = typedArray.getColor(R.styleable.CustomBackToolbar_rightTextColor,
                getResources().getColor(R.color.white));
        titleString = typedArray.getString(R.styleable.CustomBackToolbar_titleText);
        leftString = typedArray.getString(R.styleable.CustomBackToolbar_leftTextString);
        rightString = typedArray.getString(R.styleable.CustomBackToolbar_rightTextString);
        leftBackground = typedArray.getResourceId(R.styleable.CustomBackToolbar_leftIvBackground,
                R.drawable.account);
        rightBackground = typedArray.getResourceId(R.styleable.CustomBackToolbar_rightIvBackground,
                R.drawable.account);
        isBack = typedArray.getBoolean(R.styleable.CustomBackToolbar_back, false);
        typedArray.recycle();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLeftTv = (TextView) findViewById(R.id.tv_toolbar_left);
        mRightTv = (TextView) findViewById(R.id.tv_toolbar_right);
        mBackLl = (LinearLayout) findViewById(R.id.ll_toolbar_back);
        mMainTitleTv = (TextView) findViewById(R.id.toolbar_title_tv);
        mRightIv = (ImageView) findViewById(R.id.iv_toolbar_right);
        mLeftIv = (ImageView) findViewById(R.id.iv_toolbar_left);
        initComponent();
    }


    public void initComponent() {
        //返回按钮点击事件
        mBackLl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
            }
        });
        if (!IsEmpty.string(titleString)) {
            mMainTitleTv.setText(titleString);
        }
        if (!IsEmpty.string(leftString)) {
            mLeftTv.setText(leftString);
            mLeftTv.setTextColor(leftTvTextColor);
            mLeftTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTvTextSize);
            mLeftTv.setVisibility(VISIBLE);
            mLeftIv.setVisibility(GONE);
        }
        if (!IsEmpty.string(rightString)) {
            mRightTv.setText(rightString);
            mRightTv.setTextColor(rightTvTextColor);
            mRightTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTvTextSize);
            mRightTv.setVisibility(VISIBLE);
            mRightIv.setVisibility(GONE);
        }
        if (leftBackground != R.drawable.account) {
            mLeftIv.setImageResource(leftBackground);
            mLeftIv.setVisibility(VISIBLE);
            mLeftTv.setVisibility(GONE);
        }
        if (rightBackground != R.drawable.account) {
            mRightIv.setImageResource(rightBackground);
            mRightIv.setVisibility(VISIBLE);
            mRightTv.setVisibility(GONE);
        }
        if (isBack) {
            mBackLl.setVisibility(VISIBLE);
            mLeftIv.setVisibility(GONE);
            mLeftTv.setVisibility(GONE);
        } else {
            mBackLl.setVisibility(GONE);
        }
    }

    public void setTvMainTitleText(String str) {
        mMainTitleTv.setText(str);
    }


    public void addOnClickListener(OnClickListener onClickListener) {
        mLeftTv.setOnClickListener(onClickListener);
        mRightTv.setOnClickListener(onClickListener);
        mRightIv.setOnClickListener(onClickListener);
        mLeftIv.setOnClickListener(onClickListener);
    }

    //以下为3.2.5使用接口
    public LinearLayout getmBackLl() {
        return mBackLl;
    }

    public TextView getmRightTv() {
        return mRightTv;
    }

    public TextView getmLeftTv() {
        return mLeftTv;
    }

    public ImageView getmRightIv() {
        return mRightIv;
    }

    public ImageView getmLeftIv() {
        return mLeftIv;
    }

    public void setmLeftIv(ImageView mLeftIv) {
        this.mLeftIv = mLeftIv;
    }

}
