package com.dong.easy.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.URLUtil;

import junit.framework.Assert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ShareUtil {

    private static final String TAG = "ShareUtil";
    private static final int MAX_DECODE_PICTURE_SIZE = 1920 * 1440;

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        if (bmp == null) {
            return null;
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle, int quality) {
        if (bmp == null) {
            return null;
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, quality, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static byte[] bmpToByteArray2(final Bitmap bmp, final boolean needRecycle) {
        int i;
        int j;
        if (bmp.getHeight() > bmp.getWidth()) {
            i = bmp.getWidth();
            j = bmp.getWidth();
        } else {
            i = bmp.getHeight();
            j = bmp.getHeight();
        }

        Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);

        while (true) {
            localCanvas.drawBitmap(bmp, new Rect(0, 0, i, j), new Rect(0, 0, i, j), null);
            if (needRecycle)
                bmp.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(CompressFormat.JPEG, 100,
                    localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;
            } catch (Exception e) {
                // F.out(e);
            }
            i = bmp.getHeight();
            j = bmp.getHeight();
        }


    }

    public static byte[] getHtmlByteArray(final String url) {
        URL htmlUrl = null;
        InputStream inStream = null;
        try {
            htmlUrl = new URL(url);
            URLConnection connection = htmlUrl.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inStream = httpConnection.getInputStream();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = inputStreamToByte(inStream);

        return data;
    }

    public static byte[] inputStreamToByte(InputStream is) {
        try {
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            int ch;
            while ((ch = is.read()) != -1) {
                bytestream.write(ch);
            }
            byte imgdata[] = bytestream.toByteArray();
            bytestream.close();
            return imgdata;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] readFromFile(String fileName, int offset, int len) {
        if (fileName == null) {
            return null;
        }

        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }

        if (len == -1) {
            len = (int) file.length();
        }

        Log.d(TAG, "readFromFile : offset = " + offset + " len = " + len + " offset + len = " + (offset + len));

        if (offset < 0) {
            Log.e(TAG, "readFromFile invalid offset:" + offset);
            return null;
        }
        if (len <= 0) {
            Log.e(TAG, "readFromFile invalid len:" + len);
            return null;
        }
        if (offset + len > (int) file.length()) {
            Log.e(TAG, "readFromFile invalid file len:" + file.length());
            return null;
        }

        byte[] b = null;
        try {
            RandomAccessFile in = new RandomAccessFile(fileName, "r");
            b = new byte[len]; // 创建合适文件大小的数组
            in.seek(offset);
            in.readFully(b);
            in.close();

        } catch (Exception e) {
            Log.e(TAG, "readFromFile : errMsg = " + e.getMessage());
            e.printStackTrace();
        }
        return b;
    }

    public static Bitmap extractThumbNail(final String path, final int height, final int width, final boolean crop) {
        Assert.assertTrue(path != null && !path.equals("") && height > 0 && width > 0);

        BitmapFactory.Options options = new BitmapFactory.Options();

        try {
            options.inJustDecodeBounds = true;
            Bitmap tmp = BitmapFactory.decodeFile(path, options);
            if (tmp != null) {
                tmp.recycle();
                tmp = null;
            }

            Log.d(TAG, "extractThumbNail: round=" + width + "x" + height + ", crop=" + crop);
            final double beY = options.outHeight * 1.0 / height;
            final double beX = options.outWidth * 1.0 / width;
            Log.d(TAG, "extractThumbNail: extract beX = " + beX + ", beY = " + beY);
            options.inSampleSize = (int) (crop ? (beY > beX ? beX : beY) : (beY < beX ? beX : beY));
            if (options.inSampleSize <= 1) {
                options.inSampleSize = 1;
            }

            // NOTE: out of memory error
            while (options.outHeight * options.outWidth / options.inSampleSize > MAX_DECODE_PICTURE_SIZE) {
                options.inSampleSize++;
            }

            int newHeight = height;
            int newWidth = width;
            if (crop) {
                if (beY > beX) {
                    newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
                } else {
                    newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
                }
            } else {
                if (beY < beX) {
                    newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
                } else {
                    newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
                }
            }

            options.inJustDecodeBounds = false;

            Bitmap bm = BitmapFactory.decodeFile(path, options);
            if (bm == null) {
                Log.e(TAG, "bitmap decode failed");
                return null;
            }

            final Bitmap scale = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
            if (scale != null) {
                bm.recycle();
                bm = scale;
            }

            if (crop) {
                final Bitmap cropped =
                        Bitmap.createBitmap(bm, (bm.getWidth() - width) >> 1, (bm.getHeight() - height) >> 1, width,
                                height);
                if (cropped == null) {
                    return bm;
                }

                bm.recycle();
                bm = cropped;
            }
            return bm;

        } catch (final OutOfMemoryError e) {
            Log.e(TAG, "decode bitmap failed: " + e.getMessage());
            options = null;
        }

        return null;
    }


    /**
     * 从图片url或者resId获取图片的字节数组 。优先从url获取 by mengweiping
     */
    public static byte[] getThumbData(Context context, String url, int imgResId, BitmapFactory.Options options) {
        if (context == null) {
            return null;
        }
        if (TextUtils.isEmpty(url) || !URLUtil.isValidUrl(url)) {
            return getThumbDataFromResId(context, imgResId);
        }
        byte[] thumbData = null;
        Bitmap thumb = loadLocalImage(url, options);
        if (null == thumb) {
            thumb = loadImgFromUrl(url, options);
        }
        if (thumb != null) {
            thumbData = ShareUtil.bmpToByteArray(thumb, true);
        }
        if (thumbData != null) {
            if (thumbData.length > 32 * 1024) {
                if (options != null) {
                    options.inSampleSize = options.inSampleSize + 1;
                } else {
                    options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    options.inPurgeable = true;
                    options.inInputShareable = true;
                    options.inSampleSize = 2;
                }
                thumbData = null;
                /**thumbData超过32K将导致无法分享到微信*/
                return getThumbData(context, url, imgResId, options);
            }
        }
        if (thumbData == null) {
            return getThumbDataFromResId(context, imgResId);
        } else {
            return thumbData;
        }
    }

    public static byte[] compressThumbData(Context context, String url) {
        if (context == null) {
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = 1;
        Bitmap thumb = loadImgFromUrl(url, options);

        int qualitySize = 100;
        byte[] thumbData = bmpToByteArray(thumb, false, qualitySize);

        while (thumbData.length > 32 * 1024 && qualitySize > 10) {
            qualitySize -= 10;
            thumbData = bmpToByteArray(thumb, false, qualitySize);
        }
        return thumbData;
    }

    /**
     * 从图片url获取图片的字节数组  by mengweiping
     */
    public static byte[] getThumbDataFromUrl(Context context, String url) {
        return getThumbDataFromUrl(context, url, null);
    }

    /**
     * 从图片url获取图片的字节数组  by mengweiping
     */
    public static byte[] getThumbDataFromUrl(Context context, String url, BitmapFactory.Options options) {
        if (!URLUtil.isValidUrl(url)) {
            return null;
        }
        byte[] thumbData = null;
        Bitmap thumb = loadLocalImage(url, options);
        if (null == thumb) {
            thumb = loadImgFromUrl(url, options);
        }
        if (thumb != null) {
            thumbData = ShareUtil.bmpToByteArray(thumb, true);
        }
        if (thumb != null && !thumb.isRecycled()) {
            thumb.recycle();
            thumb = null;
        }
        return thumbData;
    }

    private static BitmapFactory.Options getSampleBitmapOpt(int inSampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

    /**
     * 从图片resId获取图片的字节数组 。by mengweiping
     */
    public static byte[] getThumbDataFromResId(Context context, int imgResId) {
        if (imgResId <= 0) {
            return null;
        }
        int sampleSize = 1;
        Bitmap thumb = BitmapFactory
                .decodeResource(context.getResources(), imgResId, getSampleBitmapOpt(sampleSize));
        byte[] thumbData = bmpToByteArray(thumb, true);
        while (thumbData != null && thumbData.length > 32 * 1024) {
            if (thumb != null && !thumb.isRecycled()) {
                thumb.recycle();
                thumb = null;
            }
            sampleSize++;
            thumb = BitmapFactory
                    .decodeResource(context.getResources(), imgResId, getSampleBitmapOpt(sampleSize));
            thumbData = ShareUtil.bmpToByteArray(thumb, true);
        }
        if (thumb != null && !thumb.isRecycled()) {
            thumb.recycle();
            thumb = null;
        }
        return thumbData;
    }

    public static Bitmap loadImgFromUrl(String url) {
        return loadImgFromUrl(url, null);
    }

    public static Bitmap loadImgFromUrl(String url, BitmapFactory.Options options) {
        Bitmap bitmap = null;
        if (url != null && !url.equals("")) {
            InputStream is = null;
            try {
                URL urlGetImg = new URL(url);
                is = urlGetImg.openStream();
                if (options == null) {
                    bitmap = BitmapFactory.decodeStream(is);
                } else {
                    bitmap = BitmapFactory.decodeStream(is, null, options);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                // handle exception  is ok
            } finally {
                try {
                    if (is != null) {
                        is.close();
                        is = null;
                    }
                } catch (Exception e) {
                }
            }
        }
        return bitmap;
    }

    public static Bitmap loadLocalImage(String url) {
        return loadLocalImage(url, null);
    }

    public static Bitmap loadLocalImage(String url, BitmapFactory.Options options) {
        Bitmap bitmap = null;
        FileInputStream fis = null;
        byte[] data;
        try {
            String[] name = url.split("/");
            String imgName = name[name.length - 2] + name[name.length - 1];

            File imgFile = new File("/sdcard/jumei/jmframe/cache/pics/"
                    + MD5.convert(imgName));
            if (!imgFile.exists()) {
                return null;
            }
            fis = new FileInputStream(imgFile);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                baos.write(buffer, 0, length);
            }
            data = baos.toByteArray();

            baos.flush();

            try {
                if (options == null) {
                    bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                } else {
                    bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
                }
            } catch (OutOfMemoryError e) {
                bitmap = null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                    fis = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    /**
     * 从本地获取图片的字节数组 。by mengweiping
     */
    public static byte[] getThumbDataFromPath(String path, int maxSize) {
        if (TextUtils.isEmpty(path) || !new File(path).exists()) {
            return null;
        }
        int sampleSize = 1;
        Bitmap thumb = BitmapFactory.decodeFile(path, getSampleBitmapOpt(sampleSize));
        byte[] thumbData = bmpToByteArray(thumb, true);
        while (thumbData != null && thumbData.length >= maxSize) {
            if (!thumb.isRecycled()) {
                thumb.recycle();
            }
            sampleSize++;
            thumb = BitmapFactory.decodeFile(path, getSampleBitmapOpt(sampleSize));
            thumbData = bmpToByteArray(thumb, true);
        }
        if (thumb != null && !thumb.isRecycled()) {
            thumb.recycle();
        }
        return thumbData;
    }


    public static Bitmap byteToBitmap(byte[] imgByte) {
        InputStream input = null;
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        input = new ByteArrayInputStream(imgByte);
        SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(
                input, null, options));
        bitmap = (Bitmap) softRef.get();
        if (imgByte != null) {
            imgByte = null;
        }

        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bitmap;
    }


}
