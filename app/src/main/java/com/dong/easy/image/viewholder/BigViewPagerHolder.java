package com.dong.easy.image.viewholder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.alexvasilkov.gestures.commons.DepthPageTransformer;
import com.dong.easy.R;
import com.dong.easy.image.adapter.ImagePagerAdapter;
import com.dong.easy.image.data.ImageData;
import com.dong.easy.image.loader.IDownloadView;
import com.dong.easy.image.loader.ImageHandlerPresenter;
import com.dong.easy.image.view.FixViewPager;
import com.dong.easy.util.UIUtils;
import com.dong.easy.util.Views;
import com.github.ybq.android.spinkit.style.Circle;

/**
 * 大图展示
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/12/13.
 */
public class BigViewPagerHolder implements ViewPager.OnPageChangeListener, IDownloadView {

    private View v_big_image_bg;//背景
    private Toolbar pagerToolbar;//标题
    public FixViewPager viewPager;
    private ProgressBar loadingView;

    private int currentPosition = -1;//当前选中

    private ImageHandlerPresenter imageHandlerPresenter;
    private Activity activity;

    public BigViewPagerHolder(final Activity activity) {
        this.activity = activity;
        imageHandlerPresenter = new ImageHandlerPresenter(this);

        this.v_big_image_bg = Views.find(activity, R.id.v_big_image_bg);
        this.pagerToolbar = Views.find(activity, R.id.pagerToolbar);
        this.viewPager = Views.find(activity, R.id.viewPager);
        this.loadingView = Views.find(activity, R.id.loadingView);
        ImageButton btn_share = Views.find(activity, R.id.btn_share);

        Circle circle = new Circle();
        circle.setColor(Color.parseColor("#8a2be2"));
        loadingView.setIndeterminateDrawable(circle);

        this.pagerToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        this.viewPager.setPageTransformer(true, new DepthPageTransformer());
        this.viewPager.addOnPageChangeListener(this);
        //分享
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getAdapter() !=  null && viewPager.getAdapter() instanceof ImagePagerAdapter
                        && currentPosition > -1) {
                    loadingView.setVisibility(View.VISIBLE);
                    ImagePagerAdapter imagePagerAdapter = (ImagePagerAdapter) viewPager.getAdapter();
                    ImageData imageData = imagePagerAdapter.getImageData(currentPosition);
                    imageHandlerPresenter.share(imageData.getObjURL());
                }
            }
        });
    }

    public void setPagerAdapter(PagerAdapter pagerAdapter) {
        this.viewPager.setAdapter(pagerAdapter);
    }

    public void setNavigationOnClickListener(View.OnClickListener onClickListener) {
        this.pagerToolbar.setNavigationOnClickListener(onClickListener);
    }

    public void setUpdateState(float state) {
        this.v_big_image_bg.setVisibility(state == 0f ? View.INVISIBLE : View.VISIBLE);
        this.v_big_image_bg.getBackground().setAlpha((int) (255 * state));

        this.pagerToolbar.setVisibility(state == 0f ? View.INVISIBLE : View.VISIBLE);
        this.pagerToolbar.setAlpha(state);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 分享图片
     */
    @Override
    public void shareImage(String imageFilePath) {
        loadingView.setVisibility(View.GONE);
        Intent imageIntent = new Intent(Intent.ACTION_SEND);
        imageIntent.setType("image/jpeg");
        imageIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(imageFilePath));
        activity.startActivity(Intent.createChooser(imageIntent, "分享"));
    }

    /**
     * 图片下载失败
     */
    @Override
    public void downloadFailure() {
        loadingView.setVisibility(View.GONE);
        UIUtils.INSTANCE.showShortToast("图片下载失败");
    }

    @Override
    public Context getContext() {
        return activity;
    }
}
