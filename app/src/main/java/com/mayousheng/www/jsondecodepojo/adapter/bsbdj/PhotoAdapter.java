package com.mayousheng.www.jsondecodepojo.adapter.bsbdj;

import android.content.Context;
import android.view.ViewGroup;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJPunsterResponse;
import com.mayousheng.www.jsondecodepojo.utils.MessageUtils;
import com.mayousheng.www.recyclerutils.BaseRecyclerAdapter;
import com.mayousheng.www.recyclerutils.BaseRecyclerHolder;
import com.mayousheng.www.jsondecodepojo.holder.bsbdj.PhotoHolder;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJPhotoResponse;

import java.util.ArrayList;

import www.mayousheng.com.showimgutils.ShowImageUtils;

public class PhotoAdapter extends BaseRecyclerAdapter<BSBDJPhotoResponse> {

    private final ShowImageUtils showImageUtils = new ShowImageUtils();

    public ShowImageUtils getShowImageUtils() {
        return showImageUtils;
    }

    @Override
    public void addData(ArrayList<BSBDJPhotoResponse> data) {
        super.addData(data);
        showImageUtils.addImgDescs(MessageUtils.response2Map(data));
    }

    @Override
    public void setData(ArrayList<BSBDJPhotoResponse> data) {
        super.setData(data);
        showImageUtils.setImgDescs(MessageUtils.response2Map(data));
    }

    public PhotoAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseRecyclerHolder<BSBDJPhotoResponse> onCreateViewHolder(ViewGroup parent, int viewType) {
        rootView = layoutInflater.inflate(R.layout.item_photo, parent, false);
        return new PhotoHolder(context, rootView, getShowImageUtils());
    }

    public void onDataChange() {

    }

}
