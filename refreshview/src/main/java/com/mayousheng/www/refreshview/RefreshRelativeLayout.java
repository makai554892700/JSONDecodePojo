package com.mayousheng.www.refreshview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

/**
 * Created by marking on 2016/12/6.
 * 下拉刷新RelativeLayout，默认都可以下拉刷新，如果需要改变可以设置RefreshListener实时改变状态
 */
public class RefreshRelativeLayout extends RelativeLayout {

    private int max_y = 240;//最大下拉距离
    private int heardHeight = 120;//头视图高度
    private ImageView heard;//头视图
    private boolean canDown = true;//是否可以下拉刷新(如当listview拉到顶端等时机)
    public RefreshListener refreshListener = null;//事件监听
    private ObjectAnimator animator;//头视图刷新动画
    private int heardState = IN_NORMAL;//头视图状态
    private View childView;

    private int down_y = 0;//当list刚滑到顶部时y轴坐标
    private int current_y = 0;//当前按下时y轴坐标
    private int last_down_y = 0;//上次y轴坐标

    private static final int IN_NORMAL = 0;//正处于隐藏状态/正常
    private static final int IN_PULL = 1;//正处于下拉状态
    private static final int IN_REFRESH = 2;//正处于刷新状态

    //设置是否可以下拉刷新
    public boolean isCanDown() {
        return canDown;
    }

    //获取是否可以下拉刷新
    public void setCanDown(boolean canDown) {
        this.canDown = canDown;
    }

    //获取刷新监听
    public RefreshListener getRefreshListener() {
        return refreshListener;
    }

    //设置刷新监听
    public void setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public RefreshRelativeLayout(Context context) {
        super(context);
    }

    public RefreshRelativeLayout(Context context, View view) {
        super(context);
        addView(view);
    }

    public RefreshRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private OnTouchListener childOnTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (view instanceof AbsListView) {
                if (((AbsListView) view).getCount() == 0) {// 没有item的时候也可以下拉刷新
                    setCanDown(true);
                } else {
                    setCanDown(((AbsListView) view).getFirstVisiblePosition() == 0
                            && ((AbsListView) view).getChildAt(0).getTop() >= 0);// 滑到AbsListView的顶部了
                }
                if (canDown && down_y == 0) {
                    down_y = current_y;
                }
            } else {
                if (view instanceof ScrollView) {
                    int scrollY = view.getScrollY();
                    if (scrollY == 0) {//滑动到了顶端
                        setCanDown(true);
                    } else {
                        setCanDown(false);
                    }
                }
            }
            return false;
        }
    };

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (childView == null) {
            childView = getChildAt(0);
            if (childView != null) {
                childView.setOnTouchListener(childOnTouchListener);
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        current_y = (int) event.getRawY();
        int move_y = down_y == 0 ? 0 : current_y - down_y;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                down_y = 0;
                if (canDown && refreshListener != null) {
                    refreshListener.onDown();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (canDown && move_y > 0 && heardState != IN_REFRESH) {
                    heardState = IN_PULL;
                    if (move_y >= (max_y + heardHeight)) {
                        setMarginTop(heard, max_y);
                        setRotate(heard, 180);
                    } else {
                        setMarginTop(heard, move_y - heardHeight);
                        setRotate(heard, 180 * ((move_y - heardHeight) * 1.0f / max_y));
                    }
                    if (refreshListener != null) {
                        refreshListener.onMove();
                    }
                }
                if (heardState == IN_PULL && last_down_y > current_y) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (canDown && heardState == IN_PULL) {
                    if (move_y >= (max_y + heardHeight) / 2) {
                        startRotate();
                        if (refreshListener != null) {
                            refreshListener.onUp();
                        }
                    } else {
                        setMarginTop(heard, -heardHeight);
                    }
                }
                if (heardState != IN_REFRESH) {
                    heardState = IN_NORMAL;
                }
                down_y = 0;
                break;
        }
        last_down_y = current_y;
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onAttachedToWindow() {//在视图初始化完成显示后添加视图，使视图在最顶层
        super.onAttachedToWindow();
        initHeardView();
    }

    //初始化头视图
    private void initHeardView() {
        if (heard == null) {
            heard = new ImageView(getContext());
            heard.setImageResource(R.drawable.refresh);
            heard.setScaleType(ImageView.ScaleType.FIT_XY);
            LayoutParams layoutParams = new LayoutParams(heardHeight, heardHeight);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            layoutParams.setMargins(0, -heardHeight, 0, 0);
            heard.setLayoutParams(layoutParams);
            addView(heard);
            animator = ObjectAnimator.ofFloat(heard, "rotation", 0, 180, 360);
            animator.setRepeatCount(Integer.MAX_VALUE);
            animator.setDuration(1000);//1s一个周期
        }
    }

    //设置与顶部的距离
    private void setMarginTop(View view, int top) {
        if (view != null) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.setMargins(0, top, 0, 0);
            view.setLayoutParams(layoutParams);
        }
    }

    //设置旋转角度
    private void setRotate(View view, float rotate) {
        if (view != null) {
            view.clearAnimation();
            ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", rotate);
            animator.setDuration(0);
            animator.start();
        }
    }

    //开始动画
    public void startRotate() {
        if (animator != null && !animator.isRunning()) {
            setMarginTop(heard, max_y / 2);
            animator.start();
            heardState = IN_REFRESH;
        }
    }

    //结束动画
    public void endRotate() {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
            setMarginTop(heard, -heardHeight);
            heardState = IN_NORMAL;
        }
    }

    public interface RefreshListener {
        //按下事件
        public void onDown();

        //下拉中
        public void onMove();

        //释放了
        public void onUp();
    }

}
