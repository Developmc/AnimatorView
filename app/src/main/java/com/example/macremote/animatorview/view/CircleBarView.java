package com.example.macremote.animatorview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**实现动画数字圆圈
 * Created by macremote on 2015/9/14.
 */
public class CircleBarView extends View {
    private RectF mColorWheelRectangle = new RectF() ;  //圆圈对象
    private Paint mDefaultWheelPaint ;       //绘制灰色圆圈的画笔
    private Paint mColorWheelPaint ;//绘制蓝色扇形的画笔
    private Paint textPaint ;//数字画笔
    private float mColorWheelRadius ;//圆圈普通状态下的半径
    private float circleStrokeWidth ;//圆圈线条大小
    private float pressExtraStrokeWidth ;//按下时圆圈线条的大小
    private String mText ;//数字内容
    private int mCount ;
    private float mSweepAnglePer ;  //蓝色扇形弧度
    private float mSweepAngle;      //扇形弧度
    private int mTextSize ;
    MyAnimation anim ;

    public CircleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init() ;
    }

    public CircleBarView(Context context) {
        super(context);
        init() ;
    }

    public CircleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init() ;
    }
    private void init()
    {
        //初始化参数（简单化就写死吧）
        circleStrokeWidth = dip2px(getContext(), 10);
        pressExtraStrokeWidth = dip2px(getContext(), 2);
        mTextSize = dip2px(getContext(), 40);

        mColorWheelPaint = new Paint(Paint.ANTI_ALIAS_FLAG) ;//设置抗锯齿
        mColorWheelPaint.setColor(0xFF29a6f6) ; //蓝色
        mColorWheelPaint.setStyle(Paint.Style.STROKE) ;//设置圆心掏空
        mColorWheelPaint.setStrokeWidth(circleStrokeWidth);

        mDefaultWheelPaint = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        mDefaultWheelPaint.setColor(0xFFE0E0E0) ;  //灰色底色
        mDefaultWheelPaint.setStyle(Paint.Style.STROKE) ;
        mDefaultWheelPaint.setStrokeWidth(circleStrokeWidth);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.LINEAR_TEXT_FLAG) ;
        textPaint.setColor(0xFF333333);  //黑色
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(mTextSize) ;

        mText="0";
        mSweepAngle = 0;

        anim = new MyAnimation();
        anim.setDuration(2000) ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mColorWheelRectangle, -90, 360, false, mDefaultWheelPaint);
        canvas.drawArc(mColorWheelRectangle, -90, mSweepAnglePer, false, mColorWheelPaint);
        Rect bounds = new Rect() ;
        String textStr = mCount+"" ;
        textPaint.getTextBounds(textStr,0,textStr.length(),bounds);
        canvas.drawText(textStr+"",(mColorWheelRectangle.centerX())-(textPaint.measureText(textStr)/2)
        ,mColorWheelRectangle.centerY()+bounds.height()/2,textPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec) ;
        int width = getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec) ;
        int min = Math.min(width,height) ;
        //将该view设置为正方形
        setMeasuredDimension(min,min);
        //避免画笔变粗时，画面溢出
        mColorWheelRadius = min-circleStrokeWidth-pressExtraStrokeWidth;
        mColorWheelRectangle.set(circleStrokeWidth+pressExtraStrokeWidth, circleStrokeWidth+pressExtraStrokeWidth,
                mColorWheelRadius, mColorWheelRadius);
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        if (pressed) {
            mColorWheelPaint.setColor(0xFF165da6);
            textPaint.setColor(0xFF070707);
            mColorWheelPaint.setStrokeWidth(circleStrokeWidth+pressExtraStrokeWidth);
            mDefaultWheelPaint.setStrokeWidth(circleStrokeWidth+pressExtraStrokeWidth);
            textPaint.setTextSize(mTextSize-pressExtraStrokeWidth);
        } else {
            mColorWheelPaint.setColor(0xFF29a6f6);
            textPaint.setColor(0xFF333333);
            mColorWheelPaint.setStrokeWidth(circleStrokeWidth);
            mDefaultWheelPaint.setStrokeWidth(circleStrokeWidth);
            textPaint.setTextSize(mTextSize);
        }
        super.setPressed(pressed);
        this.invalidate();
    }
    //重新开始动画
    public void startCustomAnimation(){
        this.startAnimation(anim);
    }

    public void setText(String text){
        mText = text;
        this.startAnimation(anim);
    }

    public void setSweepAngle(float sweepAngle){
        mSweepAngle = sweepAngle;

    }


    /**
     * 重写Animation
     */
    private class MyAnimation extends Animation{
        public MyAnimation(){}

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            //插值器interpolatedTime的取值：0-1
            if(interpolatedTime<1.0f)
            {
                mSweepAnglePer = interpolatedTime*mSweepAngle ;
                mCount = (int)(interpolatedTime*Float.parseFloat(mText)) ;
            }
            else
            {
                mSweepAnglePer = mSweepAngle ;
                mCount = Integer.parseInt(mText) ;
            }
            //通知view重绘
            postInvalidate();
        }
    }
    //适应屏幕分辨率
    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }
}
