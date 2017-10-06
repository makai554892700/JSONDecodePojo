package com.mayousheng.www.jsondecodepojo.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.base.BaseRecyclerAdapter;
import com.mayousheng.www.jsondecodepojo.base.BaseRecyclerHolder;
import com.mayousheng.www.jsondecodepojo.holder.TextNewsHolder;

/**
 * Created by ma kai on 2017/10/5.
 */

public class TextNewsAdapter extends BaseRecyclerAdapter<String> {

    public TextNewsAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseRecyclerHolder<String> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TextNewsHolder(context, layoutInflater.inflate(R.layout.item_text_news, parent, false));
    }

}
