package pk.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import pk.view.R;

public class HeadView extends View implements GestureDetector.OnGestureListener {

    private static final int DEAULT_TEXT_SIZE_SP = 20;

    private String mTitle;
    private String mLeftText;
    private String mRightText;
    private Bitmap mLeftBitmap;
    private Bitmap mRightBitmap;

    private int mLeftTextPadding;
    private int mRightTextPadding;

    private int mLeftSize;
    private int mTitleSize;
    private int mRightSize;

    private Rect mLeftRect;
    private Rect mTitleRect;
    private Rect mRightRect;
    private Paint mPaint;

    private GestureDetector mGestureDetector;
    private OnHeadClick mOnHeadClick;

    public HeadView(Context context) {
        super(context);
        init(null, 0);
    }

    public HeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public HeadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        mPaint = new Paint();
        mLeftRect = new Rect();
        mTitleRect = new Rect();
        mRightRect = new Rect();
        mGestureDetector = new GestureDetector(getContext(), this);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.HeadView);
            if (a != null) {
                int l = a.getResourceId(R.styleable.HeadView_leftImage, 0);
                if (l != 0) {
                    mLeftBitmap = BitmapFactory.decodeResource(getResources(), l);
                }
                int r = a.getResourceId(R.styleable.HeadView_rightImage, 0);
                if (r != 0) {
                    mRightBitmap = BitmapFactory.decodeResource(getResources(), r);
                }

                int color = a.getColor(R.styleable.HeadView_fontColor, Color.BLACK);
                mPaint.setColor(color);

                int defaultSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEAULT_TEXT_SIZE_SP, getResources().getDisplayMetrics());

                mLeftTextPadding = a.getDimensionPixelSize(R.styleable.HeadView_leftTextPadding, 0);
                mLeftText = a.getString(R.styleable.HeadView_leftText);
                if (mLeftText == null) {
                    mLeftText = "";
                }
                mLeftSize = a.getDimensionPixelSize(R.styleable.HeadView_leftSize, defaultSize);
                mPaint.setTextSize(mLeftSize);
                mPaint.getTextBounds(mLeftText, 0, mLeftText.length(), mLeftRect);

                mTitle = a.getString(R.styleable.HeadView_titleText);
                if (mTitle == null) {
                    mTitle = "";
                }
                mTitleSize = a.getDimensionPixelSize(R.styleable.HeadView_titleSize, defaultSize);
                mPaint.setTextSize(mTitleSize);
                mPaint.getTextBounds(mTitle, 0, mTitle.length(), mTitleRect);

                mRightTextPadding = a.getDimensionPixelSize(R.styleable.HeadView_rightTextPadding, 0);
                mRightText = a.getString(R.styleable.HeadView_rightText);
                if (mRightText == null) {
                    mRightText = "";
                }
                mRightSize = a.getDimensionPixelSize(R.styleable.HeadView_rightSize, defaultSize);
                mPaint.setTextSize(mRightSize);
                mPaint.getTextBounds(mRightText, 0, mRightText.length(), mRightRect);
                a.recycle();
            }
        }


        setOnHeadClick(new OnHeadClick() {
            @Override
            public void onLeftClick(HeadView view) {
                Toast.makeText(getContext(), "左", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightClick(HeadView view) {
                Toast.makeText(getContext(), "右", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setLeftSize(int size) {
        mLeftSize = size;
        mPaint.getTextBounds(mLeftText, 0, mLeftText == null ? 0 : mLeftText.length(), mLeftRect);
        invalidate();
    }

    public void setRightSize(int size) {
        mRightSize = size;
        mPaint.getTextBounds(mRightText, 0, mRightText == null ? 0 : mRightText.length(), mRightRect);
        invalidate();
    }

    public void setTitleSize(int size) {
        mTitleSize = size;
        mPaint.getTextBounds(mTitle, 0, mTitle == null ? 0 : mTitle.length(), mTitleRect);
        invalidate();
    }

    public void setTextColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode != MeasureSpec.EXACTLY) {
            width = getResources().getDisplayMetrics().widthPixels;
        }

        if (heightMode != MeasureSpec.EXACTLY) {
            int paddingHeight = getPaddingTop() + getPaddingBottom();
            int minHeight = Math.max(Math.max(mLeftRect.height(), mRightRect.height()), mTitleRect.height());
            if (mLeftBitmap != null) {
                minHeight = Math.max(minHeight, mLeftBitmap.getHeight());
            }
            if (mRightBitmap != null) {
                minHeight = Math.max(minHeight, mRightBitmap.getHeight());
            }
            minHeight += paddingHeight;
            if (height < minHeight) {
                height = minHeight;
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();

        int width = getWidth();
        int height = getHeight();

        int leftOffset = mLeftTextPadding;
        if (mLeftBitmap != null) {
            leftOffset += mLeftBitmap.getWidth();
            canvas.drawBitmap(mLeftBitmap, paddingLeft, (height - mLeftBitmap.getHeight()) / 2, mPaint);
        }
        mPaint.setTextSize(mLeftSize);
        canvas.drawText(mLeftText, leftOffset + paddingLeft, (height + mLeftRect.height()) / 2, mPaint);

        mPaint.setTextSize(mTitleSize);
        canvas.drawText(mTitle, (width - mTitleRect.width()) / 2, (height + mTitleRect.height()) / 2, mPaint);

        int rightOffset = mRightTextPadding;
        if (mRightBitmap != null) {
            rightOffset += mRightBitmap.getWidth();
            canvas.drawBitmap(mRightBitmap, width - paddingRight - rightOffset, (height - mRightBitmap.getHeight()) / 2, mPaint);
        }
        mPaint.setTextSize(mRightSize);
        canvas.drawText(mRightText, width - paddingRight - mRightRect.width() - rightOffset, (height + mRightRect.height()) / 2, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (mOnHeadClick != null) {
            int leftArea = getPaddingLeft() + mLeftTextPadding + mLeftRect.width();
            if (mLeftBitmap != null) {
                leftArea += mLeftBitmap.getWidth();
            }

            int rightArea = getWidth() - getPaddingRight() - mRightTextPadding - mRightRect.width();
            if (mRightBitmap != null) {
                rightArea -= mRightBitmap.getWidth();
            }

            float x = e.getX();
            if (x < leftArea) {
                mOnHeadClick.onLeftClick(this);
            } else if (x > rightArea) {
                mOnHeadClick.onRightClick(this);
            }
        }
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return true;
    }

    public void setOnHeadClick(OnHeadClick listener) {
        this.mOnHeadClick = listener;
    }

    public static interface OnHeadClick {
        void onLeftClick(HeadView view);

        void onRightClick(HeadView view);
    }

}
