package com.mayousheng.www.jsondecodepojo.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.holder.JokeHolder;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJPunsterResponse;
import com.mayousheng.www.jsondecodepojo.pojo.JokeResponse;
import com.mayousheng.www.jsondecodepojo.utils.MessageUtils;
import com.mayousheng.www.recyclerutils.BaseRecyclerAdapter;
import com.mayousheng.www.recyclerutils.BaseRecyclerHolder;

import java.util.ArrayList;

import www.mayousheng.com.showimgutils.ShowImageUtils;

public class JokeAdapter extends BaseRecyclerAdapter<JokeResponse> {

    private ShowImageUtils showImageUtils = new ShowImageUtils();

    public ShowImageUtils getShowImageUtils() {
        return showImageUtils;
    }

    @Override
    public void addData(ArrayList<JokeResponse> data) {
        super.addData(data);
        showImageUtils.addImgDescs(MessageUtils.response2Map(data));
    }

    @Override
    public void setData(ArrayList<JokeResponse> data) {
        super.setData(data);
        showImageUtils.setImgDescs(MessageUtils.response2Map(data));
    }

    public JokeAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseRecyclerHolder<JokeResponse> onCreateViewHolder(ViewGroup parent, int viewType) {
        rootView = layoutInflater.inflate(R.layout.item_joke, parent, false);
        return new JokeHolder(context, rootView, getShowImageUtils());
    }

}
