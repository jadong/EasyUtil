package com.dong.easy.image.viewholder;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.alexvasilkov.gestures.GestureController;
import com.alexvasilkov.gestures.animation.ViewPositionAnimator;
import com.alexvasilkov.gestures.commons.RecyclePagerAdapter;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.dong.easy.R;
import com.dong.easy.glide.GlideImageTarget;
import com.dong.easy.image.data.ImageData;
import com.dong.easy.image.listener.ImagePagerController;
import com.dong.easy.util.Views;
import com.github.ybq.android.spinkit.style.Circle;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/12/1.
 */
public class BigImageViewHolder extends RecyclePagerAdapter.ViewHolder implements GestureController.OnGestureListener {

    private static final long PROGRESS_DELAY = 300L;

    public GestureImageView gestureImageView;
    private ProgressBar progress;

    private boolean gesturesDisabled;

    private ImagePagerController controller;

    public BigImageViewHolder(ViewGroup parent) {
        super(Views.inflate(parent, R.layout.holder_big_image));

        gestureImageView = Views.find(itemView, R.id.photo_full_image);
        progress = Views.find(itemView, R.id.photo_full_progress);
        Circle circle = new Circle();
        circle.setColor(Color.parseColor("#8a2be2"));
        progress.setIndeterminateDrawable(circle);
        gestureImageView.getController().getSettings()
                .setRotationEnabled(true)
                .setRestrictRotation(true)
                .setFillViewport(true)
                .setMaxZoom(3f);

        gestureImageView.getPositionAnimator().addPositionUpdateListener(
                new ViewPositionAnimator.PositionUpdateListener() {
                    @Override
                    public void onPositionUpdate(float state, boolean isLeaving) {
                        progress.setVisibility(state == 1f ? View.VISIBLE : View.INVISIBLE);
                    }
                });
        gestureImageView.getController().setOnGesturesListener(this);
    }

    public void setImagePagerController(ImagePagerController controller) {
        this.controller = controller;
        gestureImageView.getController().enableScrollInViewPager(controller.getViewPager());
    }

    public void initData(ImageData imageData) {

        // 临时禁止触摸
        if (!gesturesDisabled) {
            gestureImageView.getController().getSettings().disableGestures();
            gesturesDisabled = true;
        }

        progress.animate().setStartDelay(PROGRESS_DELAY).alpha(1f);

        RequestOptions requestOptions = new RequestOptions()
                .dontAnimate()
                .placeholder(gestureImageView.getDrawable())
                .dontAnimate();

        Glide.with(gestureImageView.getContext())
                .asBitmap()
                .load(imageData.getObjURL())
                .apply(requestOptions)
                .thumbnail(Glide.with(gestureImageView.getContext())
                        .asBitmap()
                        .load(imageData.getThumbURL()))
                .listener(new RequestListener<Bitmap>() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Bitmap> target, boolean b) {
                        progress.animate().alpha(0f);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap bitmap, Object o, Target<Bitmap> target, DataSource dataSource, boolean b) {
                        progress.animate().cancel();
                        progress.animate().alpha(0f);

                        //启用触摸
                        if (gesturesDisabled) {
                            gestureImageView.getController().getSettings().enableGestures();
                            gesturesDisabled = false;
                        }
                        return false;
                    }

                }).into(new GlideImageTarget(gestureImageView));

    }

    public void onRecycleViewHolder() {

        if (gesturesDisabled) {
            gestureImageView.getController().getSettings().enableGestures();
            gesturesDisabled = false;
        }

        //Glide.clear(gestureImageView);

        progress.animate().cancel();
        progress.setAlpha(0f);

        gestureImageView.setImageDrawable(null);
    }


    @Override
    public void onDown(@NonNull MotionEvent event) {

    }

    @Override
    public void onUpOrCancel(@NonNull MotionEvent event) {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent event) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(@NonNull MotionEvent event) {
        if (controller != null) {
            controller.onExitImagePager();
        }
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent event) {

    }

    @Override
    public boolean onDoubleTap(@NonNull MotionEvent event) {
        return false;
    }
}
