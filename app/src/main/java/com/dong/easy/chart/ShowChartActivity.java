package com.dong.easy.chart;

import android.animation.ValueAnimator;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dong.easy.R;
import com.dong.easy.base.BaseActivity;
import com.dong.easy.chart.view.CustomDragView;
import com.dong.easy.chart.view.TrimTimeView;
import com.dong.easy.util.UIUtils;
import com.dong.easy.view.TrimTime2View;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/12/20.
 */
public class ShowChartActivity extends BaseActivity {

    private View view_white_line;
    private ImageView iv_hand_image;

    @Override
    public int getContentView() {
        return R.layout.activity_show_chart;
    }

    @Override
    public void initData() {

        view_white_line = findViewById(R.id.view_white_line);
        iv_hand_image = (ImageView) findViewById(R.id.iv_hand_image);

        final TrimTimeView trimTimeView = (TrimTimeView) findViewById(R.id.trimTimeView);
        CustomDragView customDragView = (CustomDragView) findViewById(R.id.customDragView);

        int maxLeftPosition = trimTimeView.initCutRange(10, 30);
        customDragView.setMaxLeftPosition(maxLeftPosition);
        customDragView.setDragViewListener(new CustomDragView.DragViewListener() {
            @Override
            public void onPositionChanged(int progress) {
                trimTimeView.setSlidingDistance(progress);
            }
        });

        TrimTime2View trimTime2View = (TrimTime2View) findViewById(R.id.trimTime2View);
//        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) trimTime2View.getLayoutParams();
//        layoutParams.width = (int) trimTime2View.getTotalWidth();
//        trimTime2View.setLayoutParams(layoutParams);

        int minHeight = UIUtils.INSTANCE.dip2px(30);
        final int maxHeight = UIUtils.INSTANCE.dip2px(130);
        ValueAnimator animator = ValueAnimator.ofInt(minHeight, maxHeight);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                FrameLayout.LayoutParams layoutParams_1 = (FrameLayout.LayoutParams) view_white_line.getLayoutParams();
                layoutParams_1.height = value;
                view_white_line.setLayoutParams(layoutParams_1);

                RelativeLayout.LayoutParams layoutParams_2 = (RelativeLayout.LayoutParams) iv_hand_image.getLayoutParams();
                layoutParams_2.bottomMargin = value;
                iv_hand_image.setLayoutParams(layoutParams_2);
            }
        });
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.start();
    }

}