package pk.view.drag;

import android.animation.ValueAnimator;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zijiao
 * @version 2016/1/13
 * @Mark
 */
public class DragLayout extends ViewGroup {

    private List<View> mViews = new ArrayList<>();
    private int x, y, x0, y0;
    private Rect mOrigin = new Rect();

    private Adapter mAdapter;
    private DataSetObserver mDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            refreshView();
        }
    };

    private void refreshView() {
        //
    }

    public DragLayout(Context context) {
        this(context, null, 0);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void setAdapter(Adapter adapter) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }
        this.mAdapter = adapter;
        this.mAdapter.registerDataSetObserver(mDataSetObserver);
        requestLayout();
    }

    private void init(AttributeSet attrs) {
        reLoad();
    }

    private void reLoad() {
        mViews.clear();
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            mViews.add(getChildAt(i));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                View view = getChildAt(i);
                width = Math.max(width, view.getMeasuredWidth());
                height = Math.max(height, view.getMeasuredHeight());
            }
            width += getPaddingRight() + getPaddingLeft();
            height += getPaddingTop() + getPaddingBottom();
            setMeasuredDimension(width, height);
        }
    }

    @Override
    public void addView(View child, int index, LayoutParams params) {
        super.addView(child, index, params);
        reLoad();
    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();
        reLoad();
    }

    @Override
    public void removeView(View view) {
        super.removeView(view);
        reLoad();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (mViews == null || mViews.size() == 0) {
            return false;
        }
        View v = mViews.get(0);
        if (e.getX() >= v.getLeft() && e.getX() <= v.getRight()
                && e.getY() >= v.getTop() && e.getY() <= v.getBottom()) {
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x0 = x = (int) e.getX();
                    y0 = y = (int) e.getY();
                    v.getDrawingRect(mOrigin);
                    break;
                case MotionEvent.ACTION_MOVE:
                    int xOffset = (int) (e.getX() - x);
                    int yOffset = (int) (e.getY() - y);
                    int l = v.getLeft() + xOffset;
                    int r = v.getRight() + xOffset;
                    int t = v.getTop() + yOffset;
                    int b = v.getBottom() + yOffset;
                    v.layout(l, t, r, b);
                    x = (int) e.getX();
                    y = (int) e.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    if (canFly(v)) {
                        fly(v);
                    }
                    break;
            }
        }
        return true;
    }

    private void fly(View v) {
        removeView(v);
    }

    private boolean canFly(View v) {
        if (Math.abs(y - y0) > 60 && Math.abs(x - x0) > 60) {
            return true;
        }
        return false;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = getWidth();
        int height = getHeight();
        for (View view : mViews) {
            int w = view.getMeasuredWidth();
            int h = view.getMeasuredHeight();
            l = (width - w) / 2;
            t = (height - h) / 2;
            r = (width + w) / 2;
            b = (height + h) / 2;
            view.layout(l, t, r, b);
        }
    }
}
