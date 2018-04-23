package com.dong.easy.pager;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dong.easy.R;
import com.dong.easy.base.BaseActivity;
import com.dong.easy.util.AppUitl;
import com.dong.easy.util.BlurUtil;
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
    private TextView tv_title_1;
    private FrameLayout ll_title_layout;
    private View view_title_bg;

    private AppBarLayout appbar;

    private CollapsingToolbarLayout collapsing_toolbar;

    @Override
    public int getContentView() {
        return R.layout.activity_viewpager;
    }

    @Override
    public void initData() {
        AppUitl.fullScreen(this);
        collapsing_toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        v_back = (ImageButton) findViewById(R.id.v_back);
        ll_title_layout = (FrameLayout) findViewById(R.id.ll_title_layout);
        view_title_bg = findViewById(R.id.view_title_bg);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title_1 = (TextView) findViewById(R.id.tv_title_1);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        imagePagerAdapter = new ImagePagerAdapter(this);

        Glide.with(this).asBitmap().load(imagePagerAdapter.getImageList().get(0)).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                float scale = (float) resource.getHeight() / resource.getWidth();
                int defaultHeight = (int) (scale * UIUtils.INSTANCE.getScreenWidth());
                Bitmap doBlur = BlurUtil.doBlur(resource, 15, 10);
                imagePagerAdapter.blurBitmapList.add(0, doBlur);
                collapsing_toolbar.setContentScrim(new BitmapDrawable(doBlur));
                initViewPager(defaultHeight);
            }
        });

        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

//                Log.i("ViewPager", "verticalOffset=" + verticalOffset + " | getTotalScrollRange =" + appBarLayout.getTotalScrollRange());

                if (appBarLayout.getTotalScrollRange() > 0) {
                    float alpha = Math.abs(verticalOffset) * 1f / appBarLayout.getTotalScrollRange();
                    Log.i("ViewPager", "alpha=" + alpha);

                    if (verticalOffset == 0) {//展开
                        tv_title.setAlpha(0);
                    } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {//折叠
                        tv_title.setAlpha(1);
                    } else {
                        tv_title.setAlpha(alpha);
                    }
                }

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
                collapsing_toolbar.setContentScrim(new BitmapDrawable(imagePagerAdapter.blurBitmapList.get(position)));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
