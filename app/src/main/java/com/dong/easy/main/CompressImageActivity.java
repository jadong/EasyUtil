package com.dong.easy.main;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dong.easy.R;
import com.dong.easy.base.BaseActivity;
import com.dong.easy.util.CompressImageUtil;
import com.dong.easy.util.ShareUtil;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2018/12/25.
 */
public class CompressImageActivity extends BaseActivity {

    @Override
    public int getContentView() {
        return R.layout.activity_compress_image;
    }

    @Override
    public void initData() {
        ImageView iv_real_image = findViewById(R.id.iv_real_image);
        final ImageView iv_compress_image = findViewById(R.id.iv_compress_image);
        Button btn_compress = findViewById(R.id.btn_compress);

        final String url = "http://showlive-10012585.image.myqcloud.com/332bbae9-7e79-422f-950d-248831509dfe";
        Glide.with(this).load(url).into(iv_real_image);

        final Handler handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                iv_compress_image.setImageBitmap((Bitmap) msg.obj);
            }
        };

        btn_compress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        byte[] thumbData = CompressImageUtil.getImageByteArray(url);
                        Log.i("CompressImageActivity", "thumbData -- " + (thumbData.length / 1024));
                        Bitmap bitmap = ShareUtil.byteToBitmap(thumbData);
                        handler.obtainMessage(0, bitmap).sendToTarget();
                    }
                }).start();
            }
        });


    }
}
