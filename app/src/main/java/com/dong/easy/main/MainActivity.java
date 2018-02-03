package com.dong.easy.main;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.dong.easy.R;
import com.dong.easy.base.BaseActivity;
import com.dong.easy.chart.ShowChartActivity;
import com.dong.easy.image.ImageListActivity;
import com.dong.easy.text.ChangeFontActivity;
import com.dong.easy.util.Views;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/12/14.
 */
public class MainActivity extends BaseActivity {

    private FloatingActionButton btn_image;
    private FloatingActionButton btn_show_chart;

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        btn_image = Views.find(this,R.id.btn_image);
        btn_show_chart = Views.find(this,R.id.btn_show_chart);
        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ImageListActivity.class);
                startActivity(intent);
            }
        });
        btn_show_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChangeFontActivity.class);
                startActivity(intent);
            }
        });
    }

}
