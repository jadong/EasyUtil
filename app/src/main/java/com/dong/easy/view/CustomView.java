package com.dong.easy.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/8/30.
 */
public class CustomView extends android.support.v7.widget.AppCompatTextView {
    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setOnClickListener(@Nullable final OnClickListener l) {
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //埋点
                if (l != null) {
                    l.onClick(v);
                }

            }
        });
    }
}
