package com.mayousheng.www.jsondecodepojo.holder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.base.BaseRecyclerHolder;
import com.mayousheng.www.jsondecodepojo.common.ViewDesc;
import com.mayousheng.www.jsondecodepojo.utils.TextUtils;

/**
 * Created by ma kai on 2017/10/5.
 */

public class TextNewsHolder extends BaseRecyclerHolder<String> implements View.OnClickListener {

    @ViewDesc(viewId = R.id.short_text)
    TextView shortText;
    @ViewDesc(viewId = R.id.long_text)
    TextView longText;
    @ViewDesc(viewId = R.id.more)
    TextView more;
    private boolean isLongShow;

    public TextNewsHolder(final Context context, View view) {
        super(context, view);
    }

    @Override
    public void onClick(View view) {
        if (isLongShow) {
            shortText.setVisibility(View.VISIBLE);
            longText.setVisibility(View.GONE);
            more.setText(context.getString(R.string.show));
        } else {
            shortText.setVisibility(View.GONE);
            longText.setVisibility(View.VISIBLE);
            more.setText(context.getString(R.string.hide));
        }
        isLongShow = !isLongShow;
    }

    private String tag;

    @Override
    public void inViewBind(String s) {
        shortText.setText(s);
        longText.setText(s);
        more.setOnClickListener(this);
        if (TextUtils.isOverFlowed(shortText)) {
            more.setVisibility(View.VISIBLE);
        } else {
            more.setVisibility(View.GONE);
        }
    }
}
