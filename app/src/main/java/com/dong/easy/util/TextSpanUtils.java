package com.dong.easy.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

/**
 * <pre>
 *     使用说明
 *     getBuilder        : 获取建造者
 *          setFlag           : 设置标识
 *          setForegroundColor: 设置前景色
 *          setBackgroundColor: 设置背景色
 *          setRundBackground : 设置圆角背景
 *          setQuoteColor     : 设置引用线的颜色
 *          setLeadingMargin  : 设置缩进
 *          setBullet         : 设置列表标记
 *          setScale          : 设置字体比例
 *          setXScale         : 设置字体横向比例
 *          setDeleteLine     : 设置删除线
 *          setUnderline      : 设置下划线
 *          setUpperMark      : 设置上标
 *          setDownMark       : 设置下标
 *          setBold           : 设置粗体
 *          setItalic         : 设置斜体
 *          setBoldItalic     : 设置粗斜体
 *          setFont           : 设置字体
 *          setAlign          : 设置对齐
 *          setBitmap         : 设置图片
 *          setDrawable       : 设置图片
 *          setUri            : 设置图片
 *          setResourceId     : 设置图片
 *          setClickSpan      : 设置点击事件
 *          setUrl            : 设置超链接
 *          setBlur           : 设置模糊
 *          append            : 追加样式字符串
 *          create            : 创建样式字符串
 * </pre>
 */

public class TextSpanUtils {

    private TextSpanUtils() {
    }

    /**
     * 获取建造者
     *
     * @param text 样式字符串文本
     * @return {@link Builder}
     */
    public static Builder getBuilder(Context context, @NonNull CharSequence text) {
        return new Builder(context, text);
    }

    @SuppressWarnings({"WeakerAccess", "unused"})
    public static class Builder {
        private Context context;
        private int defaultValue = 0x12000000;
        private CharSequence text;
        private Paint paint;//测量笔

        private int flag;
        @ColorInt
        private int foregroundColor;
        @ColorInt
        private int backgroundColor;
        @ColorInt
        private int quoteColor;

        private boolean isLeadingMargin;
        private int first;
        private int rest;

        private boolean isBullet;
        private int gapWidth;
        private int bulletColor;

        private float scale;
        private float xProportion;
        private boolean isDeleteLine;
        private boolean isUnderline;
        private boolean isUpperMark;
        private boolean isDownMark;
        private boolean isBold;
        private boolean isItalic;
        private boolean isBoldItalic;
        private String fontFamily;
        private Layout.Alignment align;

        //设置 图片
        private boolean imageIsBitmap;
        private Bitmap bitmap;
        private boolean imageIsDrawable;
        private Drawable drawable;
        private boolean imageIsUri;
        private Uri uri;
        private boolean imageIsResourceId;
        @DrawableRes
        private int resourceId;

        //设置圆角背景
        private boolean isRoundImageSpan;//是否需要圆角背景
        @ColorInt
        private int roundColor = -1;
        private int roundRadius = 0;
        private int offsetX = 0;
        private int offsetY = 0;

        //设置可点击超链接
        private ClickableSpan clickSpan;
        private String url;

        //设置模糊
        private boolean isBlur;
        private float radius;
        private BlurMaskFilter.Blur style;

        private SpannableStringBuilder mBuilder;

        //设置字体大小
        private boolean isTextSize;
        private int textSize;

        private Builder(Context context, @NonNull CharSequence text) {
            this.text = text;
            this.context = context;
            flag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
            foregroundColor = defaultValue;
            backgroundColor = defaultValue;
            quoteColor = defaultValue;
            scale = -1;
            xProportion = -1;
            mBuilder = new SpannableStringBuilder();
        }

        /**
         * 设置标识
         *
         * @param flag <ul>
         *             <li>{@link Spanned#SPAN_INCLUSIVE_EXCLUSIVE}</li>
         *             <li>{@link Spanned#SPAN_INCLUSIVE_INCLUSIVE}</li>
         *             <li>{@link Spanned#SPAN_EXCLUSIVE_EXCLUSIVE}</li>
         *             <li>{@link Spanned#SPAN_EXCLUSIVE_INCLUSIVE}</li>
         *             </ul>
         * @return {@link Builder}
         */
        public Builder setFlag(int flag) {
            this.flag = flag;
            return this;
        }

        /**
         * 设置前景色
         *
         * @param color 前景色
         * @return {@link Builder}
         */
        public Builder setForegroundColor(@ColorInt int color) {
            this.foregroundColor = color;
            return this;
        }

        /**
         * 设置背景色
         *
         * @param color 背景色
         * @return {@link Builder}
         */
        public Builder setBackgroundColor(@ColorInt int color) {
            this.backgroundColor = color;
            return this;
        }

