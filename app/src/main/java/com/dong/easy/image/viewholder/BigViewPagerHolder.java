package com.dong.easy.image.viewholder;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alexvasilkov.gestures.commons.DepthPageTransformer;
import com.dong.easy.R;
import com.dong.easy.image.view.FixViewPager;
import com.dong.easy.util.Views;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/12/13.
 */
public class BigViewPagerHolder {

    public View v_big_image_bg;
    public Toolbar pagerToolbar;
    public FixViewPager viewPager;

    public BigViewPagerHolder(Activity parent) {
        this.v_big_image_bg = Views.find(parent, R.id.v_big_image_bg);
        this.pagerToolbar = Views.find(parent, R.id.pagerToolbar);
        this.viewPager = Views.find(parent, R.id.viewPager);

        this.pagerToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        this.viewPager.setPageTransformer(true, new DepthPageTransformer());
    }

    public void setPagerAdapter(PagerAdapter pagerAdapter){
        this.viewPager.setAdapter(pagerAdapter);
    }

    public void setNavigationOnClickListener(View.OnClickListener onClickListener){
        this.pagerToolbar.setNavigationOnClickListener(onClickListener);
    }

    public void setUpdateState(float state){
        this.v_big_image_bg.setVisibility(state == 0f ? View.INVISIBLE : View.VISIBLE);
        this.v_big_image_bg.getBackground().setAlpha((int) (255 * state));

        this.pagerToolbar.setVisibility(state == 0f ? View.INVISIBLE : View.VISIBLE);
        this.pagerToolbar.setAlpha(state);
    }
}
