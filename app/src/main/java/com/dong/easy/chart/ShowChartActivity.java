package com.dong.easy.chart;

import com.dong.easy.R;
import com.dong.easy.base.BaseActivity;
import com.dong.easy.chart.view.CustomDragView;
import com.dong.easy.chart.view.TrimTimeView;

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
    }
}