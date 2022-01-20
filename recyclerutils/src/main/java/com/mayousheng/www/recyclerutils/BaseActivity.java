package com.mayousheng.www.recyclerutils;

import android.app.Activity;
import android.os.Bundle;

import com.mayousheng.www.initview.ViewUtils;


public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ViewUtils.initAllView(BaseActivity.class, this);
    }

    protected abstract int getLayout();
}
