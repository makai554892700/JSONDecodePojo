package com.mayousheng.www.jsondecodepojo.base;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.viewinit.ViewDesc;

/**
 * Created by ma kai on 2017/10/5.
 */

public abstract class BaseNewsHolder<T extends BaseResponse> extends BaseRecyclerHolder<T> {

    @ViewDesc(viewId = R.id.common_user_img)
    public ImageView userImg;
    @ViewDesc(viewId = R.id.common_user_name)
    public TextView userName;
    @ViewDesc(viewId = R.id.common_date)
    public TextView date;
    @ViewDesc(viewId = R.id.text)
    public TextView text;
    @ViewDesc(viewId = R.id.common_love)
    public ImageView love;
    @ViewDesc(viewId = R.id.common_love_text)
    public TextView loveText;
    @ViewDesc(viewId = R.id.common_hate)
    public ImageView hate;
    @ViewDesc(viewId = R.id.common_hate_text)
    public TextView hateText;
    @ViewDesc(viewId = R.id.common_share)
    public ImageView share;
    @ViewDesc(viewId = R.id.common_share_text)
    public TextView shareText;
    @ViewDesc(viewId = R.id.common_comment)
    public ImageView comment;
    @ViewDesc(viewId = R.id.common_comment_text)
    public TextView commentText;

    public BaseNewsHolder(final Context context, View view) {
        super(context, view);
    }

}