        /**
         * 设置字体大小
         *
         * @param textSize 字体大小  单位 px
         * @return {@link Builder}
         */
        public Builder setTextSize(int textSize) {
            this.isTextSize = true;
            this.textSize = textSize;
            return this;
        }

        /**
         * 设置圆角背景
         *
         * @param color   颜色
         * @param round   圆角大小
         * @param offsetX X修正
         * @param offsetY Y修正
         */
        public Builder setRundBackground(@ColorInt int color, int round, int offsetX, int offsetY) {
            isRoundImageSpan = true;
            roundColor = color;
            roundRadius = round;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            return this;
        }

        /**
         * 设置引用线的颜色
         *
         * @param color 引用线的颜色
         * @return {@link Builder}
         */
        public Builder setQuoteColor(@ColorInt int color) {
            this.quoteColor = color;
            return this;
        }

        /**
         * 设置缩进
         *
         * @param first 首行缩进
         * @param rest  剩余行缩进
         * @return {@link Builder}
         */
        public Builder setLeadingMargin(int first, int rest) {
            this.first = first;
            this.rest = rest;
            isLeadingMargin = true;
            return this;
        }

        /**
         * 设置列表标记
         *
         * @param gapWidth 列表标记和文字间距离
         * @param color    列表标记的颜色
         * @return {@link Builder}
         */
        public Builder setBullet(int gapWidth, int color) {
            this.gapWidth = gapWidth;
            bulletColor = color;
            isBullet = true;
            return this;
        }

        /**
         * 设置字体比例
         *
         * @param scale 比例
         * @return {@link Builder}
         */
        public Builder setScale(float scale) {
            this.scale = scale;
            return this;
        }

        /**
         * 设置字体横向比例
         *
         * @param proportion 比例
         * @return {@link Builder}
         */
        public Builder setXScale(float proportion) {
            this.xProportion = proportion;
            return this;
        }

        /**
         * 设置删除线
         *
         * @return {@link Builder}
         */
        public Builder setDeleteLine() {
            this.isDeleteLine = true;
            return this;
        }

        /**
         * 设置下划线
         *
         * @return {@link Builder}
         */
        public Builder setUnderline() {
            this.isUnderline = true;
            return this;
        }

        /**
         * 设置上标
         *
         * @return {@link Builder}
         */
        public Builder setUpperMark() {
            this.isUpperMark = true;
            return this;
        }

        /**
         * 设置下标
         *
         * @return {@link Builder}
         */
        public Builder setDownMark() {
            this.isDownMark = true;
            return this;
        }

        /**
         * 设置粗体
         *
         * @return {@link Builder}
         */
        public Builder setBold() {
            isBold = true;
            return this;
        }

        /**
         * 设置斜体
         *
         * @return {@link Builder}
         */
        public Builder setItalic() {
            isItalic = true;
            return this;
        }

        /**
         * 设置粗斜体
         *
         * @return {@link Builder}
         */
        public Builder setBoldItalic() {
            isBoldItalic = true;
            return this;
        }

        /**
         * 设置字体
         *
         * @param fontFamily 字体
         *                   <ul>
         *                   <li>monospace</li>
         *                   <li>serif</li>
         *                   <li>sans-serif</li>
         *                   </ul>
         * @return {@link Builder}
         */
        public Builder setFont(@Nullable String fontFamily) {
            this.fontFamily = fontFamily;
            return this;
        }

        /**
         * 设置对齐
         *
         * @param align 对其方式
         *              <ul>
         *              <li>{@link Layout.Alignment#ALIGN_NORMAL}正常</li>
         *              <li>{@link Layout.Alignment#ALIGN_OPPOSITE}相反</li>
         *              <li>{@link Layout.Alignment#ALIGN_CENTER}居中</li>
         *              </ul>
         * @return {@link Builder}
         */
        public Builder setAlign(@Nullable Layout.Alignment align) {
            this.align = align;
            return this;
        }

        /**
         * 设置图片
         *
         * @param bitmap 图片位图
         * @return {@link Builder}
         */
        public Builder setBitmap(@NonNull Bitmap bitmap) {
            this.bitmap = bitmap;
            imageIsBitmap = true;
            return this;
        }

        /**
         * 设置图片
         *
         * @param drawable 图片资源
         * @return {@link Builder}
         */
        public Builder setDrawable(@NonNull Drawable drawable) {
            this.drawable = drawable;
            imageIsDrawable = true;
            return this;
        }

        /**
         * 设置图片
         *
         * @param uri 图片uri
         * @return {@link Builder}
         */
        public Builder setUri(@NonNull Uri uri) {
            this.uri = uri;
            imageIsUri = true;
            return this;
        }

