package com.dong.easy.image.listener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dong.easy.view.LoadMoreRecyclerView;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/12/1.
 */
public interface IImageAdapterController {

    void hideSoftInput();

    Context getContext();

    LoadMoreRecyclerView getRecyclerView();

    ImageView getAnimView();

    ViewGroup getRootView();

    View getTopView();

}
