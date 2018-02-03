package com.dong.easy.chart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.dong.easy.chart.entity.PointEntity;
import com.dong.easy.chart.entity.SmallBall;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/12/26.
 */
public class LogicView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder surfaceHolder;
    private boolean isDrawing = false;
    private Canvas canvas;
    private Paint paint;
    private Random random = new Random();
    private List<SmallBall> smallBalls = new ArrayList<>();

    private SensorManager sensorManager;

    public LogicView(Context context) {
        super(context);
        init(context);
    }

    public LogicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        //画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setStrokeWidth(10f);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
//        paint.setStrokeJoin(Paint.Join.ROUND);
//        paint.setStrokeCap(Paint.Cap.ROUND);

        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent event) {
                SmallBall.ax = -event.values[0] / 16;
                SmallBall.ay = event.values[1] / 16;
                SmallBall.az = -event.values[2] / 16;

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, sensor, SensorManager.SENSOR_DELAY_GAME);

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDrawing = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                smallBalls.add(new SmallBall(getRandomColor(), (int) event.getX(), (int) event.getY()));
                drawing();
                break;
            default:
                break;
        }

        return true;
    }

    private int getRandomColor() {
        return random.nextInt() % 0xff000000;
    }

    @Override
    public void run() {
        while (isDrawing) {
            drawing();
        }
    }

    private void drawing() {

        try {

            canvas = surfaceHolder.lockCanvas();
            if (canvas == null) {
                return;
            }

            canvas.drawColor(Color.WHITE);
            for (int i = 0; i < smallBalls.size(); i++) {
                SmallBall smallBall = smallBalls.get(i);
                smallBall.drawAndMove(canvas);
            }


        } finally {
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }

        sleep();

    }

    private void sleep() {
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
