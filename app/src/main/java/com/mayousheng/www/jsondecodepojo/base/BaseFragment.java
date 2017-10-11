package com.mayousheng.www.jsondecodepojo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mayousheng.www.initview.ViewUtils;

/**
 * Created by ma kai on 2017/10/4.
 */

public abstract class BaseFragment extends Fragment {

    private View rootView;

    protected abstract int getLayoutId();

    protected abstract void initView(View view);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        ViewUtils.initAllView(BaseFragment.class, this, rootView);
        initView(rootView);
        return rootView;
    }
}
