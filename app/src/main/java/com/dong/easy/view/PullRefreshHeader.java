package com.dong.easy.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dong.easy.R;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2018/2/24.
 */
public class PullRefreshHeader {

    private View view;
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    public View getView() {
        return view;
    }

    public PullRefreshHeader(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.layout_pull_refresh_header, null);
        progressBar = (ProgressBar) view.findViewById(R.id.pb_view);
        textView = (TextView) view.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = (ImageView) view.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.mipmap.down_arrow);
        progressBar.setVisibility(View.GONE);
    }

    public void refreshing() {
        textView.setText("正在刷新");
        imageView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void pullEnable(boolean enable) {
        textView.setText(enable ? "松开刷新" : "下拉刷新");
        imageView.setVisibility(View.VISIBLE);
        imageView.setRotation(enable ? 180 : 0);
    }

    public void refreshDone() {
        progressBar.setVisibility(View.GONE);
    }
}
