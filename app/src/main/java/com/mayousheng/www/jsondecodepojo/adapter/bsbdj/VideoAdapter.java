package com.mayousheng.www.jsondecodepojo.adapter.bsbdj;

import android.content.Context;
import android.view.ViewGroup;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJPunsterResponse;
import com.mayousheng.www.jsondecodepojo.utils.MessageUtils;
import com.mayousheng.www.recyclerutils.BaseRecyclerAdapter;
import com.mayousheng.www.recyclerutils.BaseRecyclerHolder;
import com.mayousheng.www.jsondecodepojo.holder.bsbdj.VideoHolder;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJVideoResponse;

import java.util.ArrayList;

import www.mayousheng.com.showimgutils.ShowImageUtils;

public class VideoAdapter extends BaseRecyclerAdapter<BSBDJVideoResponse> {

    private final ShowImageUtils showImageUtils = new ShowImageUtils();

    public ShowImageUtils getShowImageUtils() {
        return showImageUtils;
    }

    @Override
    public void addData(ArrayList<BSBDJVideoResponse> data) {
        super.addData(data);
        showImageUtils.addImgDescs(MessageUtils.response2Map(data));
    }

    @Override
    public void setData(ArrayList<BSBDJVideoResponse> data) {
        super.setData(data);
        showImageUtils.setImgDescs(MessageUtils.response2Map(data));
    }

    public VideoAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseRecyclerHolder<BSBDJVideoResponse> onCreateViewHolder(ViewGroup parent, int viewType) {
        rootView = layoutInflater.inflate(R.layout.item_video, parent, false);
        return new VideoHolder(context, rootView, getShowImageUtils());
    }

    public void onDataChange() {

    }

}