        /**
         * 设置图片
         *
         * @param resourceId 图片资源id
         * @return {@link Builder}
         */
        public Builder setResourceId(@DrawableRes int resourceId) {
            this.resourceId = resourceId;
            imageIsResourceId = true;
            return this;
        }

        /**
         * 设置点击事件
         * <p>需添加view.setMovementMethod(LinkMovementMethod.getInstance())</p>
         *
         * @param clickSpan 点击事件
         * @return {@link Builder}
         */
        public Builder setClickSpan(@NonNull ClickableSpan clickSpan) {
            this.clickSpan = clickSpan;
            return this;
        }

        /**
         * 设置超链接
         * <p>需添加view.setMovementMethod(LinkMovementMethod.getInstance())</p>
         *
         * @param url 超链接
         * @return {@link Builder}
         */
        public Builder setUrl(@NonNull String url) {
            this.url = url;
            return this;
        }

        /**
         * 设置模糊
         * <p>尚存bug，其他地方存在相同的字体的话，相同字体出现在之前的话那么就不会模糊，出现在之后的话那会一起模糊</p>
         * <p>推荐还是把所有字体都模糊这样使用</p>
         *
         * @param radius 模糊半径（需大于0）
         * @param style  模糊样式<ul>
         *               <li>{@link BlurMaskFilter.Blur#NORMAL}</li>
         *               <li>{@link BlurMaskFilter.Blur#SOLID}</li>
         *               <li>{@link BlurMaskFilter.Blur#OUTER}</li>
         *               <li>{@link BlurMaskFilter.Blur#INNER}</li>
         *               </ul>
         * @return {@link Builder}
         */
        public Builder setBlur(float radius, BlurMaskFilter.Blur style) {
            this.radius = radius;
            this.style = style;
            this.isBlur = true;
            return this;
        }

        /**
         * 追加样式字符串
         *
         * @param text 样式字符串文本
         * @return {@link Builder}
         */
        public Builder append(@NonNull CharSequence text) {
            setSpan();
            this.text = text;
            return this;
        }

        /**
         * 创建样式字符串
         *
         * @return 样式字符串
         */
        public SpannableStringBuilder create() {
            setSpan();
            paint = null;
            return mBuilder;
        }

        /**
         * 获取当前 string的所占宽度
         * 如果已经调用 setTextSize() 方法可以参数传0;
         *
         * @param textSize 字体字号 单位px
         * @return 返回 字符串所占用宽度
         */
        public float getStringWidth(int textSize) {
            if (TextUtils.isEmpty(text)) {
                return 0;
            }
            if (isTextSize) {
                return getStringWidth(text.toString(), this.textSize);
            }
            return getStringWidth(text.toString(), textSize);
        }

