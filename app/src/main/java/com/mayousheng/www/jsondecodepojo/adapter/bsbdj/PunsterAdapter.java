package com.mayousheng.www.jsondecodepojo.adapter.bsbdj;

import android.content.Context;
import android.view.ViewGroup;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.base.BaseRecyclerAdapter;
import com.mayousheng.www.jsondecodepojo.base.BaseRecyclerHolder;
import com.mayousheng.www.jsondecodepojo.holder.JokeHolder;
import com.mayousheng.www.jsondecodepojo.holder.bsbdj.PunsterHolder;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJPunsterResponse;
import com.mayousheng.www.jsondecodepojo.pojo.JokeResponse;

/**
 * Created by ma kai on 2017/10/5.
 */

public class PunsterAdapter extends BaseRecyclerAdapter<BSBDJPunsterResponse> {

    public PunsterAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseRecyclerHolder<BSBDJPunsterResponse> onCreateViewHolder(ViewGroup parent, int viewType) {
        rootView = layoutInflater.inflate(R.layout.item_punster, parent, false);
        return new PunsterHolder(context, rootView);
    }

    public void onDataChange() {

    }

}
