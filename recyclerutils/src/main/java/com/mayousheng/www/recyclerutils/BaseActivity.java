package com.mayousheng.www.recyclerutils;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mayousheng.www.initview.ViewUtils;


public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ViewUtils.initAllView(BaseActivity.class, this);
    }

    protected abstract int getLayout();
}
