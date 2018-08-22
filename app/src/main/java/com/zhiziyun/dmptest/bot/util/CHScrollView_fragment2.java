package com.zhiziyun.dmptest.bot.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import com.zhiziyun.dmptest.bot.ui.fragment.FriendsFragment;

public class CHScrollView_fragment2 extends HorizontalScrollView {

	FriendsFragment fragment;
	public CHScrollView_fragment2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}


	public CHScrollView_fragment2(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public CHScrollView_fragment2(Context context) {
		super(context);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		//进行触摸赋值
		if (fragment!=null) {
			fragment.mTouchView = this;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		//当前的CHSCrollView被触摸时，滑动其它
		if(fragment.mTouchView == this) {
			fragment.onScrollChanged(l, t, oldl, oldt);
		}else{
			super.onScrollChanged(l, t, oldl, oldt);
		}
	}

	public FriendsFragment getFragment() {
		return fragment;
	}

	public void setFragment(FriendsFragment fragment) {
		this.fragment = fragment;
	}
}
