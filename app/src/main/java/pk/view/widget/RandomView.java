package pk.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author zijiao
 * @version 2015/12/30
 * @Mark
 */
public class RandomView extends View {

    private Random mRandom;
    private List<String> mContents;

    private Paint mPaint;
    private Rect mBound;

    private String mText;
    private int mSize;

    public RandomView(Context context) {
        super(context);
        init();
    }

    public RandomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RandomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundColor(Color.GRAY);
        mRandom = new Random();
        mBound = new Rect();
        mSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics());

        mPaint = new Paint();
        mPaint.setTextSize(mSize);
        mPaint.setColor(Color.BLACK);
    }

    public void setSize(int size) {
        mSize = size;
        mPaint.setTextSize(mSize);
        mPaint.getTextBounds(mText, 0, mText.length(), mBound);
        requestLayout();
    }

    public void setColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }

    public void change() {
        int random = mRandom.nextInt(mContents.size());
        mText = mContents.get(random);
        mPaint.getTextBounds(mText, 0, mText.length(), mBound);
        requestLayout();
    }

    public boolean isTrue(String content) {
        return content == null ? false : content.equals(mText);
    }

    public void setData(String... contents) {
        mContents = Arrays.asList(contents);
        change();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        format();
        canvas.drawText(mText, (getWidth() - mBound.width()) / 2 + getPaddingLeft(), (getHeight() + mBound.height()) / 2 + getPaddingTop(), mPaint);
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
            mPaint.setTextSize(mSize);
            mPaint.getTextBounds(mText, 0, mText.length(), mBound);
            float textWidth = mBound.width() - getPaddingRight();
            int desired = (int) (textWidth + Math.max(getPaddingLeft() + getPaddingRight(), 72));
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            mPaint.setTextSize(mSize);
            mPaint.getTextBounds(mText, 0, mText.length(), mBound);
            float textHeight = mBound.height() - getPaddingBottom();
            int desired = (int) (textHeight + Math.max(getPaddingTop() + getPaddingBottom(), 60));
            height = desired;
        }

        setMeasuredDimension(width, height);
    }

    private void format() {
        if (mText == null) {
            mText = "";
        }
    }

}
