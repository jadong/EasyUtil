package com.dong.easy.chart;

import android.widget.HorizontalScrollView;

import com.dong.easy.R;
import com.dong.easy.base.BaseActivity;
import com.dong.easy.chart.view.CustomDragView;
import com.dong.easy.chart.view.TrimTimeView;
import com.dong.easy.view.TrimTime2View;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/12/20.
 */
public class ShowChartActivity extends BaseActivity {

    @Override
    public int getContentView() {
        return R.layout.activity_show_chart;
    }

    @Override
    public void initData() {

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

        HorizontalScrollView h_scroll_view = (HorizontalScrollView) findViewById(R.id.h_scroll_view);

    }
}