package com.dong.easy.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.dong.easy.R;
import com.dong.easy.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2018/8/30.
 */
public class NoteAnimView extends FrameLayout {

    private List<ImageView> imageViews = new ArrayList<>();

    public NoteAnimView(Context context) {
        super(context);
    }

    public NoteAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NoteAnimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initAnimView() {
        int val = UIUtils.INSTANCE.dip2px(50);
        for (int i = 0; i < 5; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(R.mipmap.ic_note);
            LayoutParams layoutParams = new LayoutParams(val, val);
            layoutParams.gravity = Gravity.BOTTOM;
            addView(imageView, layoutParams);
            imageViews.add(imageView);
        }
    }

    public void startAnim() {
        if (imageViews.size() > 0) {
            execAnim(0);
        }
    }

    private void execAnim(final int index) {
        int val = UIUtils.INSTANCE.dip2px(90);

        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator translationY = ObjectAnimator.ofFloat(imageViews.get(index), "translationY", 0, -val);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageViews.get(index), "scaleY", 1, 0);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageViews.get(index), "scaleX", 1, 0);

        animatorSet.setDuration(3000);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                int i = index + 1;
                if (i < imageViews.size()){
                    execAnim(i);
                }
            }
        });
        animatorSet.play(translationY).with(scaleY).with(scaleX);
        animatorSet.start();
    }
}
