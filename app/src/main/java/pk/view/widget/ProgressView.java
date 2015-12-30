package pk.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import pk.view.R;

/**
 * @author zijiao
 * @version 2015/12/30
 * @Mark
 */
public class ProgressView extends View {

    private int mFirstColor;        //底层颜色
    private int mSecondColor;       //上层颜色
    private int mProgressWidth;     //进度条宽度
    private String mText;           //默认文字显示
    private int mTextSize;          //文字大小
    private int mTextColor;         //文字颜色
    private int mRadius;            //进度圈半径
    private float mCurProgress;     //当前进度值,0 - 1之间
    private boolean mTextEnable;    //是否显示text
    private int mAngle;             //0%处对应的角度

    private Rect mTextBound;        //文字绘制边界
    private RectF mOval;            //圆

    private Paint mPaint;

    public ProgressView(Context context) {
        super(context);
        init(null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        findValueByXml(attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mTextBound = new Rect();
        mOval = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width, height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = 2 * mRadius + mProgressWidth + getPaddingLeft() + getPaddingRight();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            int heightOffset = getPaddingTop() + getPaddingBottom();
            height = 2 * mRadius + mProgressWidth + heightOffset;
        }

        setMeasuredDimension(width, height);
    }

    public void setProgress(float progress) {
        if (progress < 0 || progress > 1) {
            return;
        }
        mCurProgress = progress;
        mText = (int) (mCurProgress * 100) + "%";
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setStrokeWidth(mProgressWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        float cx = mRadius + mProgressWidth / 2 + getPaddingLeft();
        float cy = mRadius + mProgressWidth / 2 + getPaddingTop();
        mOval.left = cx - mRadius;
        mOval.right = cx + mRadius;
        mOval.top = cy - mRadius;
        mOval.bottom = cy + mRadius;
        //画第一个圆
        mPaint.setColor(mFirstColor);
        canvas.drawCircle(cx, cy, mRadius, mPaint);

        //画第二个圆
        mPaint.setColor(mSecondColor);
        canvas.drawArc(mOval, mAngle, 360 * mCurProgress, false, mPaint);

        //画文字
        if (mText != null && mTextEnable) {
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setTextSize(mTextSize);
            mPaint.setColor(mTextColor);
            mPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
            canvas.drawText(mText, cx - mTextBound.width() / 2, cy + mTextBound.height() / 2 ,mPaint);
        }
    }

    private void findValueByXml(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray arrays = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressView);
            mRadius = arrays.getDimensionPixelSize(R.styleable.ProgressView_progressRadius, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()));
            mProgressWidth = arrays.getDimensionPixelSize(R.styleable.ProgressView_progressWidth, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
            mCurProgress = arrays.getFloat(R.styleable.ProgressView_curProgress, 0);
            mFirstColor = arrays.getColor(R.styleable.ProgressView_firstColor, Color.MAGENTA);
            mSecondColor = arrays.getColor(R.styleable.ProgressView_secondColor, Color.YELLOW);
            mTextColor = arrays.getColor(R.styleable.ProgressView_progressTextColor, Color.BLACK);
            mTextSize = arrays.getDimensionPixelSize(R.styleable.ProgressView_progressTextSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics()));
            mText = arrays.getString(R.styleable.ProgressView_progressText);
            mTextEnable = arrays.getBoolean(R.styleable.ProgressView_progressTextEnable, true);
            mAngle = arrays.getInt(R.styleable.ProgressView_progressAngle, 0) % 360;
            if (mText == null) {
                mText = (int) (mCurProgress * 100) + "%";
            }
            arrays.recycle();
        }

    }

    public void setSecondColor(int secondColor) {
        if (mSecondColor != secondColor) {
            mSecondColor = secondColor;
            invalidate();
        }
    }

    public void setFirstColor(int firstColor) {
        if (mFirstColor != firstColor) {
            mFirstColor = firstColor;
            invalidate();
        }
    }
}
