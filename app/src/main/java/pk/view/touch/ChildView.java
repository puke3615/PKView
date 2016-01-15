package pk.view.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author zijiao
 * @version 2016/1/13
 * @Mark
 */
public class ChildView extends View {
    public ChildView(Context context, AttributeSet attrs) {
        super(context, attrs);
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

    static void L(Object s) {
        Log.i(TouchActivity.TAG, s + "");
    }

}
