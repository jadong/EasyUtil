package com.dong.easy.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dong.easy.R;
import com.dong.easy.base.BaseActivity;
import com.dong.easy.util.UIUtils;


/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2018/4/11.
 */
public class TestAnimActivity extends BaseActivity {

    private ImageView iv_image;
    private ImageView iv_small_image;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 0) {
                firstPopRedBagAnim();
            } else if (msg.what == 1) {
                shrinkRedBagAnim();
            }

        }
    };

    @Override
    public int getContentView() {
        return R.layout.activity_anim;
    }

    @Override
    public void initData() {
        iv_image = (ImageView) findViewById(R.id.iv_image);
        iv_small_image = (ImageView) findViewById(R.id.iv_small_image);

        handler.sendEmptyMessageDelayed(0, 1000);

        iv_small_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSmall) {
                    popRedBagAnim();
                }
            }
        });

        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAnimDone) {
                    Toast.makeText(TestAnimActivity.this, "领取红包", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isSmall = false;
    private boolean isAnimDone = false;

    private void firstPopRedBagAnim() {
        iv_image.setVisibility(View.VISIBLE);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(iv_image, "scaleX", 0, 1);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(iv_image, "scaleY", 0, 1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimDone = true;
                handler.sendEmptyMessageDelayed(1, 1000);
            }
        });
        animatorSet.start();
    }

    private void shrinkRedBagAnim() {
        int[] location1 = new int[2];
        iv_image.getLocationOnScreen(location1);

        int[] location2 = new int[2];
        iv_small_image.getLocationOnScreen(location2);

        float halfWidth = iv_image.getWidth() * 1f / 2;
        float smallHalfWidth = iv_small_image.getWidth() * 1f / 2;
        final float tX = location2[0] - (location1[0] + halfWidth - smallHalfWidth);

        float halfHeight = iv_image.getHeight() * 1f / 2;
        float smallHalfHeight = iv_small_image.getHeight() * 1f / 2;
        final float tY = location2[1] - (location1[1] + halfHeight - smallHalfHeight);

        final float scale = UIUtils.INSTANCE.dip2px(100) * 1f / iv_image.getWidth();

        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(iv_image, "translationX", 0, tX);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(iv_image, "translationY", 0, tY);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(iv_image, "scaleX", 1, scale);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(iv_image, "scaleY", 1, scale);

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(objectAnimatorX, objectAnimatorY, scaleX, scaleY);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isSmall = true;
                iv_small_image.setVisibility(View.VISIBLE);
                iv_image.setVisibility(View.INVISIBLE);
                resetAnim(tX, tY, scale);
            }
        });
        animatorSet.start();
    }

    private void resetAnim(float tX, float tY, float scale) {

        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(iv_image, "translationX", tX, 0);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(iv_image, "translationY", tY, 0);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(iv_image, "scaleX", scale, 1);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(iv_image, "scaleY", scale, 1);

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(100);
        animatorSet.playTogether(objectAnimatorX, objectAnimatorY, scaleX, scaleY);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                iv_image.setVisibility(View.GONE);
            }
        });
        animatorSet.start();
    }

    private void popRedBagAnim() {
        iv_image.setVisibility(View.VISIBLE);
        iv_small_image.setVisibility(View.GONE);

        int[] location1 = new int[2];
        iv_image.getLocationOnScreen(location1);

        int[] location2 = new int[2];
        iv_small_image.getLocationOnScreen(location2);

        float halfWidth = iv_image.getWidth() * 1f / 2;
        float smallHalfWidth = iv_small_image.getWidth() * 1f / 2;
        float tX = location2[0] - (location1[0] + halfWidth - smallHalfWidth);

        float halfHeight = iv_image.getHeight() * 1f / 2;
        float smallHalfHeight = iv_small_image.getHeight() * 1f / 2;
        float tY = location2[1] - (location1[1] + halfHeight - smallHalfHeight);

        float scale = UIUtils.INSTANCE.dip2px(100) * 1f / iv_image.getWidth();

        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(iv_image, "translationX", tX, 0);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(iv_image, "translationY", tY, 0);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(iv_image, "scaleX", scale, 1);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(iv_image, "scaleY", scale, 1);

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(objectAnimatorX, objectAnimatorY, scaleX, scaleY);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimDone = true;
            }
        });
        animatorSet.start();
    }
}
