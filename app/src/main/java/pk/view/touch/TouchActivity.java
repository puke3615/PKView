package pk.view.touch;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import pk.view.R;

/**
 * @author zijiao
 * @version 2016/1/13
 * @Mark
 */
public class TouchActivity extends Activity {

    public static final String TAG = "touchtouch";
    private View relative, linear, button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            L("                                                             ");
            L(getClass().getSimpleName() + " dispatchTouchEvent");
        }
        return super.dispatchTouchEvent(e);
//        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            L(getClass().getSimpleName() + " onTouch");
        }
        return super.onTouchEvent(e);
//        return true;
    }

    private void setContentView() {
        setContentView(R.layout.activity_touch);
        relative = findViewById(R.id.relative);
        linear = findViewById(R.id.linear);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L("onClick");
            }
        });
    }

    public static class FatherLayout extends LinearLayout {

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
    }

    static void L(Object s) {
        Log.i(TAG, s + "");
    }

}
