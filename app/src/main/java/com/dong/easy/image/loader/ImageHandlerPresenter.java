package com.dong.easy.image.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.dong.easy.base.BasePresenter;
import com.dong.easy.util.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/12/15.
 */
public class ImageDownloadPresenter extends BasePresenter<IDownloadView> {

    private String suffix = ".jpg";

    public ImageDownloadPresenter(IDownloadView view) {
        super(view);
    }

    public void download(String imageUrl) {

        suffix = ".jpg";
        int index = imageUrl.lastIndexOf(".");
        int index2 = imageUrl.lastIndexOf("?");
        if (index > -1) {
            if (index2 > -1) {
                suffix = imageUrl.substring(index, index2);
            } else {
                suffix = imageUrl.substring(index, imageUrl.length());
            }
        }

        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().get()
                .url(imageUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e("ImageDownloadPresenter", "onFailure--" + e.getMessage());
                if (getView() != null) {
                    getView().downloadFailure();
                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String absolutePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
                File dirFile = new File(absolutePath);
                dirFile.mkdirs();
                String imageFileName = System.currentTimeMillis() + suffix;
                File imageFile = new File(dirFile, imageFileName);

                //获取流
                InputStream in = response.body().byteStream();
                //转化为bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                try {
                    FileOutputStream fos = new FileOutputStream(imageFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();

                    LogUtil.i("ImageDownloadPresenter", "保存图片--" + imageFile.getAbsolutePath());

                    //更新UI
                    if (getView() != null) {
                        getView().downloadSuccess(imageFile.getAbsolutePath());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (getView() != null) {
                        getView().downloadFailure();
                    }
                }

            }
        });
    }

}
