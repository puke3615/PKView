package pk.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author zijiao
 * @version 2016/1/14
 * @Mark
 */
public class RoundImageView extends ImageView {

    private Path mPath;
    private Paint mPaint;

    public RoundImageView(Context context) {
        this(context, null, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(6);
        mPaint.setTextSize(16);
        mPaint.setTextAlign(Paint.Align.RIGHT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable d = getDrawable();
        if (d == null) {
            super.onDraw(canvas);
        } else {
            Bitmap b = ((BitmapDrawable) d).getBitmap();
            mPath.reset();
            canvas.clipPath(mPath);
            int w = getMeasuredWidth();
            int h = getMeasuredHeight();
            int r = Math.min(w, h) / 2;
            mPath.addCircle(w / 2, h / 2, r, Path.Direction.CCW);
            canvas.clipPath(mPath, Region.Op.REPLACE);
            canvas.drawBitmap(b, 0, 0, mPaint);
        }
    }
}
