package pk.view.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author zijiao
 * @version 2016/1/11
 * @Mark
 */
public class ClickEffectImageView extends ImageView {

    private boolean isLongClick;
    private boolean isRelease;
    private boolean isCancel;

    public ClickEffectImageView(Context context) {
        this(context, null, 0);
    }

    public ClickEffectImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClickEffectImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "点击了", Toast.LENGTH_SHORT).show();
            }
        });
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getContext(), "长按了", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

        @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isCancel = false;
                isLongClick = false;
                isRelease = false;
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!isCancel && (isLongClick = !isRelease)) {
                            performLongClick();
                        }
                    }
                }, 1000);
                addClickEffect();
                break;
            case MotionEvent.ACTION_MOVE:
                isCancel = e.getX() < 0 || e.getX() > getWidth()
                        || e.getY() < 0 || e.getY() > getHeight();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (!isRelease) {
                    isRelease = true;
                    clearEffect();
                }
                if(!isCancel && !isLongClick) {
                    performClick();
                }
                break;
        }
        return true;
    }

    private void addClickEffect() {
//        Drawable drawable = getDrawable();
//        if (drawable != null) {
//            //指定滤镜颜色以及混合模式
//            drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
//            setImageDrawable(drawable);
//        }
        setColorFilter(Color.parseColor("#33000000"));
    }

    private void clearEffect() {
//        Drawable drawable = getDrawable();
//        if (drawable != null) {
//            drawable.clearColorFilter();
//            setImageDrawable(drawable);
//        }
        clearColorFilter();
    }


}
