package com.dong.easy.main;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Process;
import android.support.design.widget.FloatingActionButton;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import com.dong.easy.R;
import com.dong.easy.base.BaseActivity;
import com.dong.easy.broadcast.AppInstallReceiver;
import com.dong.easy.chart.ShowChartActivity;
import com.dong.easy.image.ImageListActivity;
import com.dong.easy.util.TextSpanUtils;
import com.dong.easy.util.UIUtils;
import com.dong.easy.util.Views;



/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/12/14.
 */
public class MainActivity extends BaseActivity {

    private FloatingActionButton btn_image;
    private FloatingActionButton btn_show_chart;

    private AppInstallReceiver appInstallReceiver;

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        btn_image = Views.find(this, R.id.btn_image);
        btn_show_chart = Views.find(this, R.id.btn_show_chart);
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
                Intent intent = new Intent(MainActivity.this, ShowChartActivity.class);
                startActivity(intent);
            }
        });

        Views.find(this, R.id.btn_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestAnimActivity.class));
            }
        });

        Views.find(this, R.id.btn_anim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        appInstallReceiver = new AppInstallReceiver();
        appInstallReceiver.register(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        appInstallReceiver.unregister(this);
    }
}
