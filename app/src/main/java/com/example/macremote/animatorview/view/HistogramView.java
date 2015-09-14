package com.example.macremote.animatorview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**实现柱状图动画
 * Created by macremote on 2015/9/12.
 */
public class HistogramView extends View {
    private Paint mPaint ;
    //控制绘制速度，分100次完成绘制
    private static final int TOTAL_PAINT_TIMES = 100 ;
    private static final int RECT_WIDTH = 60;    //每个矩形块的宽度
    private static final int RECT_DISTANCE = 40; //矩形块之间的间距
    //待绘制的矩形块矩阵，left为高度，right为颜色
    private static final int[][] RECT_ARRAY = {
            {380,Color.GRAY},
            {600,Color.YELLOW},
            {200,Color.GREEN},
            {450,Color.RED},
            {300,Color.BLUE}
    };
    //当前已绘制的次数
    private int mPaintTimes = 0 ;

    public HistogramView(Context context) {
        super(context);
        initialize();
    }

    public HistogramView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public HistogramView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    /**绘制
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaintTimes++ ;

        for( int i=0; i<RECT_ARRAY.length; i++ ) {

            mPaint.setColor(RECT_ARRAY[i][1]);

            int paintXPos = i*(RECT_WIDTH+RECT_DISTANCE) + RECT_DISTANCE;
            int paintYPos = RECT_ARRAY[i][0]/TOTAL_PAINT_TIMES*mPaintTimes;

            //呵呵，弄错坐标图看不到
            canvas.drawRect(paintXPos, getHeight()-paintYPos, paintXPos+RECT_WIDTH,getHeight(), mPaint);
            canvas.drawText("" + paintYPos, paintXPos+RECT_WIDTH/2, getHeight() - paintYPos, mPaint);
            if( mPaintTimes < TOTAL_PAINT_TIMES ) {
                Log.d("Log",""+mPaintTimes) ;
                //实现动画的关键点(重绘)
                invalidate();
            }
        }

    }

    /**
     * 初始化对象
     */
    public void initialize()
    {
        mPaint = new Paint() ;
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }
}
