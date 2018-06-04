package com.dong.easy.chart.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.dong.easy.R;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2018/5/9.
 */
public class CustomDragView extends FrameLayout {

    private ViewDragHelper viewDragHelper;
    private int maxLeftPosition = -1;

    public CustomDragView(Context context) {
        super(context);
        init();
    }

    public CustomDragView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomDragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                if (child.getId() == R.id.btn_drag) {
                    return true;
                }
                return false;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if (maxLeftPosition > -1 && left > maxLeftPosition) {
                    left = maxLeftPosition;
                } else if (left > getWidth() - child.getMeasuredWidth()) {// 右侧边界
                    left = getWidth() - child.getMeasuredWidth();
                } else if (left < 0) {// 左侧边界
                    left = 0;
                }

                if (dragViewListener != null) {
                    dragViewListener.onPositionChanged(dx);
                }
                Log.i("CustomDragView", "left=" + left + " | dx=" + dx);
                return left;
            }
        });
    }

    public void setMaxLeftPosition(int maxLeftPosition) {
        this.maxLeftPosition = maxLeftPosition;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    private DragViewListener dragViewListener;

    public void setDragViewListener(DragViewListener dragViewListener) {
        this.dragViewListener = dragViewListener;
    }

    public interface DragViewListener {
        void onPositionChanged(int dx);
    }
}
