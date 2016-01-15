package pk.view.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * @author zijiao
 * @version 2016/1/13
 * @Mark
 */
public class FatherLayout extends RelativeLayout {

    public FatherLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FatherLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            L(getClass().getSimpleName() + " dispatchTouchEvent");
        }
        return super.dispatchTouchEvent(e);
//        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            L(getClass().getSimpleName() + " onTouch");
        }
        return super.onTouchEvent(e);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            L(getClass().getSimpleName() + " onIntercept");
        }
        return super.onInterceptTouchEvent(e);
//        return true;
    }

    static void L(Object s) {
        Log.i(TouchActivity.TAG, s + "");
    }
}