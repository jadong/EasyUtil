package com.dong.easy.chart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.dong.easy.chart.entity.ColumnEntity;

import java.util.Random;

/**
 * 排序动画演示
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/12/26.
 */
public class SortAnimView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder surfaceHolder;
    private boolean isDrawing = false;
    private Canvas canvas;
    private Paint paint;
    private ColumnEntity[] columnEntities;
    private Random random = new Random();
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            drawing();
        }
    };

    public SortAnimView(Context context) {
        super(context);
        init();
    }

    public SortAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        //画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setStrokeWidth(10f);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
//        paint.setStrokeJoin(Paint.Join.ROUND);
//        paint.setStrokeCap(Paint.Cap.ROUND);

        int len = 30;
        columnEntities = new ColumnEntity[len];
        for (int i = 0; i < len; i++) {
            Rect rect = new Rect();
            rect.top = 150 + random.nextInt(330);//(len - i) * 10;
            rect.left = 100 + i * 30;
            rect.right = 120 + i * 30;
            rect.bottom = 500;

            columnEntities[i] = new ColumnEntity(rect);
        }

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
    public void run() {
        drawing();

        insertSort2();

//        insertSort();

//        selectSort();
    }

    private void drawing() {

        try {
            canvas = surfaceHolder.lockCanvas();
            if (canvas == null) {
                return;
            }
            canvas.drawColor(Color.WHITE);

            for (int i = 0; i < columnEntities.length; i++) {
                paint.setColor(columnEntities[i].paintColor);
                columnEntities[i].paintColor = Color.RED;
                canvas.drawRect(columnEntities[i].rect, paint);
            }


        } finally {
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }

    }

    private void selectSort() {
        int len = columnEntities.length;

        for (int i = 0; i < len; i++) {
            int min = i;
            for (int j = i + 1; j < len; j++) {
                if (less(j, min)) {
                    min = j;
                }
            }

            exchange(i, min);
            drawing();
            sleep();
        }
    }

    private void insertSort() {

        int len = columnEntities.length;

        for (int i = 0; i < len; i++) {
            for (int j = i; j > 0 && less(j, j - 1); j--) {
                exchange(j, j - 1);
                drawing();
                sleep();
            }
        }
    }

    public void insertSort2() {
        int len = columnEntities.length;
        for (int i = 1; i < len; i++) {

            int temp = columnEntities[i].rect.top;

            int j;
            for (j = i; j > 0 && temp > columnEntities[j - 1].rect.top; j--) {
                columnEntities[j].rect.top = columnEntities[j - 1].rect.top;
                columnEntities[j].paintColor = Color.BLUE;
                drawing();
                sleep();
            }

            columnEntities[j].rect.top = temp;
            columnEntities[j].paintColor = Color.BLUE;
            drawing();
            sleep();
        }
    }

    private void shellSort() {
        int len = columnEntities.length;

        int h = 1;// h有序
        int z = 3;

        //递增序列
        while (h < len / z) {
            h *= z + 1;
        }

        while (h >= 1) {
            for (int i = h; i < len; i++) {
                for (int j = i; j >= h && less(j, j - h); j -= h) {
                    exchange(j, j - h);
                    drawing();
                    sleep();
                }
            }
            h /= z;
        }
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean less(int index1, int index2) {
        Rect rect1 = columnEntities[index1].rect;
        Rect rect2 = columnEntities[index2].rect;
        if (rect1.top > rect2.top) {
            return true;
        }
        return false;
    }

    public void exchange(int index1, int index2) {
        columnEntities[index2].paintColor = Color.BLUE;
        columnEntities[index1].paintColor = Color.GREEN;
        int top = columnEntities[index1].rect.top;
        columnEntities[index1].rect.top = columnEntities[index2].rect.top;
        columnEntities[index2].rect.top = top;
    }

}
