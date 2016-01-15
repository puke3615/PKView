package pk.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author zijiao
 * @version 2016/1/11
 * @Mark
 */
public class ClockView extends View {

    private Paint mPaint;
    private OvalHelper mHelper;

    public ClockView(Context context) {
        this(context, null, 0);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mHelper = new OvalHelper(this);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelper.start(200);
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.GREEN);
        canvas.translate(width / 2, height / 2);
        int size = width == 0 || height == 0 ? Math.max(width, height) : Math.min(width, height);
        int radius = (size - 200) / 2;
        canvas.drawCircle(0, 0, radius, mPaint);

        canvas.save();
        canvas.translate(-radius, -radius);
        Path path = new Path();
        path.addArc(new RectF(0, 0, 2 * radius, 2 * radius), -140, 120);
        mPaint.setStrokeWidth(2);
        mPaint.setTextSize(55);
        canvas.drawTextOnPath("http://www.mogujie.com", path, 0, 55, mPaint);
        canvas.restore();

        final int x = 0;
        final int y = radius;
        mPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < 60; i ++) {
            if (i % 5 == 0) {
                mPaint.setStrokeWidth(10);
                canvas.drawLine(x, y, x, y + 20, mPaint);
                float textSize = mPaint.getTextSize();
                int value = (i / 5 + 6) % 12;
                canvas.drawText(String.valueOf(value == 0 ? 12 : value), x - textSize / 2, y + 10 + textSize, new Paint(mPaint));
            } else {
                mPaint.setStrokeWidth(5);
                canvas.drawLine(x, y, x, y + 10, mPaint);
            }
            canvas.rotate(6, 0, 0);
        }

        canvas.restore();
        mHelper.handle(canvas);
    }
}
