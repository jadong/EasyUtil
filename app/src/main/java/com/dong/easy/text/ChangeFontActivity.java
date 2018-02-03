package com.dong.easy.text;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dong.easy.R;
import com.dong.easy.base.BaseActivity;
import com.dong.easy.constant.AppConstant;
import com.dong.easy.text.view.CenterTextView;
import com.dong.easy.util.Views;

import java.io.File;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/12/20.
 */
public class ChangeFontActivity extends BaseActivity {

    private String[] fonts = {"SourceHanSansCN-ExtraLight.otf", "SourceHanSansCN-Light.otf", "zhuolang.otf", "yingxing.otf", "caoshu.ttf", "default"};
    private int flag = 0;
    private LinearLayout ll_root_view;
    private File[] files;

    @Override
    public int getContentView() {
        return R.layout.activity_change_text;
    }

    @Override
    public void initData() {

        ll_root_view = Views.find(this, R.id.ll_root_view);
        final TextView tv_text = Views.find(this, R.id.tv_text);
        final TextView tv_name = Views.find(this, R.id.tv_name);
        final TextView tv_center_text = Views.find(this, R.id.tv_center_text);
        final Button btn_change = (Button) findViewById(R.id.btn_change);
        btn_change.setOnClickListener(new View.OnClickListener() {
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


//                if (files != null) {
//                    File file = files[flag % files.length];
//                    if (file.exists()) {
//                        Typeface typeface = Typeface.createFromFile(file);
//                        tv_text.setTypeface(typeface);
//                    }
//
//                    btn_change.setText("切换字体，当前字体->" + file.getName());
//
//                    flag++;
//                }

            }
        });

        int colors[] = {Color.parseColor("#02042B"), Color.parseColor("#070543")};
        GradientDrawable bg = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            ll_root_view.setBackgroundDrawable(bg);
        } else {
            ll_root_view.setBackground(bg);
        }

        tv_text.setTextColor(Color.WHITE);
        tv_center_text.setTextColor(Color.WHITE);
        tv_name.setTextColor(Color.WHITE);

//        tv_center_text.post(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });

        File file = new File(AppConstant.INSTANCE.getFONTS_PATH(), fonts[0]);
        if (file.exists()) {
            Typeface typeface = Typeface.createFromFile(file);
            tv_center_text.setTypeface(typeface);
            tv_name.setTypeface(typeface);
        }


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                File dirFile = new File("/system/fonts/");
//                files = dirFile.listFiles();
//
//
//            }
//        }).start();
    }
}