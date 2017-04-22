package com.mayousheng.www.jsondecodepojo.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.base.BaseHolder;

/**
 * Created by marking on 2017/4/18.
 */

public class CommonFootHolder extends BaseHolder {

    public TextView title;

    public CommonFootHolder(Context context, int layoutResource, ViewGroup parent) {
        super(context, layoutResource, parent);
    }

    @Override
    public void onViewBind(View view) {
        if (view != null) {
            title = (TextView) view.findViewById(R.id.title);
        }
    }

}
