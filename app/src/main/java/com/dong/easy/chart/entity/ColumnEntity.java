package com.dong.easy.chart.entity;

import android.graphics.Color;
import android.graphics.Rect;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/12/26.
 */
public class ColumnEntity {

    public ColumnEntity(Rect rect) {
        this.rect = rect;
    }

    public Rect rect;

    public int paintColor = Color.RED;

}
