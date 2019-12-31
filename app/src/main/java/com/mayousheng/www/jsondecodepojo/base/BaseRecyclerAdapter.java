package com.mayousheng.www.jsondecodepojo.base;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.mayousheng.www.jsondecodepojo.utils.MessageUtils;

import java.util.ArrayList;
import java.util.HashMap;

import www.mayousheng.com.showimgutils.ShowImageUtils;

/**
 * Created by ma kai on 2017/10/5.
 */

public abstract class BaseRecyclerAdapter<T extends BaseResponse> extends RecyclerView.Adapter<BaseRecyclerHolder<T>> {

    protected Context context;
    protected LayoutInflater layoutInflater;
    protected View rootView;
    protected ArrayList<T> data = new ArrayList<>();
    private ShowImageUtils showImageUtils = new ShowImageUtils();

    public ShowImageUtils getShowImageUtils() {
        return showImageUtils;
    }

    public BaseRecyclerAdapter(Context context) {
        this.context = context.getApplicationContext();
        layoutInflater = LayoutInflater.from(context.getApplicationContext());
    }

    public void addData(ArrayList<T> data) {
        this.data.addAll(data);
        showImageUtils.addImgDescs(MessageUtils.response2Map(data));
    }

    public void setData(ArrayList<T> data) {
        this.data.clear();
        addData(data);
        showImageUtils.setImgDescs(MessageUtils.response2Map(data));
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
