package com.mayousheng.www.recyclerutils;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.mayousheng.www.initview.ViewUtils;

public abstract class BaseRecyclerHolder<T> extends RecyclerView.ViewHolder {

    protected Context context;

    public BaseRecyclerHolder(Context context, View view) {
        super(view);
        this.context = context.getApplicationContext();
        ViewUtils.initAllView(BaseRecyclerHolder.class, this, view);
    }

    public abstract void inViewBind(T t);

}
