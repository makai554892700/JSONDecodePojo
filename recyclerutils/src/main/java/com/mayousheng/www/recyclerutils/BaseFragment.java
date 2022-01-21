package com.mayousheng.www.recyclerutils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.mayousheng.www.initview.ViewUtils;

public abstract class BaseFragment extends Fragment {

    private View rootView;

    protected abstract int getLayoutId();

    protected abstract void initView(View view);

    public View getRootView() {
        return rootView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        ViewUtils.initAllView(BaseFragment.class, this, rootView);
        initView(rootView);
        return rootView;
    }
}
