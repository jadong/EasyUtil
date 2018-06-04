package com.dong.easy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/8/30.
 */
public class CustomView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder surfaceHolder;
    private Canvas canvas;//绘图的画布
    private boolean isStart;//控制绘画线程

    private final int TIME_IN_FRAME = 30;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
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

        while (isStart) {

            //取得更新之前的时间
            long startTime = System.currentTimeMillis();

            //在这里加上线程安全锁
            synchronized (surfaceHolder) {

                drawLogic();

            }

            //取得更新结束的时间
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

    private void drawLogic() {
        try {
            canvas = surfaceHolder.lockCanvas();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
}
