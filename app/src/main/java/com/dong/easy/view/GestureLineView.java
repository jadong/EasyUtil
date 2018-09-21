package com.dong.easy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dong.easy.util.UIUtils;

//视频界面手势进度条
public class GestureLineView extends View {

    private Paint paint = new Paint();
    private Path path;
    private LinearGradient linearGradient;
    private Matrix localM;

    public GestureLineView(Context context) {
        this(context, null);
    }

    public GestureLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        linearGradient = new LinearGradient(0, 0, 0, UIUtils.INSTANCE.dip2px(130),
                Color.parseColor("#FFFFFFFF"), Color.parseColor("#00FFFFFF"), LinearGradient.TileMode.CLAMP);
        path = new Path();
        localM = new Matrix();
        paint.setAntiAlias(true);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        localM.setTranslate(0, getMeasuredHeight() - UIUtils.INSTANCE.dip2px(130));
        linearGradient.setLocalMatrix(localM);
        paint.setShader(linearGradient);
        path.moveTo(0, UIUtils.INSTANCE.dip2px(6));
        path.lineTo(0, getMeasuredHeight());
        path.lineTo(getMeasuredWidth(), getMeasuredHeight());
        path.lineTo(getMeasuredWidth(), UIUtils.INSTANCE.dip2px(11));
        path.close();
        canvas.drawPath(path, paint);

    }


}
