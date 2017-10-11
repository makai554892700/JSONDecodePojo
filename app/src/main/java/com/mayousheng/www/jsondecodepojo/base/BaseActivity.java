package com.mayousheng.www.jsondecodepojo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mayousheng.www.initview.ViewUtils;

/**
 * Created by marking on 2017/4/11.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ViewUtils.initAllView(BaseActivity.class, this);
    }

    protected abstract int getLayout();
}
