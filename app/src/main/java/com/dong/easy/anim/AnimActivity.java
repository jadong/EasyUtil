package com.dong.easy.anim;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.dong.easy.R;
import com.dong.easy.base.BaseActivity;
import com.dong.easy.view.NoteAnimView;

/**
 * M = moveto(M X,Y) ：将画笔移动到指定的坐标位置
 * L = lineto(L X,Y) ：画直线到指定的坐标位置
 * H = horizontal lineto(H X)：画水平线到指定的X坐标位置
 * V = vertical lineto(V Y)：画垂直线到指定的Y坐标位置
 * C = curveto(C X1,Y1,X2,Y2,ENDX,ENDY)：三次贝赛曲线
 * S = smooth curveto(S X2,Y2,ENDX,ENDY)
 * Q = quadratic Belzier curve(Q X,Y,ENDX,ENDY)：二次贝赛曲线
 * T = smooth quadratic Belzier curveto(T ENDX,ENDY)：映射
 * A = elliptical Arc(A RX,RY,XROTATION,FLAG1,FLAG2,X,Y)：画圆弧。分别对应： x轴半径，y轴半径，x轴偏移量，弧度（0代表取小弧度，1代表取大弧度） ，方向（0取逆时针，1为顺时针），目标X坐标，目标y坐标。（当rx=ry时就是一个圆，但是终点坐标要对哈）
 * <p>
 * Z = closepath()：关闭路径
 * <p>
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2018/4/25.
 */
public class AnimActivity extends BaseActivity {

//    private NoteAnimView noteAnimView;

    @Override
    public int getContentView() {
        return R.layout.activity_anim2;
    }

    @Override
    public void initData() {

        final ImageView imageView = (ImageView) findViewById(R.id.image_view);

        imageView.setImageDrawable(getDrawable(R.drawable.vectalign_animated_vector_drawable_start_to_end));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = imageView.getDrawable();
                if (drawable instanceof Animatable) {
                    Log.i("AnimActivity","anim start");
                    Animatable animatable = (Animatable) drawable;
                    animatable.start();
                }
            }
        });

//        noteAnimView = (NoteAnimView) findViewById(R.id.noteAnimView);
//        noteAnimView.initAnimView();
//        findViewById(R.id.btn_action).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                noteAnimView.startAnim();
//            }
//        });

    }

}