        /**
         * 设置样式
         */
        private void setSpan() {
            if (TextUtils.isEmpty(this.text) || mBuilder == null){
                return;
            }
            int start = mBuilder.length();
            mBuilder.append(this.text);
            int end = mBuilder.length();

            if (isRoundImageSpan) {//设置 圆角 背景
                TagImageSpan tagImageSpan = new TagImageSpan(10, 10);
                tagImageSpan.setOffsetX(offsetX);
                tagImageSpan.setOffsetY(offsetY);
                mBuilder.setSpan(tagImageSpan, start, end, flag);
                isRoundImageSpan = false;
            }
            if (foregroundColor != defaultValue) {
                mBuilder.setSpan(new ForegroundColorSpan(foregroundColor), start, end, flag);
                foregroundColor = defaultValue;
            }
            if (backgroundColor != defaultValue) {
                mBuilder.setSpan(new BackgroundColorSpan(backgroundColor), start, end, flag);
                backgroundColor = defaultValue;
            }

            if (isTextSize) {
                mBuilder.setSpan(new AbsoluteSizeSpan(textSize), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                isTextSize = false;
            }

            if (isLeadingMargin) {
                mBuilder.setSpan(new LeadingMarginSpan.Standard(first, rest), start, end, flag);
                isLeadingMargin = false;
            }
            if (quoteColor != defaultValue) {
                mBuilder.setSpan(new QuoteSpan(quoteColor), start, end, 0);
                quoteColor = defaultValue;
            }
            if (isBullet) {
                mBuilder.setSpan(new BulletSpan(gapWidth, bulletColor), start, end, 0);
                isBullet = false;
            }
            if (scale != -1) {
                mBuilder.setSpan(new RelativeSizeSpan(scale), start, end, flag);
                scale = -1;
            }
            if (xProportion != -1) {
                mBuilder.setSpan(new ScaleXSpan(xProportion), start, end, flag);
                xProportion = -1;
            }
            if (isDeleteLine) {
                mBuilder.setSpan(new StrikethroughSpan(), start, end, flag);
                isDeleteLine = false;
            }
            if (isUnderline) {
                mBuilder.setSpan(new UnderlineSpan(), start, end, flag);
                isUnderline = false;
            }
            if (isUpperMark) {
                mBuilder.setSpan(new SuperscriptSpan(), start, end, flag);
                isUpperMark = false;
            }
            if (isDownMark) {
                mBuilder.setSpan(new SubscriptSpan(), start, end, flag);
                isDownMark = false;
            }
            if (isBold) {
                mBuilder.setSpan(new StyleSpan(Typeface.BOLD), start, end, flag);
                isBold = false;
            }
            if (isItalic) {
                mBuilder.setSpan(new StyleSpan(Typeface.ITALIC), start, end, flag);
                isItalic = false;
            }
            if (isBoldItalic) {
                mBuilder.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), start, end, flag);
                isBoldItalic = false;
            }
            if (fontFamily != null) {
                mBuilder.setSpan(new TypefaceSpan(fontFamily), start, end, flag);
                fontFamily = null;
            }
            if (align != null) {
                mBuilder.setSpan(new AlignmentSpan.Standard(align), start, end, flag);
                align = null;
            }
            if (imageIsBitmap || imageIsDrawable || imageIsUri || imageIsResourceId) {
                if (imageIsBitmap) {
                    mBuilder.setSpan(new ImageSpan(context, bitmap), start, end, flag);
                    bitmap = null;
                    imageIsBitmap = false;
                } else if (imageIsDrawable) {
                    mBuilder.setSpan(new ImageSpan(drawable), start, end, flag);
                    drawable = null;
                    imageIsDrawable = false;
                } else if (imageIsUri) {
                    mBuilder.setSpan(new ImageSpan(context, uri), start, end, flag);
                    uri = null;
                    imageIsUri = false;
                } else {
                    mBuilder.setSpan(new ImageSpan(context, resourceId), start, end, flag);
                    resourceId = 0;
                    imageIsResourceId = false;
                }
            }
            if (clickSpan != null) {
                mBuilder.setSpan(clickSpan, start, end, flag);
                clickSpan = null;
            }
            if (url != null) {
                mBuilder.setSpan(new URLSpan(url), start, end, flag);
                url = null;
            }
            if (isBlur) {
                mBuilder.setSpan(new MaskFilterSpan(new BlurMaskFilter(radius, style)), start, end, flag);
                isBlur = false;
            }

            flag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

        }

        /**
         * 生成shape 可以通过xml实现
         */
        private GradientDrawable getShape() {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setCornerRadius(roundRadius);
            drawable.setColor(roundColor);
            //drawable.setStroke(1, Color.parseColor("#FE4070"));
            return drawable;
        }

        private class TagImageSpan extends ImageSpan {

            private int expandWidth = 0; //设置之后可能会出现显示不全的问题，可通过TextView的 padding解决
            private int expandHeight = 0;//设置之后可能会出现显示不全的问题，可通过TextView的 padding解决
            private int offsetY = 0; //Y轴偏移量
            private int offsetX = 0; //X轴偏移量

            TagImageSpan(int expandWidth, int expandHeight) {
                super(getShape());
                this.expandWidth = expandWidth;
                this.expandHeight = expandHeight;
            }


            @Override
            public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
                int width = getWidth(text, start, end, paint);
                int height = getHeight(paint);
                getDrawable().setBounds(0, 0, width, height);
                float bgX = x + offsetX - (expandWidth * 0.5F); //使得在水平方向居中
                int bgBottom = bottom + offsetY + expandHeight / 2;//使得在垂直方向居中
                super.draw(canvas, text, start, end, bgX, top, y, bgBottom, paint);
                paint.setColor(Color.WHITE);
                canvas.drawText(text.subSequence(start, end).toString(), x, y, paint);
            }

            @Override
            public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
                return getWidth(text, start, end, paint);
            }

            void setOffsetY(int offsetY) {
                this.offsetY = offsetY;
            }

            void setOffsetX(int offsetX) {
                this.offsetX = offsetX;
            }

            /**
             * 计算span的宽度
             */
            private int getWidth(CharSequence text, int start, int end, Paint paint) {
                return Math.round(paint.measureText(text, start, end)) + expandWidth;
            }

            /**
             * 计算span的高度
             */
            private int getHeight(Paint paint) {
                Paint.FontMetrics fm = paint.getFontMetrics();
                return (int) Math.ceil(fm.descent - fm.ascent) + expandHeight;
            }
        }


        private float getStringWidth(String text, float size) {
            if (TextUtils.isEmpty(text)) {
                return 0;
            }
            if (paint == null) {
                paint = new Paint();
            } else {
                paint.reset();
            }
            paint.setTextSize(size);
            return paint.measureText(text);//得到总体长度
        }

    }
}
