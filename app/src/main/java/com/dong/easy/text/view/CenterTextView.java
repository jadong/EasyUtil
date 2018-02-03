package com.dong.easy.text.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2018/1/3.
 */
public class CenterTextView extends android.support.v7.widget.AppCompatTextView {

    private StaticLayout mStaticLayout;
    private TextPaint mTextPaint;

    public CenterTextView(Context context) {
        super(context);
    }

    public CenterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CenterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init();
    }

    private void init() {
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(getTextSize());
        mTextPaint.setColor(getCurrentTextColor());
        mStaticLayout = new StaticLayout(getText(), mTextPaint, getWidth(), Layout.Alignment.ALIGN_CENTER, 1.0f, 15.0f, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mStaticLayout.draw(canvas);
    }

    @Override
    public void setTypeface(Typeface tf) {
        if (mTextPaint!= null && mTextPaint.getTypeface() != tf) {
            mTextPaint.setTypeface(tf);
            if (mStaticLayout != null) {
                requestLayout();
                invalidate();
            }
        }
    }
}
