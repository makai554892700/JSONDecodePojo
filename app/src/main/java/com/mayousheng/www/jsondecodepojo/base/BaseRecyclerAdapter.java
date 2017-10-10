package com.mayousheng.www.jsondecodepojo.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by ma kai on 2017/10/5.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHolder<T>> {

    protected Context context;
    protected LayoutInflater layoutInflater;
    protected View rootView;
    protected ArrayList<T> data = new ArrayList<>();

    public BaseRecyclerAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void addData(ArrayList<T> data) {
        this.data.addAll(data);
    }

    public void setData(ArrayList<T> data) {
        this.data.clear();
        addData(data);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder<T> holder, int position) {
        holder.inViewBind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
