package com.alexvasilkov.gestures.commons.circle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircleImageView extends ImageView {

    private static final int DEFAULT_PAINT_FLAGS = Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG;
    private static final Matrix tmpMatrix = new Matrix();

    private final Paint bitmapPaint = new Paint(DEFAULT_PAINT_FLAGS);
    private final RectF rect = new RectF();

    private boolean isCircle = true;

    public CircleImageView(Context context) {
        this(context, null, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int specWidth = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        int specHeight = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        super.onMeasure(specWidth, specHeight);
    }

    @Override
    protected boolean setFrame(int left, int top, int right, int bottom) {
        boolean changed = super.setFrame(left, top, right, bottom);
        rect.set(getPaddingLeft(), getPaddingTop(),
                getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        setup();
        return changed;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (bitmapPaint.getShader() == null) {
            super.onDraw(canvas);
        } else {
            float rx = 0.5f * rect.width();
            float ry = 0.5f * rect.height();
            canvas.drawRoundRect(rect, rx, ry, bitmapPaint);
        }
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        setup();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        setup();
    }

    @Override
    public void setImageMatrix(Matrix matrix) {
        super.setImageMatrix(matrix);
        setup();
    }

    public void setCircle(boolean isCircle) {
        this.isCircle = isCircle;
        setup();
    }

    private void setup() {
        Bitmap bitmap = isCircle ? getBitmapFromDrawable(getDrawable()) : null;

        if (bitmap != null) {
            BitmapShader bitmapShader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
            tmpMatrix.set(getImageMatrix());
            tmpMatrix.postTranslate(getPaddingLeft(), getPaddingTop());
            bitmapShader.setLocalMatrix(tmpMatrix);
            bitmapPaint.setShader(bitmapShader);
        } else {
            bitmapPaint.setShader(null);
        }

        invalidate();
    }

    protected Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        } else if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else {
            throw new RuntimeException("For better performance only BitmapDrawables are supported,"
                    + " but you can override getBitmapFromDrawable() and build bitmap on your own");
        }
    }

}
