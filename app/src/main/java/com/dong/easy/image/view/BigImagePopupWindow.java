package com.dong.easy.image.view;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.widget.PopupWindow;

import com.dong.easy.image.data.ImageData;

import java.util.List;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/12/1.
 */
public class BigImagePopupWindow extends PopupWindow {

    private Toolbar toolbar;
    private FixViewPager viewPager;
    private Context context;

    public BigImagePopupWindow(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
//        View view = LayoutInflater.from(context).inflate(R.layout.popup_big_image, null);
//
//        setContentView(view);
//        setBackgroundDrawable(new ColorDrawable());
//        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//        setFocusable(true);
//
//        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//        viewPager = (FixViewPager) view.findViewById(R.id.viewPager);
    }

    public void initData(List<ImageData> dataList,int currPosition) {

    }

}
