package com.mayousheng.www.jsondecodepojo.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mayousheng.www.jsondecodepojo.utils.ViewUtils;

/**
 * Created by ma kai on 2017/10/5.
 */

public abstract class BaseRecyclerHolder<T> extends RecyclerView.ViewHolder {

    protected Context context;

    public BaseRecyclerHolder(Context context, View view) {
        super(view);
        this.context = context;
        ViewUtils.initAllView(this, view);
    }

    public abstract void inViewBind(T t);

}
