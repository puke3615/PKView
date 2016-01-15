package pk.view.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.TypedValue;
import android.view.View;

/**
 * @author zijiao
 * @version 2016/1/14
 * @Mark
 */
public class OvalHelper {

    private View mView;
    private float mProgress;
    private Paint mPaint;
    private float mOffset;
    private boolean mFinish = true;

    public OvalHelper(View v) {
        this.mView = v;
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#e1e1e1"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth((mOffset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, v.getResources().getDisplayMetrics())));
    }

    public void start(int time) {
        ValueAnimator a = ValueAnimator.ofFloat(0, 1);
        a.setDuration(time);
        a.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (float) animation.getAnimatedValue();
                mView.invalidate();
            }
        });
        a.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mFinish = false;
                mView.invalidate();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mFinish = true;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        a.start();
    }

    public void handle(Canvas canvas) {
        if (mFinish) {
            return;
        }
        int w = mView.getMeasuredWidth();
        int h = mView.getMeasuredHeight();
        int r = Math.max(w, h) / 2;
        canvas.save();
        canvas.translate(w / 2, h / 2);
        float f = mOffset / 2;

        RectF rf = new RectF(-w / 2, -h / 2, w / 2, h / 2);
        canvas.save();
        canvas.clipRect(rf.left * mProgress, rf.top * mProgress, rf.right * mProgress, rf.bottom * mProgress);
//        canvas.drawColor(Color.GRAY);
        canvas.restore();

//        canvas.drawCircle(0, 0, (float) (Math.sqrt((w / 2) * (w / 2) + (h / 2) * (h / 2)) * mProgress), mPaint);
//        canvas.drawArc(new RectF(-w / 2 + f, -h / 2 + f, w / 2 - f, h / 2 - f), 0, 360 * mProgress, false, mPaint);
        canvas.restore();
    }

}
