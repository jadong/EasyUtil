package com.dong.easy.image.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.alexvasilkov.gestures.commons.RecyclePagerAdapter;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.dong.easy.image.data.ImageData;
import com.dong.easy.image.listener.ImagePagerController;
import com.dong.easy.image.viewholder.BigImageViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ImagePagerAdapter extends RecyclePagerAdapter<BigImageViewHolder> {

    private List<ImageData> dataList = new ArrayList<>();

    private boolean activated;
    private ImagePagerController controller;

    public ImagePagerAdapter(ImagePagerController controller) {
        this.controller = controller;
    }

    public void addData(List<ImageData> dataList) {
        if (dataList == null) {
            return;
        }
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void setData(List<ImageData> dataList) {
        if (dataList == null) {
            return;
        }
        this.dataList.clear();
        addData(dataList);
    }

    public ImageData getImageData(int position){
        return this.dataList.get(position);
    }

    /**
     * 为了防止ViewPager持有views 而不是显示
     * 假设没有item在这个适配器 activated=false
     * 但是，一旦需要运行打开动画，应该再次激活这个适配器。
     * 默认情况下不激活适配器。
     */
    public void setActivated(boolean activated) {
        if (this.activated != activated) {
            this.activated = activated;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return !activated || dataList == null ? 0 : dataList.size();
    }

    @Override
    public BigImageViewHolder onCreateViewHolder(@NonNull ViewGroup container) {
        BigImageViewHolder holder = new BigImageViewHolder(container);
        holder.setImagePagerController(controller);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BigImageViewHolder holder, int position) {
        holder.initData(dataList.get(position));
    }

    @Override
    public void onRecycleViewHolder(@NonNull BigImageViewHolder holder) {
        super.onRecycleViewHolder(holder);

        holder.onRecycleViewHolder();
    }

    public static GestureImageView getImageView(BigImageViewHolder holder) {
        return holder.gestureImageView;
    }

}
