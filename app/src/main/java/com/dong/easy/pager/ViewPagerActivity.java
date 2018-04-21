package com.dong.easy.pager;

import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dong.easy.R;
import com.dong.easy.base.BaseActivity;
import com.dong.easy.util.AppUitl;
import com.dong.easy.util.UIUtils;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2018/4/19.
 */
public class ViewPagerActivity extends BaseActivity {

    private ViewPager viewPager;
    private ImagePagerAdapter imagePagerAdapter;
    private ImageButton v_back;
    private TextView tv_title;

    @Override
    public int getContentView() {
        return R.layout.activity_viewpager;
    }

    @Override
    public void initData() {
        AppUitl.fullScreen(this);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        imagePagerAdapter = new ImagePagerAdapter(this);

        Glide.with(this).asBitmap().load(imagePagerAdapter.getImageList().get(0)).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                float scale = (float) resource.getHeight() / resource.getWidth();
                int defaultHeight = (int) (scale * UIUtils.INSTANCE.getScreenWidth());
                initViewPager(defaultHeight);
            }
        });

    }

    private void initViewPager(final int defaultHeight) {

        viewPager.setAdapter(imagePagerAdapter);
        final int[] heightArr = imagePagerAdapter.getHeightArr();
        //为ViewPager设置高度
        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
        params.height = defaultHeight;
        viewPager.setLayoutParams(params);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == heightArr.length - 1) {
                    return;
                }

                //计算ViewPager现在应该的高度,heights[]表示页面高度的数组。
                int height = (int) ((heightArr[position] == 0 ? defaultHeight : heightArr[position])
                        * (1 - positionOffset) +
                        (heightArr[position + 1] == 0 ? defaultHeight : heightArr[position + 1])
                                * positionOffset);

                //为ViewPager设置高度
                ViewGroup.LayoutParams params = viewPager.getLayoutParams();
                params.height = height;
                viewPager.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
