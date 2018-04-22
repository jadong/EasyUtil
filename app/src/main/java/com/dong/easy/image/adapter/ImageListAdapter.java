package com.dong.easy.image.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dong.easy.R;
import com.dong.easy.image.data.ImageData;
import com.dong.easy.image.viewholder.ImageViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ImageListAdapter extends DefaultEndlessRecyclerAdapter<RecyclerView.ViewHolder> {

    private List<ImageData> dataList = new ArrayList<>();

    private final OnImageListener listener;

    public ImageListAdapter(OnImageListener listener) {
        super();
        this.listener = listener;
    }

    public void addData(List<ImageData> dataList) {
        if (dataList == null) {
            return;
        }
        int startPos = getCount() - 1;
        startPos = startPos < 0 ? 0 : startPos;
        this.dataList.addAll(dataList);
        //notifyDataSetChanged();
        notifyItemRangeChanged(startPos, dataList.size());
    }

    public void setData(List<ImageData> dataList) {
        if (dataList == null) {
            return;
        }
        this.dataList.clear();
        addData(dataList);
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(parent);
    }

    @Override
    protected void onBindHolder(RecyclerView.ViewHolder holder, final int position) {
        ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
        imageViewHolder.initData(dataList.get(position),position);
        imageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onImageClick(position);
                }
            }
        });
    }

    @Override
    protected void onBindLoadingView(TextView loadingText) {
        loadingText.setText(R.string.loading_images);
    }

    @Override
    protected void onBindErrorView(TextView errorText) {
        errorText.setText(R.string.reload_images);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder instanceof ImageViewHolder) {
            //Glide.clear(((ImageViewHolder) holder).itemView);
        }
    }

    public static ImageView getImage(RecyclerView.ViewHolder holder) {
        if (holder instanceof ImageViewHolder) {
            return ((ImageViewHolder) holder).getImageView();
        } else {
            return null;
        }
    }

    public interface OnImageListener {
        void onImageClick(int position);
    }

}
