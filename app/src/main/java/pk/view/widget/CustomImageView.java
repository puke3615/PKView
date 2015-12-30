package pk.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import pk.view.R;

/**
 * @author zijiao
 * @version 2015/12/30
 * @Mark
 */
public class CustomImageView extends View {

    private String mText;           //文字内容
    private int mTextSize;          //字体大小
    private int mTextColor;         //字体颜色
    private Bitmap mBitmap;         //图片
    private int mImageType;         //0：fillXY  1：center

    private Rect mImageRect;        //图片实际边框
    private Paint mPaint;           //画笔
    private Rect mTextBound;        //文字边界

    public CustomImageView(Context context) {
        super(context);
        init(null);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        findValueByXmlIfNeed(attrs);
        mImageRect = new Rect();
        mTextBound = new Rect();
        mPaint = new Paint();

        //设置画笔颜色
        mPaint.setTextSize(mTextSize);
        //计算文字部分边界
        mPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width, height;

        //计算宽度
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            int textDesire = mTextBound.width() + getPaddingLeft() + getPaddingRight();
            int imageDesire = mBitmap.getWidth() + getPaddingLeft() + getPaddingRight();
            width = Math.max(textDesire, imageDesire);
        }

        //计算高度
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            int textDesire = mTextBound.height() + getPaddingTop() + getPaddingBottom();
            int imageDesire = mBitmap.getHeight() + getPaddingTop() + getPaddingBottom();
            height = Math.max(textDesire, imageDesire);
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //画边框
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        //对外边框赋值
        mImageRect.left = getPaddingLeft();
        mImageRect.right = getWidth() - getPaddingRight();
        mImageRect.top = getPaddingTop();
        mImageRect.bottom = getHeight() - getPaddingBottom();

        //画文本
        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        if (mTextBound.width() > getWidth()) {//文字宽度大于控件宽度时，追加省略号显示
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mText, paint, getWidth() - getPaddingLeft() - getPaddingRight(), TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), getHeight() - getPaddingBottom(), mPaint);
        } else {//文字宽度小于空间宽度时，居中显示
            canvas.drawText(mText, (getWidth() - mTextBound.width()) / 2, getHeight() - getPaddingBottom(), mPaint);
        }

        //画图
        if (mBitmap != null) {
            mImageRect.bottom -= mTextBound.height();//图片的下边界，是在视图下边界的基础上减去文字的高度
            if (mImageType == 1) {//type为center时，处理mImageRect
                mImageRect.left = (getWidth() - mBitmap.getWidth()) / 2;
                mImageRect.right = (getWidth() + mBitmap.getWidth()) / 2;
                mImageRect.top = (getHeight() - mTextBound.height() - mBitmap.getHeight()) / 2;
                mImageRect.bottom = (getHeight() - mTextBound.height() + mBitmap.getHeight()) / 2;

            }
            canvas.drawBitmap(mBitmap, null, mImageRect, mPaint);
        }

    }

    private void findValueByXmlIfNeed(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray arrays = getContext().obtainStyledAttributes(attrs, R.styleable.CustomImageView);
            int size = arrays.getIndexCount();
            for (int i = 0; i < size; i ++) {
                int attr = arrays.getIndex(i);
                switch (attr) {
                    case R.styleable.CustomImageView_text:
                        mText = arrays.getString(attr);
                        break;
                    case R.styleable.CustomImageView_textSize:
                        mTextSize = arrays.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                        break;
                    case R.styleable.CustomImageView_textColor:
                        mTextColor = arrays.getColor(attr, Color.BLACK);
                        break;
                    case R.styleable.CustomImageView_image:
                        mBitmap = BitmapFactory.decodeResource(getResources(), arrays.getResourceId(attr, 0));
                        break;
                    case R.styleable.CustomImageView_imageType:
                        mImageType = arrays.getInt(attr, 0);
                        break;
                }
            }
            arrays.recycle();
        }
    }


}
