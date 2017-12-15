package com.dong.easy.image.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.alexvasilkov.gestures.commons.DepthPageTransformer;
import com.dong.easy.R;
import com.dong.easy.image.adapter.ImagePagerAdapter;
import com.dong.easy.image.data.ImageData;
import com.dong.easy.image.loader.IDownloadView;
import com.dong.easy.image.loader.ImageDownloadPresenter;
import com.dong.easy.image.view.FixViewPager;
import com.dong.easy.util.UIUtils;
import com.dong.easy.util.Views;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/12/13.
 */
public class BigViewPagerHolder implements ViewPager.OnPageChangeListener, IDownloadView {

    private View v_big_image_bg;//背景
    private Toolbar pagerToolbar;//标题
    public FixViewPager viewPager;
    private int currentPosition = -1;//当前选中

    private ImageDownloadPresenter downloadPresenter;
    private Activity activity;

    public BigViewPagerHolder(final Activity activity) {
        this.activity = activity;
        downloadPresenter = new ImageDownloadPresenter(this);

        this.v_big_image_bg = Views.find(activity, R.id.v_big_image_bg);
        this.pagerToolbar = Views.find(activity, R.id.pagerToolbar);
        this.viewPager = Views.find(activity, R.id.viewPager);
        ImageButton btn_share = Views.find(activity, R.id.btn_share);

        this.pagerToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        this.viewPager.setPageTransformer(true, new DepthPageTransformer());
        this.viewPager.addOnPageChangeListener(this);
        //分享
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getAdapter() !=  null && viewPager.getAdapter() instanceof ImagePagerAdapter
                        && currentPosition > -1) {
                    ImagePagerAdapter imagePagerAdapter = (ImagePagerAdapter) viewPager.getAdapter();
                    ImageData imageData = imagePagerAdapter.getImageData(currentPosition);
                    downloadPresenter.download(imageData.getObjURL());
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
     * 图片下载成功
     */
    @Override
    public void downloadSuccess(String imageFilePath) {
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
        UIUtils.INSTANCE.showShortToast("图片下载失败");
    }
}
