package pk.view.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author zijiao
 * @version 2015/12/30
 * @Mark
 */
public class CornerLayout extends ViewGroup {

    public CornerLayout(Context context) {
        super(context);
    }

    public CornerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CornerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 布局设置为外边距布局
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY || heightMode != MeasureSpec.EXACTLY) {
            int w1 = 0;
            int w2 = 0;
            int h1 = 0;
            int h2 = 0;
            int count = getChildCount();
            for (int i = 0; i < count; i ++) {
                View v = getChildAt(i);
                MarginLayoutParams lp = (MarginLayoutParams) v.getLayoutParams();
                int w = v.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                int h = v.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
                switch (i) {
                    case 0:
                        w1 += w;
                        h1 += h;
                        break;
                    case 1:
                        w1 += w;
                        h2 += h;
                        break;
                    case 2:
                        w2 += w;
                        h1 += h;
                        break;
                    case 3:
                        w2 += w;
                        h2 += h;
                        break;
                }
            }

            if (widthMode != MeasureSpec.EXACTLY) {
                width = Math.max(w1, w2);
            }

            if (heightMode != MeasureSpec.EXACTLY) {
                height = Math.max(h1, h2);
            }
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i ++) {
            View v = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) v.getLayoutParams();
            int w = v.getMeasuredWidth();
            int h = v.getMeasuredHeight();
            switch (i) {
                case 0:
                    l = lp.leftMargin;
                    t = lp.topMargin;
                    break;
                case 1:
                    l = getWidth() - lp.rightMargin - w;
                    t = lp.topMargin;
                    break;
                case 2:
                    l = lp.leftMargin;
                    t = getHeight() - lp.bottomMargin - h;
                    break;
                case 3:
                    l = getWidth() - lp.rightMargin - w;
                    t = getHeight() - lp.bottomMargin - h;
                    break;
            }
            r = l + w;
            b = t + h;
            v.layout(l, t, r, b);
        }
    }
}
