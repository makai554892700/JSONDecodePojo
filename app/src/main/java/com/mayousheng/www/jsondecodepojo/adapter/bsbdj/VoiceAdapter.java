package com.mayousheng.www.jsondecodepojo.adapter.bsbdj;

import android.content.Context;
import android.view.ViewGroup;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJPunsterResponse;
import com.mayousheng.www.jsondecodepojo.utils.MessageUtils;
import com.mayousheng.www.recyclerutils.BaseRecyclerAdapter;
import com.mayousheng.www.recyclerutils.BaseRecyclerHolder;
import com.mayousheng.www.jsondecodepojo.holder.bsbdj.VoiceHolder;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJVoiceResponse;

import java.util.ArrayList;

import www.mayousheng.com.showimgutils.ShowImageUtils;

public class VoiceAdapter extends BaseRecyclerAdapter<BSBDJVoiceResponse> {

    private final ShowImageUtils showImageUtils = new ShowImageUtils();

    public ShowImageUtils getShowImageUtils() {
        return showImageUtils;
    }

    @Override
    public void addData(ArrayList<BSBDJVoiceResponse> data) {
        super.addData(data);
        showImageUtils.addImgDescs(MessageUtils.response2Map(data));
    }

    @Override
    public void setData(ArrayList<BSBDJVoiceResponse> data) {
        super.setData(data);
        showImageUtils.setImgDescs(MessageUtils.response2Map(data));
    }

    public VoiceAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseRecyclerHolder<BSBDJVoiceResponse> onCreateViewHolder(ViewGroup parent, int viewType) {
        rootView = layoutInflater.inflate(R.layout.item_voice, parent, false);
        return new VoiceHolder(context, rootView, getShowImageUtils());
    }

    public void onDataChange() {

    }

}
