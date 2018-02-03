package com.dong.easy.chart.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/12/29.
 */
public class SmallBall {

    private int color;
    public int x;
    public int y;
    private int z;
    private Paint p;
    private int radius;
    private float vx;
    private float vy;
    private float vz;
    public static float ax = 0;
    public static float ay = 0;
    public static float az = 0;


    public SmallBall(int color) {
        this.color = color;
        x = 0;
        y = 0;
        z = 0;
        vx = 0;
        vy = 0;
        vz = 0;
        radius = 25;
        p = new Paint();
        p.setColor(color);
        RadialGradient rg = new RadialGradient(x + 10, y - 10, radius, color + (0xffffffff - color) / 2, color, Shader.TileMode.CLAMP);

        p.setShader(rg);
        p.setAntiAlias(true);
    }

    public SmallBall(int color, int x, int y) {
        this(color);
        this.x = x;
        this.y = y;
    }

    public void drawAndMove(Canvas c) {

        draw(c);
        x += vx;
        y += vy;
        z += vz;
        vy += ay;
        vx += ax;
        vz += az;
    }

    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Canvas c) {
        RadialGradient rg = new RadialGradient(x, y, radius, 0xffdddddd, color, Shader.TileMode.CLAMP);
        p.setShader(rg);
        c.drawCircle(x, y, radius, p);

    }
}
