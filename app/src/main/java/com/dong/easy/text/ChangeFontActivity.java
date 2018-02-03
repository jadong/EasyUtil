package com.dong.easy.chart;

import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.dong.easy.R;
import com.dong.easy.base.BaseActivity;
import com.dong.easy.constant.AppConstant;
import com.dong.easy.util.Views;

import java.io.File;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/12/20.
 */
public class ShowChartActivity extends BaseActivity {

    private String[] fonts = {"xingkai.TTF", "zhuolang.otf", "yingxing.otf", "caoshu.ttf", "default"};
    private int flag = 0;

    @Override
    public int getContentView() {
        return R.layout.activity_show_chart;
    }

    @Override
    public void initData() {

        final TextView tv_text = Views.find(this, R.id.tv_text);
        findViewById(R.id.btn_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fileName = fonts[flag % fonts.length];
                if (TextUtils.equals(fileName, "default")) {
                    tv_text.setTypeface(Typeface.DEFAULT);
                } else {
                    File file = new File(AppConstant.INSTANCE.getFONTS_PATH(), fileName);
                    if (file.exists()) {
                        Typeface typeface = Typeface.createFromFile(file);
                        tv_text.setTypeface(typeface);
                    }
                }
                flag++;
            }
        });
    }
}