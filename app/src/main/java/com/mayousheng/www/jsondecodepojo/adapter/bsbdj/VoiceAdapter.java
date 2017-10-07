package com.mayousheng.www.jsondecodepojo.adapter.bsbdj;

import android.content.Context;
import android.view.ViewGroup;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.base.BaseRecyclerAdapter;
import com.mayousheng.www.jsondecodepojo.base.BaseRecyclerHolder;
import com.mayousheng.www.jsondecodepojo.holder.bsbdj.PhotoHolder;
import com.mayousheng.www.jsondecodepojo.holder.bsbdj.VoiceHolder;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJPhotoResponse;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJVoiceResponse;

/**
 * Created by ma kai on 2017/10/5.
 */

public class VoiceAdapter extends BaseRecyclerAdapter<BSBDJVoiceResponse> {

    public VoiceAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseRecyclerHolder<BSBDJVoiceResponse> onCreateViewHolder(ViewGroup parent, int viewType) {
        rootView = layoutInflater.inflate(R.layout.item_voice, parent, false);
        return new VoiceHolder(context, rootView);
    }

    public void onDataChange() {

    }

}
