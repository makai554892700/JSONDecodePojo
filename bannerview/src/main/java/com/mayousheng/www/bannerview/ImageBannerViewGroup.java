package com.mayousheng.www.bannerview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by marking on 2017/4/12.
 */

public class ImageBannerViewGroup extends ViewGroup {

    private int childrenCount;
    private int childrenWidth;
    private int childrenHeight;
    private int currentX;
    private int index = 0;
    private Scroller scroller;
    private boolean isAuto = true;
    private Timer timer = new Timer();
    private Handler autoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (++index >= childrenCount) {
                        index = 0;
                    }
                    scrollTo(childrenWidth * index, 0);
                    if (imageBannerViewGroupListener != null) {
                        imageBannerViewGroupListener.selectImage(index);
                    }
                    break;
            }
        }
    };
    private boolean isClick;
    private ImageBannerListener imageBannerListener;

    public ImageBannerListener getImageBannerListener() {
        return imageBannerListener;
    }

    public void setImageBannerListener(ImageBannerListener imageBannerListener) {
        this.imageBannerListener = imageBannerListener;
    }

    private ImageBannerViewGroupListener imageBannerViewGroupListener;

    public ImageBannerViewGroupListener getImageBannerViewGroupListener() {
        return imageBannerViewGroupListener;
    }

    public void setImageBannerViewGroupListener(ImageBannerViewGroupListener imageBannerViewGroupListener) {
        this.imageBannerViewGroupListener = imageBannerViewGroupListener;
    }

    public interface ImageBannerListener {
        void onItemClick(int index);
    }

    public ImageBannerViewGroup(Context context) {
        super(context);
        initObj();
    }

    public ImageBannerViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initObj();
    }

    public ImageBannerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initObj();
    }

    private void initObj() {
        scroller = new Scroller(getContext());
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (isAuto) {
                    autoHandler.sendEmptyMessage(0);
                }
            }
        };
        timer.schedule(timerTask, 100, 2000);
    }

    private void stopAuto() {
        isAuto = false;
    }

    private void startAuto() {
        isAuto = true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        childrenCount = getChildCount();
        if (0 == childrenCount) {
            setMeasuredDimension(0, 0);
        } else {
            measureChildren(widthMeasureSpec, heightMeasureSpec);
            View view = getChildAt(0);
            childrenHeight = view.getMeasuredHeight();
            childrenWidth = view.getMeasuredWidth();
            int totalWidth = childrenWidth * childrenCount;
            setMeasuredDimension(totalWidth, childrenHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int leftMargin = 0;
            for (int i = 0; i < childrenCount; i++) {
                View view = getChildAt(i);
                view.layout(leftMargin, 0, leftMargin + childrenWidth, childrenHeight);
                leftMargin += childrenWidth;
            }
        }
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                stopAuto();
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                currentX = (int) event.getX();
                isClick = true;
                break;
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int changeX = x - currentX;
                scrollBy(-changeX, 0);
                currentX = x;
                isClick = false;
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                index = (scrollX + childrenWidth / 2) / childrenWidth;
                if (index < 0) {
                    index = 0;
                } else if (index > childrenCount - 1) {
                    index = childrenCount - 1;
                }
                if (isClick) {
                    if (imageBannerListener != null) {
                        imageBannerListener.onItemClick(index);
                    }
                } else {
                    int dx = index * childrenWidth - scrollX;
                    scroller.startScroll(scrollX, 0, dx, 0);
                    postInvalidate();
                    if (imageBannerViewGroupListener != null) {
                        imageBannerViewGroupListener.selectImage(index);
                    }
                }
                startAuto();
                break;
        }
        return true;
    }

    public interface ImageBannerViewGroupListener {
        void selectImage(int index);
    }

}
