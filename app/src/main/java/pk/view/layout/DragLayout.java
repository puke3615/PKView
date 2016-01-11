package pk.view.layout;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author zijiao
 * @version 2015/12/31
 * @Mark
 */
public class DragLayout extends LinearLayout {

    private ViewDragHelper mHelper;
    private Map<View, P> mOriginDatas;
    private Set<View> mEdgeViews;

    private static enum TYPE {
        NONE, DRAG, AUTO_BACK, EDGE_TRACKER
    }

    private class P {
        public P(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int x, y;
    }

    public TYPE getType(View view) {
        TYPE type = TYPE.NONE;
        int index = indexOfChild(view);
        switch(index) {
            case 0:
                type = TYPE.DRAG;
                break;
            case 1:
                type = TYPE.AUTO_BACK;
                break;
            case 3:
            case 2:
                type = TYPE.EDGE_TRACKER;
                break;
            default:
                type = TYPE.NONE;
                break;
        }
        return type;
    }

    public DragLayout(Context context) {
        super(context);
        init();
    }

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mHelper = ViewDragHelper.create(this, 1, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                TYPE type = getType(child);
                return type == TYPE.DRAG || type == TYPE.AUTO_BACK;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                TYPE type = getType(releasedChild);
                if (type == TYPE.AUTO_BACK) {
                    if (mOriginDatas != null && mOriginDatas.size() != 0) {
                        P p = mOriginDatas.get(releasedChild);
                        if (p != null) {
                            mHelper.settleCapturedViewAt(p.x, p.y);
                            invalidate();
                        }
                    }
                }
            }

            //在边界拖动时回调
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                if (mEdgeViews != null && mEdgeViews.size() != 0) {
                    for (View view : mEdgeViews) {
                        mHelper.captureChildView(view, pointerId);
                    }
                }
            }

            @Override
            public int clampViewPositionVertical(View v, int top, int dy) {
                //上边界
                int topBound = getPaddingTop();
                //下边界
                int bottomBound = getHeight() - getPaddingBottom() - v.getHeight();

                if (top < topBound) {
                    top = topBound;
                } else if (top > bottomBound) {
                    top = bottomBound;
                }

                return top;
            }

            @Override
            public int clampViewPositionHorizontal(View v, int left, int dx) {
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - v.getWidth() - leftBound;

                //final int newLeft = Math.min(Math.max(left, leftBound), rightBound);

                if (left < leftBound) {
                    left = leftBound;
                } else if (left > rightBound) {
                    left = rightBound;
                }

                return left;
            }

        });
//        mHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        mHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int size = getChildCount();
        for (int i = 0; i < size; i ++) {
            View view = getChildAt(i);
            TYPE type = getType(view);
            if (type == TYPE.AUTO_BACK) {
                if (mOriginDatas == null) {
                    mOriginDatas = new HashMap<>();
                }
                mOriginDatas.put(view, new P(view.getLeft(), view.getTop()));
            } else if (type == TYPE.EDGE_TRACKER) {
                if (mEdgeViews == null) {
                    mEdgeViews = new HashSet<>();
                }
                mEdgeViews.add(view);
            }
        }
    }

    @Override
    public void computeScroll() {
        if(mHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mHelper.shouldInterceptTouchEvent(ev);
    }
}
