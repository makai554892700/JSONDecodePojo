package com.mayousheng.www.jsondecodepojo.adapter.bsbdj;

import android.content.Context;
import android.view.ViewGroup;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.utils.MessageUtils;
import com.mayousheng.www.recyclerutils.BaseRecyclerAdapter;
import com.mayousheng.www.recyclerutils.BaseRecyclerHolder;
import com.mayousheng.www.jsondecodepojo.holder.bsbdj.PunsterHolder;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJPunsterResponse;

import java.util.ArrayList;

import www.mayousheng.com.showimgutils.ShowImageUtils;

public class PunsterAdapter extends BaseRecyclerAdapter<BSBDJPunsterResponse> {

    private final ShowImageUtils showImageUtils = new ShowImageUtils();

    public ShowImageUtils getShowImageUtils() {
        return showImageUtils;
    }

    @Override
    public void addData(ArrayList<BSBDJPunsterResponse> data) {
        super.addData(data);
        showImageUtils.addImgDescs(MessageUtils.response2Map(data));
    }

    @Override
    public void setData(ArrayList<BSBDJPunsterResponse> data) {
        super.setData(data);
        showImageUtils.setImgDescs(MessageUtils.response2Map(data));
    }

    public PunsterAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseRecyclerHolder<BSBDJPunsterResponse> onCreateViewHolder(ViewGroup parent, int viewType) {
        rootView = layoutInflater.inflate(R.layout.item_punster, parent, false);
        return new PunsterHolder(context, rootView, getShowImageUtils());
    }

    public void onDataChange() {

    }

}
