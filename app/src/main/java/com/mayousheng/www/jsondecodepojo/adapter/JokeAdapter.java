package com.mayousheng.www.jsondecodepojo.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.base.BaseRecyclerAdapter;
import com.mayousheng.www.jsondecodepojo.base.BaseRecyclerHolder;
import com.mayousheng.www.jsondecodepojo.holder.JokeHolder;
import com.mayousheng.www.jsondecodepojo.pojo.JokeResponse;

/**
 * Created by ma kai on 2017/10/5.
 */

public class JokeAdapter extends BaseRecyclerAdapter<JokeResponse> {

    public JokeAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseRecyclerHolder<JokeResponse> onCreateViewHolder(ViewGroup parent, int viewType) {
        rootView = layoutInflater.inflate(R.layout.item_joke, parent, false);
        return new JokeHolder(context, rootView);
    }

    public void onDataChange() {

    }

}
