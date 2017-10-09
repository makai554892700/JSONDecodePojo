package com.mayousheng.www.jsondecodepojo.adapter.bsbdj;

import android.content.Context;
import android.view.ViewGroup;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.base.BaseRecyclerAdapter;
import com.mayousheng.www.jsondecodepojo.base.BaseRecyclerHolder;
import com.mayousheng.www.jsondecodepojo.holder.bsbdj.VideoHolder;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJVideoResponse;

/**
 * Created by ma kai on 2017/10/5.
 */

public class VideoAdapter extends BaseRecyclerAdapter<BSBDJVideoResponse> {

    public VideoAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseRecyclerHolder<BSBDJVideoResponse> onCreateViewHolder(ViewGroup parent, int viewType) {
        rootView = layoutInflater.inflate(R.layout.item_video, parent, false);
        return new VideoHolder(context, rootView);
    }

    public void onDataChange() {

    }

}
