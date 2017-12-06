package com.zhiziyun.dmptest.bot.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by ccwxf on 2016/6/30.
 */
public class SlideItemView extends LinearLayout {
    //正文的宽度
    public static int Width;
    private View content;
    private View menu;
    private Scroller mScroller;
    private SlideListView listView;
    //Menu相比正文宽度的比例
    private float scale;

    public SlideItemView(Context context) {
        super(context);
        init();
    }

    public SlideItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        setOrientation(LinearLayout.HORIZONTAL);
        mScroller = new Scroller(getContext());
    }

    @Override
    public void computeScroll(){
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }

    public void setView(SlideListView listView, int contentId, int menuId, float menuScale){
        this.listView = listView;
        this.content = View.inflate(getContext(), contentId, null);
        this.menu = View.inflate(getContext(), menuId, null);
        this.scale = menuScale;
        LayoutParams param1 = new LayoutParams(Width, LayoutParams.MATCH_PARENT);
        addView(content, param1);
        LayoutParams param2 = new LayoutParams((int) (Width * menuScale), LayoutParams.MATCH_PARENT);
        addView(menu, param2);
    }

    public View getContent(){
        return content;
    }

    public View getMenu(){
        return menu;
    }

    public void showContent(){
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), -mScroller.getFinalX(), 0);
        invalidate();
    }

    public void showMenu(){
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), menu.getWidth() - mScroller.getFinalX(), 0);
        invalidate();
    }

    public boolean shouldShowContent(int dx){
        //初始化
        if(menu.getWidth() == 0){
            resetWidth();
        }
        if(dx > 0){
            //右滑，当滑过1/4的时候开始变化
            if(mScroller.getFinalX() < menu.getWidth() * 3 / 4){
                return true;
            }else{
                return false;
            }
        }else{
            //左滑，当滑过1/4的时候开始变化
            if(mScroller.getFinalX() < menu.getWidth() / 4){
                return true;
            }else{
                return false;
            }
        }
    }

    public void scroll(int dx){
        if(dx > 0){
            //右滑
            if(mScroller.getFinalX() > 0){
                if(dx > mScroller.getFinalX()){
                    mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), -mScroller.getFinalX(), 0);
                }else{
                    mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), -dx, 0);
                }
            }else{
                mScroller.setFinalX(0);
            }
            invalidate();
        }else{
            //左滑
            if(mScroller.getFinalX() < menu.getWidth()){
                if(mScroller.getFinalX() - dx > menu.getWidth()){
                    mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), menu.getWidth()- mScroller.getFinalX(), 0);
                }else{
                    mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), -dx, 0);
                }
            }else{
                mScroller.setFinalX(menu.getWidth());
            }
            invalidate();
        }
    }

    /**
     * 重设宽度，在ListView的onMeasure方法中调用。
     * 此方法是为了动态适配ListView的宽度，因为ListView的layout_width不一定等于MATCH_PARENT
     * 也可能是定值比如300dp
     */
    public void resetWidth(){
        ViewGroup.LayoutParams param1 = content.getLayoutParams();
        if(param1 == null){
            param1 = new LayoutParams(Width, LayoutParams.MATCH_PARENT);
        }else{
            param1.width = Width;
        }
        content.setLayoutParams(param1);
        ViewGroup.LayoutParams param2 = menu.getLayoutParams();
        if(param2 == null){
            param2 = new LayoutParams((int) (Width * scale), LayoutParams.MATCH_PARENT);
        }else{
            param2.width = (int) (Width * scale);
        }
        menu.setLayoutParams(param2);
    }

}
