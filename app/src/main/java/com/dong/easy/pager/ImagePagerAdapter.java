package com.dong.easy.pager;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.dong.easy.util.BlurUtil;
import com.dong.easy.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2018/4/19.
 */
public class ImagePagerAdapter extends PagerAdapter {

    private List<String> imageList = new ArrayList<>();
    private Context context;

    private int[] heightArr;

    public List<Bitmap> blurBitmapList = new ArrayList<>();

    public ImagePagerAdapter(Context context) {
        this.context = context;
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524133770241&di=e5339e941e87273d85e432dce6d844a7&imgtype=0&src=http%3A%2F%2Ff0.topitme.com%2F0%2F7a%2F63%2F113144393585b637a0o.jpg");
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524133885290&di=5cdf0f6165ea7a40f6534177d09fa47e&imgtype=0&src=http%3A%2F%2Fwww.zhlzw.com%2FUploadFiles%2FArticle_UploadFiles%2F201210%2F20121006203300515.jpg");
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524133827875&di=2e990340262d13643492c4e1168adf1f&imgtype=0&src=http%3A%2F%2Fpic5.nipic.com%2F20100116%2F4192253_155419349414_2.jpg");
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524133911553&di=800b5e319c7ef5f0e357170a687ea7de&imgtype=0&src=http%3A%2F%2Ff9.topitme.com%2F9%2F37%2F30%2F11224703137bb30379o.jpg");
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524133942228&di=cab2a7656ce6b4d30617e9c396be10a3&imgtype=0&src=http%3A%2F%2Fpic1.16pic.com%2F00%2F12%2F03%2F16pic_1203603_b.jpg");
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524133976758&di=ed3883ea9e70d1ef0e6d2d4343b1ca02&imgtype=0&src=http%3A%2F%2Fwww.33lc.com%2Farticle%2FUploadPic%2F2012-8%2F20128151131052092.jpg");
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524134083891&di=7713a9147c7d1d88faf7185827325057&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201508%2F19%2F20150819164326_x2CrR.jpeg");
    }

    public List<String> getImageList() {
        return imageList;
    }

    public int[] getHeightArr() {
        return heightArr;
    }

    @Override
    public int getCount() {
        int size = imageList.size();
        if (heightArr == null) {
            heightArr = new int[size];
        }

        return size;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Glide.with(context).asBitmap().load(imageList.get(position)).into(new ImageViewTarget<Bitmap>(imageView) {
            @Override
            protected void setResource(Bitmap bitmap) {
                if (bitmap != null) {
                    float scale = (float) bitmap.getHeight() / bitmap.getWidth();
                    heightArr[position] = (int) (scale * UIUtils.INSTANCE.getScreenWidth());
                    imageView.setImageBitmap(bitmap);

                    Bitmap doBlur = BlurUtil.doBlur(bitmap, 20, 10);
                    if (!blurBitmapList.contains(doBlur)) {
                        blurBitmapList.add(position, doBlur);
                    }

                }
            }
        });
        container.addView(imageView);
        return imageView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
