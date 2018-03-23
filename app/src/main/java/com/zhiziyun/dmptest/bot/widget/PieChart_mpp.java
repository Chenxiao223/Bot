package com.zhiziyun.dmptest.bot.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.zhiziyun.dmptest.bot.entity.PieDataEntity;
import com.zhiziyun.dmptest.bot.ui.fragment.VisitorsViewFragment;
import com.zhiziyun.dmptest.bot.util.CalculateUtil;

import java.util.Arrays;
import java.util.List;


/**
 * 饼状图表
 */
public class PieChart_mpp extends View {
    /**
     * 视图的宽和高
     */
    private int mTotalWidth, mTotalHeight;
    /**
     * 绘制区域的半径
     */
    private float mRadius;

    private Paint mPaint, mLinePaint, mTextPaint;

    private Path mPath;
    /**
     * 扇形的绘制区域
     */
    private RectF mRectF;
    /**
     * 点击之后的扇形的绘制区域
     */
    private RectF mRectFTouch;

    private List<PieDataEntity> mDataList;
    /**
     * 所有的数据加起来的总值
     */
    private float mTotalValue;
    /**
     * 起始角度的集合
     */
    private float[] angles;
    /**
     * 手点击的部分的position
     */
    private int position = -1;
    /**
     * 点击监听
     */
    private OnItemPieClickListener mOnItemPieClickListener;

    public void setOnItemPieClickListener(OnItemPieClickListener onItemPieClickListener) {
        mOnItemPieClickListener = onItemPieClickListener;
    }

    public interface OnItemPieClickListener {
        void onClick(int position);
    }

    public PieChart_mpp(Context context) {
        super(context);
        init(context);
    }

    public PieChart_mpp(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PieChart_mpp(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mRectF = new RectF();
        mRectFTouch = new RectF();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setStrokeWidth(2);
        mLinePaint.setColor(Color.BLACK);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(18);

        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w - getPaddingLeft() - getPaddingRight();
        mTotalHeight = h - getPaddingTop() - getPaddingBottom();

        mRadius = (float) (Math.min(mTotalWidth, mTotalHeight) / 2 * 0.7);
//        mRadius=55f;//饼状图半径

        mRectF.left = -mRadius;
        mRectF.top = -mRadius;
        mRectF.right = mRadius;
        mRectF.bottom = mRadius;

        mRectFTouch.left = -mRadius - 16;
        mRectFTouch.top = -mRadius - 16;
        mRectFTouch.right = mRadius + 16;
        mRectFTouch.bottom = mRadius + 16;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDataList == null)
            return;
        canvas.translate(mTotalWidth / 2, mTotalHeight / 2);
        //绘制饼图的每块区域
        drawPiePath(canvas);
    }

    /**
     * 绘制饼图的每块区域 和文本
     *
     * @param canvas
     */
    private void drawPiePath(Canvas canvas) {
        //起始地角度
        float startAngle = 0;

        for (int i = 0; i < mDataList.size(); i++) {
            float sweepAngle = mDataList.get(i).getValue() / mTotalValue * 360 - 1;//每个扇形的角度
            mPaint.setColor(mDataList.get(i).getColor());
            if (position - 1 == i) {
                canvas.drawArc(mRectFTouch, startAngle, sweepAngle, true, mPaint);
            } else {
                canvas.drawArc(mRectF, startAngle, sweepAngle, true, mPaint);
            }
            Log.i("toRadians", (startAngle + sweepAngle / 2) + "****" + Math.toRadians(startAngle + sweepAngle / 2));
            //确定直线的起始和结束的点的位置
            float pxs = (float) (mRadius * Math.cos(Math.toRadians(startAngle + sweepAngle / 2)));
            float pys = (float) (mRadius * Math.sin(Math.toRadians(startAngle + sweepAngle / 2)));
            float pxt = (float) ((mRadius + 30) * Math.cos(Math.toRadians(startAngle + sweepAngle / 2)));
            float pyt = (float) ((mRadius + 30) * Math.sin(Math.toRadians(startAngle + sweepAngle / 2)));
            angles[i] = startAngle;
            startAngle += sweepAngle + 1;
            //绘制线和文本
            canvas.drawLine(pxs, pys, pxt, pyt, mLinePaint);
            float res = mDataList.get(i).getValue() / mTotalValue * 100;
            //提供精确的小数位四舍五入处理。
            double resToRound = CalculateUtil.round(res, 2);
            float v = startAngle % 360;
            if (startAngle % 360.0 >= 90.0 && startAngle % 360.0 <= 270.0) {//2 3 象限
                try {
                    canvas.drawLine(pxt, pyt, pxt - 30, pyt, mLinePaint);
                    canvas.drawText(VisitorsViewFragment.visitorsViewFragment.list_price.get(i), pxt - mTextPaint.measureText(resToRound + "%") - 30, pyt, mTextPaint);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {//1 4象限
                try {
                    canvas.drawLine(pxt, pyt, pxt + 30, pyt, mLinePaint);
                    canvas.drawText(VisitorsViewFragment.visitorsViewFragment.list_price.get(i),pxt+30,pyt,mTextPaint);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void setDataList(List<PieDataEntity> dataList) {
        this.mDataList = dataList;
        mTotalValue = 0;
        for (PieDataEntity pieData : mDataList) {
            mTotalValue += pieData.getValue();
        }
        angles = new float[mDataList.size()];
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX() - (mTotalWidth / 2);
                float y = event.getY() - (mTotalHeight / 2);
                float touchAngle = 0;
                if (x < 0 && y < 0) {  //2 象限
                    touchAngle += 180;
                } else if (y < 0 && x > 0) {  //1象限
                    touchAngle += 360;
                } else if (y > 0 && x < 0) {  //3象限
                    touchAngle += 180;
                }
                //Math.atan(y/x) 返回正数值表示相对于 x 轴的逆时针转角，返回负数值则表示顺时针转角。
                //返回值乘以 180/π，将弧度转换为角度。
                touchAngle += Math.toDegrees(Math.atan(y / x));
                if (touchAngle < 0) {
                    touchAngle = touchAngle + 360;
                }
                float touchRadius = (float) Math.sqrt(y * y + x * x);
                if (touchRadius < mRadius) {
                    try {
                        position = -Arrays.binarySearch(angles, (touchAngle)) - 1;
                        invalidate();
                        if (mOnItemPieClickListener != null) {
                            mOnItemPieClickListener.onClick(position - 1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
