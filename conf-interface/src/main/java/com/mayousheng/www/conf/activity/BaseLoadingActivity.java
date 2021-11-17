package com.mayousheng.www.conf.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.mayousheng.www.conf.R;

public class BaseLoadingActivity extends AppCompatActivity {

    public RelativeLayout loading;
    public ImageView leftImg, rightImg;
    private AnimatorSet animatorSet = new AnimatorSet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loading = findViewById(R.id.common_loading);
        leftImg = findViewById(R.id.common_left_img);
        rightImg = findViewById(R.id.common_right_img);
        loading.setOnClickListener(getOnLoadClickListener());
        ObjectAnimator translationX = ObjectAnimator.ofFloat(leftImg, "translationX"
                , 0, getResources().getDimension(R.dimen.size_20dp));
        translationX.setRepeatCount(10000);
        translationX.setCurrentPlayTime(10000);
        translationX.setRepeatMode(ValueAnimator.REVERSE);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(rightImg, "translationX"
                , 0, -getResources().getDimension(R.dimen.size_20dp));
        translationY.setRepeatCount(10000);
        translationY.setCurrentPlayTime(10000);
        translationY.setRepeatMode(ValueAnimator.REVERSE);
        animatorSet.playTogether(translationX, translationY);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    protected void isLoading(final boolean isLoading) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    animatorSet.start();
                } else {
                    animatorSet.cancel();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isLoading(false);
    }

    @Override
    protected void onStop() {
        isLoading(true);
        super.onStop();
    }

    private View.OnClickListener getOnLoadClickListener() {
        return view -> isLoading(false);
    }

}
