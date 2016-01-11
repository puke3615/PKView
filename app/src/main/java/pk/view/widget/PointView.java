package pk.view.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zijiao
 * @version 2016/1/11
 * @Mark
 */
public class PointView extends View {

    private final List<List<Point>> mPoints = new ArrayList<>();
    private boolean isRecord = true;

    private int mProgress;
    private Paint mPaint;

    private int time = 6000;

    public void setTime(int time) {
        this.time = time;
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        invalidate();
    }

    public void clear() {
        this.mPoints.clear();
        invalidate();
    }

    public void setIsRecord(boolean isRecord) {
        this.isRecord = isRecord;
        if (!isRecord) {
            int all = 0;
            for (List<Point> l : mPoints) {
                all += l.size();
            }
            ValueAnimator a = ValueAnimator.ofInt(0, all);
            a.setDuration(time);
            a.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator a) {
                    int value = (int) a.getAnimatedValue();
                    setProgress(value);
                }
            });
            a.start();
        } else {
            mPoints.clear();
        }
    }

    public PointView(Context context) {
        this(context, null, 0);
    }

    public PointView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);

//        mPaint.setPathEffect(new DashPathEffect(new float[]{20, 10}, 0));
        mPaint.setPathEffect(new CornerPathEffect(2));
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (isRecord) {
            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                mPoints.add(new ArrayList<Point>());
            }
            int x = (int) e.getX();
            int y = (int) e.getY();
            mPoints.get(mPoints.size() - 1).add(new Point(x, y));
            return true;
        }
        return super.onTouchEvent(e);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isRecord) {
            int progress = 0;
            for (List<Point> l : mPoints) {
                boolean first = true;
                Path path = new Path();
                for (Point p : l) {
                    if (progress > mProgress) {
                        break;
                    }
                    if (first) {
                        first = false;
                        path.moveTo(p.x, p.y);
                    } else {
                        path.lineTo(p.x, p.y);
                    }
                    progress += 1;
                }
                canvas.drawPath(path, mPaint);
                if (progress > mProgress) {
                    break;
                }
            }
        }
    }


}
