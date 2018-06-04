package com.dong.easy.chart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.dong.easy.util.UIUtils;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2018/5/9.
 */
public class TrimTimeView extends View {

    private RectF[] defaultRectArr;

    private int lineWidth = UIUtils.INSTANCE.dip2px(3);
    private int spaceWidth = UIUtils.INSTANCE.dip2px(2);
    private float totalWidth = UIUtils.INSTANCE.getScreenWidth() - UIUtils.INSTANCE.dip2px(40);
    private int totalHeight = UIUtils.INSTANCE.dip2px(75);
    private int lineRadius = lineWidth / 2;

    private int[] lineHeightArr = {10, 20, 30, 10, 20, 20, 60, 35, 25, 10, 20, 30, 30, 40, 75, 60, 50, 40, 60, 30, 15, 40, 30, 20, 10, 30, 40, 20, 50, 40, 10, 20, 30, 10, 20, 20, 60, 35, 25, 10, 20, 30, 30, 40, 75, 60, 50, 40, 60, 30};

    private Paint paint = new Paint();

    private float startPosition = 0;
    private float endPosition = 0;

    //截取范围宽度 = 截取的时长*总宽度/总时长,
    private float timeRange = 10 * totalWidth / 30;

    public TrimTimeView(Context context) {
        super(context);
        init();
    }

    public TrimTimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TrimTimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //高度
        defaultRectArr = new RectF[lineHeightArr.length];
        for (int i = 0; i < lineHeightArr.length; i++) {
            int lineHeight = UIUtils.INSTANCE.dip2px(lineHeightArr[i]);
            RectF rectF = new RectF();
            rectF.left = i * lineWidth + i * spaceWidth;
            rectF.top = totalHeight / 2 - lineHeight / 2;
            rectF.right = rectF.left + lineWidth;
            rectF.bottom = rectF.top + lineHeight;
            defaultRectArr[i] = rectF;

            if (i == defaultRectArr.length - 1) {
                this.totalWidth = defaultRectArr[i].right;
                Log.i("TrimTimeView", "totalWidth=" + totalWidth);
            }

        }

        paint.setColor(Color.parseColor("#60FFFFFF"));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        Log.i("TrimTimeView", "startPosition=" + startPosition);
        Log.i("TrimTimeView", "endPosition=" + endPosition);
    }

    public int initCutRange(int cutTime, int totalTime) {
        timeRange = cutTime * totalWidth / totalTime;//截取范围宽度
        this.startPosition = 0;
        this.endPosition = timeRange;
        invalidate();

        return (int) (totalWidth - timeRange);//最大开始位置
    }

    public void setSlidingDistance(int distance) {
        this.startPosition += distance;
        if (this.startPosition < 0) {
            this.startPosition = 0;
        } else if (this.startPosition > totalWidth - timeRange) {
            this.startPosition = totalWidth - timeRange;
        }

        this.endPosition += distance;
        if (this.endPosition > totalWidth) {
            this.endPosition = totalWidth;
        } else if (this.endPosition < timeRange) {
            this.endPosition = timeRange;
        }

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.parseColor("#60FFFFFF"));
        for (int i = 0; i < defaultRectArr.length; i++) {
            canvas.drawRoundRect(defaultRectArr[i], lineRadius, lineRadius, paint);

        }

        paint.setColor(Color.parseColor("#E73F3F"));
        //绘制选中的
        for (int i = 0; i < defaultRectArr.length; i++) {
            RectF rectF = defaultRectArr[i];

            if (rectF.left >= startPosition && rectF.right <= endPosition) {

                if (startPosition > rectF.left && startPosition < rectF.right) {
                    rectF.left = startPosition;
                }

                if (endPosition > rectF.left && endPosition < rectF.right) {
                    rectF.right = endPosition;
                }

                canvas.drawRoundRect(rectF, lineRadius, lineRadius, paint);
            }


        }
    }


}
