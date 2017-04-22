package com.mayousheng.www.jsondecodepojo.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by marking on 2017/4/18.
 */

public abstract class BaseHolder {

    private Context context;
    private View view;

    public BaseHolder(Context context, int layoutResource, ViewGroup parent) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(layoutResource, parent, false);
        onViewBind(view);
    }

    public abstract void onViewBind(View view);

    public View getView() {
        return view;
    }

}
