package com.dong.easy.image.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dong.easy.R;
import com.dong.easy.glide.GlideRoundTransform;
import com.dong.easy.image.data.ImageData;
import com.dong.easy.util.UIUtils;
import com.dong.easy.util.Views;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/12/14.
 */
public class ImageViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private int imageWidth;
    private RequestOptions requestOptions = new RequestOptions();

    public ImageViewHolder(ViewGroup parent) {
        super(Views.inflate(parent, R.layout.holder_image_item));
        imageWidth = (UIUtils.INSTANCE.getScreenWidth() - UIUtils.INSTANCE.dip2px(6f)-UIUtils.INSTANCE.dip2px(18f)) / 3;
        imageView = Views.find(itemView, R.id.imageView);
        requestOptions.transform(new GlideRoundTransform(5));
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void initData(ImageData imageData) {

        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.width = imageWidth;
        layoutParams.height = (int) ((imageData.getHeight() * 1f / imageData.getWidth() * 1f) * imageWidth);
        imageView.setLayoutParams(layoutParams);

        Glide.with(itemView.getContext())
                .load(imageData.getThumbURL())
                .apply(requestOptions)
                .into(imageView);

    }
}
