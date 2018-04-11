package com.dong.easy.video;

import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.dong.easy.R;
import com.dong.easy.base.BaseActivity;
import com.dong.easy.util.UIUtils;
import com.dong.easy.video.adapter.VideoFragmentAdapter;
import com.dong.easy.video.view.VerticalViewPager;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2018/2/5.
 */
public class VideoActivity extends BaseActivity {

    private static final float MIN_SCALE = 0.75f;
    private static final float MIN_ALPHA = 0.75f;

    private int lastPosition = 0;

    @Override
    public void beforeSetContentView() {
        super.beforeSetContentView();
        //防止播放器首次初始化时黑色闪屏问题
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_video;
    }

    @Override
    public void initData() {
        VerticalViewPager verticalViewPager = (VerticalViewPager) findViewById(R.id.verticalviewpager);

        final VideoFragmentAdapter videoFragmentAdapter = new VideoFragmentAdapter(getSupportFragmentManager());
        verticalViewPager.setAdapter(videoFragmentAdapter);
        verticalViewPager.setOffscreenPageLimit(5);
        verticalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("VideoActivity", "---onPageSelected--" + position);
                videoFragmentAdapter.getFragment(lastPosition).startPlay();
                if (lastPosition < position) {
                    //下个页面提前播放
                    VideoFragment videoFragment = videoFragmentAdapter.getNextFragment(position);
                    if (videoFragment != null) {
                        videoFragment.preLoading();
                    }
                } else if (lastPosition > position) {
                    //上个页面停止播放
//                    VideoFragment videoFragment = videoFragmentAdapter.getFragment(lastPosition);
//                    if (videoFragment != null) {
//                        videoFragment.stopPlay();
//                    }
                }

                lastPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == VerticalViewPager.SCROLL_STATE_IDLE) {
                    Log.i("VideoActivity", "---VerticalViewPager.SCROLL_STATE_IDLE--");
                }
            }
        });

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                videoFragmentAdapter.getFragment(lastPosition).startPlay();

                //下一个提前加载
                VideoFragment nextFragment = videoFragmentAdapter.getNextFragment(lastPosition);
                if (nextFragment != null) {
                    nextFragment.preLoading();
                }
            }
        }, 500);

        //verticalViewPager.setPageMargin(UIUtils.INSTANCE.dip2px(16));
        //verticalViewPager.setPageMarginDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_green_dark)));

    }
}
