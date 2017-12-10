package com.mayousheng.www.jsondecodepojo.adapter.bsbdj;

import android.content.Context;
import android.view.ViewGroup;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.base.BaseRecyclerAdapter;
import com.mayousheng.www.jsondecodepojo.base.BaseRecyclerHolder;
import com.mayousheng.www.jsondecodepojo.holder.bsbdj.PhotoHolder;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJPhotoResponse;

/**
 * Created by ma kai on 2017/10/5.
 */

public class PhotoAdapter extends BaseRecyclerAdapter<BSBDJPhotoResponse> {

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
