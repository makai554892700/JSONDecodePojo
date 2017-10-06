package com.mayousheng.www.jsondecodepojo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.mayousheng.www.jsondecodepojo.R;

public class IconAndTextView extends View {

    public static final int DEFAULT_COLOR = 0xffeb0021;//默认颜色
    public static final int DEFAULT_TEXT_PAINT_COLOR = 0xffffffff;//默认画板颜色
    //    public static final int DEFAULT_TEXT_PAINT_COLOR = 0xfff0f0f0;//默认画板颜色
    private static final int MAX_ALPHA = 255;//最大透明度

    //默认颜色
    private int color = 0xffeb0021;
    //图片对象
    private Bitmap iconBitmap;
    //默认显示文本
    private String text = "";
    //默认字体大小
    private int textSize = (int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());
    //内存中位图类型
    private Canvas canvas;
    //图片源
    private Bitmap bitmap;
    //图片paint
    private Paint paint;
    //透明度
    private float alpha;
    //icon范围
    private Rect iconRect;
    //文本范围
    private Rect textBound;
    //文本paint
    private Paint textPaint;

    public IconAndTextView(Context context) {
        this(context, null);
    }

    public IconAndTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //获取自定义属性的值
    public IconAndTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取IconAndTextView的TypedArray
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.IconAndTextView);
        //获取所有的属性值
        int indexCount = typedArray.getIndexCount();
        //遍历并赋值
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.IconAndTextView_icon: {
                    BitmapDrawable drawable = (BitmapDrawable) typedArray.getDrawable(index);
                    if (drawable != null) {
                        iconBitmap = drawable.getBitmap();
                    }
                    break;
                }
                case R.styleable.IconAndTextView_color: {
                    color = typedArray.getColor(index, DEFAULT_COLOR);
                    break;
                }
                case R.styleable.IconAndTextView_text: {
                    text = typedArray.getString(index);
                    break;
                }
                case R.styleable.IconAndTextView_text_size: {
                    textSize = (int) typedArray.getDimension(index, TypedValue
                            .applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
                                    getResources().getDisplayMetrics()));
                    break;
                }
            }
        }
        //回收前面的TypedArray
        typedArray.recycle();
        //设置文本的各项属性
        textBound = new Rect();
        textPaint = new Paint();
        //字体大小
        textPaint.setTextSize(textSize);
        //颜色
        textPaint.setColor(DEFAULT_TEXT_PAINT_COLOR);
        //范围
        textPaint.getTextBounds(text, 0, text.length(), textBound);
    }

    //设置图片透明状态
    public void setIconAlpha(float alpha) {
        this.alpha = alpha;
        invalidateView();
    }

    //重绘
    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    @Override
    //为位图渲染宽高的方法
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //图片的宽度
        int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft()
                - getPaddingRight(), getMeasuredHeight() - getPaddingTop()
                - getPaddingBottom() - textBound.height());
        //图片与button左边距离
        int left = getMeasuredWidth() / 2 - iconWidth / 2;
        //图片与button顶部距离
        int top = getMeasuredHeight() / 2 - (textBound.height() + iconWidth)
                / 2;
        iconRect = new Rect(left, top, left + iconWidth, top + iconWidth);
    }

    @Override
    //最终绘制
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(iconBitmap, null, iconRect, null);
        int alpha = (int) Math.ceil(MAX_ALPHA * this.alpha);
        // 内存去准备mBitmap , setAlpha , 纯色 ，xfermode ， 图标
        setupTargetBitmap(alpha);
        // 绘制原文本
        drawSourceText(canvas, alpha);
        //绘制变色的文本
        drawTargetText(canvas, alpha);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    //绘制变色的文本
    private void drawTargetText(Canvas canvas, int alpha) {
        textPaint.setColor(color);
        textPaint.setAlpha(alpha);
        int x = getMeasuredWidth() / 2 - textBound.width() / 2;
        int y = iconRect.bottom + textBound.height();
        canvas.drawText(text, x, y, textPaint);
    }

    //绘制原文本
    private void drawSourceText(Canvas canvas, int alpha) {
        textPaint.setColor(DEFAULT_TEXT_PAINT_COLOR);
        textPaint.setAlpha(MAX_ALPHA - alpha);
        int x = getMeasuredWidth() / 2 - textBound.width() / 2;
        int y = iconRect.bottom + textBound.height();
        canvas.drawText(text, x, y, textPaint);
    }

    //在内存中绘制可变色的Icon
    private void setupTargetBitmap(int alpha) {
        bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
                Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setAlpha(alpha);
        canvas.drawRect(iconRect, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        paint.setAlpha(MAX_ALPHA);
        canvas.drawBitmap(iconBitmap, null, iconRect, paint);
    }

    private static final String INSTANCE_STATUS = "instance_status";
    private static final String STATUS_ALPHA = "status_alpha";

    //当activity被系统销毁时会调用此方法
    //将malpha保存起来便于恢复数据
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
        bundle.putFloat(STATUS_ALPHA, alpha);
        return bundle;
    }

    //当系统恢复应用状态时调用此方法
    //恢复malpha数据
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            alpha = bundle.getFloat(STATUS_ALPHA);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
