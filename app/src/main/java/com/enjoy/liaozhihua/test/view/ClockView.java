package com.enjoy.liaozhihua.test.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.enjoy.liaozhihua.test.R;

import java.util.Calendar;

public class ClockView extends View {

    /* 全局画布 */
    private Canvas mCanvas;
    /* 12、3、6、9小时文本画笔 */
    private Paint mTextPaint;
    /* 测量小时文本宽高的矩形 */
    private Rect mTextRect = new Rect();
    /* 小时圆圈画笔 */
    private Paint mCirclePaint;
    /* 小时圆圈线条宽度 */
    private float mCircleStrokeWidth = 4;
    /* 小时圆圈的外接矩形 */
    private RectF mCircleRectF = new RectF();

    /* 亮色，用于分针、秒针、渐变终止色 */
    private int mLightColor;
    /* 暗色，圆弧、刻度线、时针、渐变起始色 */
    private int mDarkColor;
    /* 背景色 */
    private int mBackgroundColor;
    /* 小时文本字体大小 */
    private float mTextSize;

    /* 时钟半径，不包括padding值 */
    private float mRadius;
    /* 刻度线长度 */
    private float mScaleLength;

    /* 时针角度 */
    private float mHourDegree;
    /* 分针角度 */
    private float mMinuteDegree;
    /* 秒针角度 */
    private float mSecondDegree;
    /* 时针画笔 */
    private Paint mHourHandPaint;
    /* 分针画笔 */
    private Paint mMinuteHandPaint;
    /* 秒针画笔 */
    private Paint mSecondHandPaint;
    /* 时针路径 */
    private Path mHourHandPath = new Path();
    /* 分针路径 */
    private Path mMinuteHandPath = new Path();
    /* 秒针路径 */
    private Path mSecondHandPath = new Path();

    /* 加一个默认的padding值，为了防止用camera旋转时钟时造成四周超出view大小 */
    private float mDefaultPadding;
    private float mPaddingLeft;
    private float mPaddingTop;
    private float mPaddingRight;
    private float mPaddingBottom;

    /* 梯度扫描渐变 */
    private SweepGradient mSweepGradient;
    /* 渐变矩阵，作用在SweepGradient */
    private Matrix mGradientMatrix;

    /* 指针的在x轴的位移 */
    private float mCanvasTranslateX;
    /* 指针的在y轴的位移 */
    private float mCanvasTranslateY;
    /* 指针的最大位移 */
    private float mMaxCanvasTranslate;

    /* 刻度圆弧画笔 */
    private Paint mScaleArcPaint;
    /* 刻度圆弧的外接矩形 */
    private RectF mScaleArcRectF = new RectF();
    /* 刻度线画笔 */
    private Paint mScaleLinePaint;

    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClockView);
        mBackgroundColor = typedArray.getColor(R.styleable.ClockView_clock_backgroundColor, Color.parseColor("#237EAD"));
        mDarkColor = typedArray.getColor(R.styleable.ClockView_clock_darkColor, Color.parseColor("#237EAD"));
        mLightColor = typedArray.getColor(R.styleable.ClockView_clock_lightColor, Color.parseColor("#237EAD"));
        int mTextSize = typedArray.getDimensionPixelSize(R.styleable.ClockView_clock_textSize, 14);
        typedArray.recycle();
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mDarkColor);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(mTextSize);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(mCircleStrokeWidth);
        mCirclePaint.setColor(mDarkColor);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureDimension(widthMeasureSpec),measureDimension(heightMeasureSpec));
    }

    private int measureDimension(int measureSpec) {
        int defaultSize = 800;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode){
            case MeasureSpec.EXACTLY:
                return size;
            case MeasureSpec.UNSPECIFIED:
                return defaultSize;
            case MeasureSpec.AT_MOST:
                return Math.min(defaultSize,size);
                default:
                    return defaultSize;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int mRadius  = Math.min(w - getPaddingLeft() - getPaddingRight(),
                h - getPaddingTop() - getPaddingBottom())/2;
        mDefaultPadding  = 0.12f * mRadius;
        mPaddingLeft  = mDefaultPadding + w / 2 - mRadius;
        mPaddingRight = mDefaultPadding + w / 2 - mRadius ;
        mPaddingTop = mDefaultPadding + h / 2 - mRadius ;
        mPaddingBottom = mDefaultPadding + h / 2 - mRadius ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;
        getCurrentTime();
        drawOutSideArc();
    }

    private void drawOutSideArc() {
        String[] timeList = new String[]{"12", "3", "6", "9"};
        //计算数字的高度
        mTextPaint.getTextBounds(timeList[1], 0, timeList[1].length(), mTextRect);
        mCircleRectF.set(mPaddingLeft + mTextRect.width() / 2 + mCircleStrokeWidth / 2,
                mPaddingTop + mTextRect.height() / 2 + mCircleStrokeWidth / 2,
                getWidth() - mPaddingRight - mTextRect.width() / 2 - mCircleStrokeWidth / 2,
                getHeight() - mPaddingBottom - mTextRect.height() / 2 - mCircleStrokeWidth / 2);
        mCanvas.drawText(timeList[0], getWidth() / 2, mCircleRectF.top + mTextRect.height() / 2, mTextPaint);
        mCanvas.drawText(timeList[1], mCircleRectF.right, getHeight() / 2 + mTextRect.height() / 2, mTextPaint);
        mCanvas.drawText(timeList[2], getWidth() / 2, mCircleRectF.bottom + mTextRect.height() / 2, mTextPaint);
        mCanvas.drawText(timeList[3], mCircleRectF.left, getHeight() / 2 + mTextRect.height() / 2, mTextPaint);
        //画连接数字的4段弧线
        for (int i = 0; i < 4; i++) {
            mCanvas.drawArc(mCircleRectF, 5 + 90 * i, 80, false, mCirclePaint);
        }
    }

    /**
     * 获取当前时间
     */
    private void getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        float milliSecond = calendar.get(Calendar.MILLISECOND);
        float second = calendar.get(Calendar.SECOND) + milliSecond / 1000;
        float minute = calendar.get(Calendar.MINUTE) + second / 60;
        float hour = calendar.get(Calendar.HOUR) + minute / 60;
        mSecondDegree = second / 60 * 360;
        mMinuteDegree = minute /60 * 360;
        mHourDegree = hour / 12 * 360;
    }

}
