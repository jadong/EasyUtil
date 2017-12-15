package com.dong.easy.main;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.dong.easy.R;
import com.dong.easy.base.BaseActivity;
import com.dong.easy.image.ImageListActivity;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/12/14.
 */
public class MainActivity extends BaseActivity {

    private FloatingActionButton btn_image;

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        btn_image = (FloatingActionButton) findViewById(R.id.btn_image);
        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ImageListActivity.class);
                startActivity(intent);
            }
        });
    }

}
