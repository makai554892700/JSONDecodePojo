package com.mayousheng.www.jsondecodepojo.base;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunkai on 2017/3/16.
 */

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    public LayoutInflater layoutInflater;
    private List<T> datas = new ArrayList<T>();

    public BaseAdapter(Context context, List<T> datas) {
        layoutInflater = LayoutInflater.from(context);
        this.datas = datas;
    }

    public void updateData(Activity activity, List<T> datas) {
        this.datas = datas;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas == null ? null : datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

}
