package com.zhiziyun.dmptest.bot.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ListView;

/**
 * Created by ccwxf on 2016/6/30.
 */
public class SlideListView extends ListView {

    private float mTouchX;
    private float mTouchY;
    private float mMoveX;
    private float mMoveY;
    private int mTouchPosition;
    private float mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    public SlideListView(Context context) {
        super(context);
    }

    public SlideListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float dx = 0;
        float dy = 0;
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mTouchX = ev.getX();
                mTouchY = ev.getY();
                mMoveX = ev.getX();
                mMoveY = ev.getY();
                mTouchPosition = pointToPosition((int)ev.getX(), (int)ev.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                dx = ev.getX() - mMoveX;
                dy = ev.getY() - mMoveY;
                if(Math.abs(dx) > Math.abs(dy)){
                    //根据坐标点得到索引值
                    int position = pointToPosition((int)ev.getX(), (int)ev.getY());
                    if(mTouchPosition != ListView.INVALID_POSITION && position == mTouchPosition){
                        //得到内存中真实的Item
                        SlideItemView itemView = (SlideItemView) getChildAt(position - getFirstVisiblePosition());
                        itemView.scroll((int) dx);
                    }
                }
                mMoveX = ev.getX();
                mMoveY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                dx = ev.getX() - mTouchX;
                dy = ev.getY() - mTouchY;
                if(Math.abs(dx) > Math.abs(dy) && Math.abs(dx) >= mTouchSlop){
                    int position = pointToPosition((int)ev.getX(), (int)ev.getY());
                    if(mTouchPosition != ListView.INVALID_POSITION && position == mTouchPosition){
                        //得到真正在内存中的Item
                        SlideItemView itemView = (SlideItemView) getChildAt(position - getFirstVisiblePosition());
                        //根据当前scrollX以及dx判断是否显示正文内容
                        if (itemView.shouldShowContent((int) dx)){
                            itemView.showContent();
                        }else{
                            itemView.showMenu();
                        }
                    }else if(position != mTouchPosition){
                        SlideItemView itemView = (SlideItemView) getChildAt(mTouchPosition - getFirstVisiblePosition());
                        //根据当前scrollX以及dx判断是否显示正文内容
                        if (itemView.shouldShowContent((int) dx)){
                            itemView.showContent();
                        }else{
                            itemView.showMenu();
                        }
                    }
                }else{
                    try {
                        SlideItemView itemView = (SlideItemView) getChildAt(mTouchPosition - getFirstVisiblePosition());
                        //根据当前scrollX以及dx判断是否显示正文内容
                        if (itemView.shouldShowContent((int) dx)){
                            itemView.showContent();
                        }else{
                            itemView.showMenu();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if(mTouchPosition != ListView.INVALID_POSITION){
                    SlideItemView itemView = (SlideItemView) getChildAt(mTouchPosition - getFirstVisiblePosition());
                    itemView.showContent();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //宽度适配，改变ItemView的宽度
        SlideItemView.Width = width;
        for(int i = 0; i < getChildCount(); i++){
            SlideItemView item = (SlideItemView) getChildAt(i);
            item.resetWidth();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
