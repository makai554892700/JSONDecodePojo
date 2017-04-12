package com.mayousheng.www.bannerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marking on 2017/4/12.
 */

public class ImageBannerFrameLayout extends FrameLayout implements ImageBannerViewGroup.ImageBannerViewGroupListener {

    private ImageBannerViewGroup imageBannerViewGroup;
    private LinearLayout linearLayout;

    private ImageBannerViewGroup.ImageBannerListener imageBannerListener;

    public ImageBannerViewGroup.ImageBannerListener getImageBannerListener() {
        return imageBannerListener;
    }

    public void setImageBannerListener(ImageBannerViewGroup.ImageBannerListener imageBannerListener) {
        this.imageBannerListener = imageBannerListener;
    }

    public ImageBannerFrameLayout(Context context) {
        super(context);
        initImageBannerViewGroup();
        initDotLinearLayout();
    }

    public ImageBannerFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initImageBannerViewGroup();
        initDotLinearLayout();
    }

    public ImageBannerFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initImageBannerViewGroup();
        initDotLinearLayout();
    }

    @Override
    public void selectImage(int index) {
        int count = linearLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            ImageView imageView = (ImageView) linearLayout.getChildAt(i);
            if (i == index) {
                imageView.setImageResource(R.drawable.dot_select);
            } else {
                imageView.setImageResource(R.drawable.dot_normal);
            }
        }
    }

    private void initImageBannerViewGroup() {
        imageBannerViewGroup = new ImageBannerViewGroup(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageBannerViewGroup.setLayoutParams(layoutParams);
        addView(imageBannerViewGroup);
    }

    private void initDotLinearLayout() {
        linearLayout = new LinearLayout(getContext());
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80);
        linearLayout.setLayoutParams(lp);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        addView(linearLayout);
        LayoutParams layoutParams = (LayoutParams) linearLayout.getLayoutParams();
        layoutParams.gravity = Gravity.BOTTOM;
        linearLayout.setLayoutParams(layoutParams);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            linearLayout.setAlpha(0.5f);
        } else {
            linearLayout.getBackground().setAlpha(100);// 0 ~ 255
        }
    }

    public void addBitmaps(int width, List<Bitmap> bitmaps) {
        if (bitmaps == null) {
            return;
        }
        for (Bitmap bitmap : bitmaps) {
            addBitmapToImageBannerViewGroup(width, bitmap);
            addTotToLinearLayout();
        }
    }

    public void addBitmapsByIds(int width, int[] ids) {
        if (width == 0 || ids == null) {
            return;
        }
        List<Bitmap> bitmaps = new ArrayList<Bitmap>();
        for (int i = 0; i < ids.length; i++) {
            bitmaps.add(BitmapFactory.decodeResource(getResources(), ids[i]));
        }
        addBitmaps(width, bitmaps);
    }

    private void addTotToLinearLayout() {
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 20, 10, 20);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(R.drawable.dot_normal);
        linearLayout.addView(imageView);

    }

    private void addBitmapToImageBannerViewGroup(int width, Bitmap bitmap) {
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageBitmap(bitmap);
        imageBannerViewGroup.setImageBannerViewGroupListener(this);
        imageBannerViewGroup.setImageBannerListener(imageBannerListener);
        imageBannerViewGroup.addView(imageView);
    }

}
