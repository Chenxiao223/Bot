package com.zhiziyun.dmptest.bot.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import com.zhiziyun.dmptest.bot.ui.activity.OriginalityListActivity;

//activity专用
public class CHScrollView_Activity extends HorizontalScrollView {

	OriginalityListActivity activity;
	public CHScrollView_Activity(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		activity = (OriginalityListActivity) context;
	}


	public CHScrollView_Activity(Context context, AttributeSet attrs) {
		super(context, attrs);
		activity = (OriginalityListActivity) context;
	}

	public CHScrollView_Activity(Context context) {
		super(context);
		activity = (OriginalityListActivity) context;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		//进行触摸赋值
		activity.mTouchView = this;
		return super.onTouchEvent(ev);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		//当当前的CHSCrollView被触摸时，滑动其它
		if (activity.mTouchView == this) {
			activity.onScrollChanged(l, t, oldl, oldt);
		} else {
			super.onScrollChanged(l, t, oldl, oldt);
		}
	}
}
