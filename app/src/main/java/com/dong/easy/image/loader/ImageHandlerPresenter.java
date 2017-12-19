package com.dong.easy.image.loader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.dong.easy.base.BasePresenter;
import com.dong.easy.util.LogUtil;
import com.dong.easy.util.MD5;
import com.dong.easy.util.StringUtil;

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
public class ImageHandlerPresenter extends BasePresenter<IDownloadView> {

    private final int SUCCESS_0 = 0;
    private final int FAILURE_1 = 1;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (getView() == null) {
                return;
            }
            switch (msg.what) {
                case SUCCESS_0:
                    String filePath = msg.obj.toString();
                    //分享图片
                    getView().shareImage(filePath);
                    break;
                case FAILURE_1:
                    getView().downloadFailure();
                    break;
                default:
                    break;
            }
        }
    };

    public ImageHandlerPresenter(IDownloadView view) {
        super(view);
    }

    public void share(final String imageUrl) {

        final File imageFile = getImageFile(imageUrl);
        if (imageFile.exists()) {//图片已经存在
            //分享图片
            if (getView() != null) {
                getView().shareImage(imageFile.getAbsolutePath());
            }
        } else {

            //下载图片
            OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder().get()
                    .url(imageUrl)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    LogUtil.e("ImageHandlerPresenter", "onFailure--" + e.getMessage());
                    handler.obtainMessage(FAILURE_1).sendToTarget();

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (getView() == null) {
                        return;
                    }

                    //获取流
                    InputStream in = response.body().byteStream();
                    //转化为bitmap
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    try {
                        FileOutputStream fos = new FileOutputStream(imageFile);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.flush();
                        fos.close();

                        LogUtil.i("ImageHandlerPresenter", "保存图片--" + imageFile.getAbsolutePath());

                        //把文件插入到系统图库
                        MediaStore.Images.Media.insertImage(getView().getContext().getContentResolver(), imageFile.getAbsolutePath(), imageFile.getName(), null);

                        //保存图片后发送广播通知更新数据库
                        Uri uri = Uri.fromFile(imageFile);
                        getView().getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

                        handler.obtainMessage(SUCCESS_0, imageFile.getAbsolutePath()).sendToTarget();

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

    @NonNull
    private File getImageFile(String imageUrl) {

        String suffix = StringUtil.getSuffix(imageUrl, ".jpg");

        String absolutePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        File dirFile = new File(absolutePath, "EasyShare");
        dirFile.mkdirs();
        String imageFileName = MD5.convert(imageUrl) + suffix;
        return new File(dirFile, imageFileName);

    }

}
