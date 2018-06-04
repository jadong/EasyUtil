package com.dong.easy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.dong.easy.util.UIUtils;

import java.util.Random;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/8/30.
 */
public class TrimTime2View extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder surfaceHolder;
    private Canvas canvas;//绘图的画布
    private boolean isStart;//控制绘画线程

    private int TIME_IN_FRAME = 1000;

    private int lineWidth = UIUtils.INSTANCE.dip2px(3);
    private int spaceWidth = UIUtils.INSTANCE.dip2px(2);
    private float totalWidth = UIUtils.INSTANCE.getScreenWidth() - UIUtils.INSTANCE.dip2px(40);
    private int totalHeight = UIUtils.INSTANCE.dip2px(75);
    private int lineRadius = lineWidth / 2;
    private Paint paint = new Paint();

    private SparseIntArray lineHeightArr = new SparseIntArray();
    private LineEntity[] defaultRectArr;

    public TrimTime2View(Context context) {
        super(context);
        init();
    }

    public TrimTime2View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TrimTime2View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);

//        setZOrderMediaOverlay(true);
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);

        paint.setColor(Color.parseColor("#60FFFFFF"));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        int maxTime = 60;
        int minTime = 15;

        int minLen = (UIUtils.INSTANCE.getScreenWidth() - spaceWidth) / (lineWidth + spaceWidth);

        int maxLen = maxTime * minLen / minTime;

        TIME_IN_FRAME = 1000 / (maxLen / 60);

        Random random = new Random();
        for (int i = 0; i < maxLen; i++) {
            int value = random.nextInt(75);
            value = value < 10 ? value + 10 : value;
            lineHeightArr.put(i, value);
        }

//        Log.i("TrimTime2View", "lineHeightArr-" + lineHeightArr.toString());

        //高度
        defaultRectArr = new LineEntity[lineHeightArr.size()];
        for (int i = 0; i < lineHeightArr.size(); i++) {
            int lineHeight = UIUtils.INSTANCE.dip2px(lineHeightArr.get(i));
            RectF rectF = new RectF();
            rectF.left = i * lineWidth + i * spaceWidth;
            rectF.top = totalHeight / 2 - lineHeight / 2;
            rectF.right = rectF.left + lineWidth;
            rectF.bottom = rectF.top + lineHeight;
            defaultRectArr[i] = new LineEntity(rectF);

//            if (i == defaultRectArr.length - 1) {
//                this.totalWidth = defaultRectArr[i].right;
//            }

        }

        totalWidth = lineHeightArr.size() * lineWidth + (lineHeightArr.size() - 1) * spaceWidth;
    }

    public float getTotalWidth() {
        return totalWidth;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension((int) totalWidth, getMeasuredHeight());
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isStart = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isStart = false;
    }

    @Override
    public void run() {


        int index = 0;
        while (isStart) {

            //更新开始时间
            long startTime = System.currentTimeMillis();

            try {
                canvas = surfaceHolder.lockCanvas();
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                for (int i = 0; i < lineHeightArr.size(); i++) {
                    LineEntity lineEntity = defaultRectArr[i];
                    if (lineEntity.isRed) {
                        paint.setColor(Color.parseColor("#E73F3F"));
                    } else {
                        paint.setColor(Color.parseColor("#60FFFFFF"));
                    }
                    canvas.drawRoundRect(lineEntity.rectF, lineRadius, lineRadius, paint);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

            if (index == defaultRectArr.length) {
                break;
            }

            defaultRectArr[index].isRed = true;
            index++;

            //更新结束时间
            long endTime = System.currentTimeMillis();

            //计算出一次更新的毫秒数
            int diffTime = (int) (endTime - startTime);

            //保证每次更新时间为指定帧数
            while (diffTime <= TIME_IN_FRAME) {
                diffTime = (int) (System.currentTimeMillis() - startTime);
                //线程等待
                Thread.yield();
            }

        }

    }

    /**
     * 绘制逻辑
     */
    private void drawLogic(int index) {


        //加上线程安全锁
        synchronized (surfaceHolder) {

            try {
                canvas = surfaceHolder.lockCanvas();

                canvas.drawRoundRect(defaultRectArr[index].rectF, lineRadius, lineRadius, paint);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

        }

    }
}
