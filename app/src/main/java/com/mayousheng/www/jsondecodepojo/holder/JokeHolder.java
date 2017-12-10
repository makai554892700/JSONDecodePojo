package com.mayousheng.www.jsondecodepojo.holder;

import android.content.Context;
import android.view.View;

import com.mayousheng.www.jsondecodepojo.base.BaseNewsHolder;
import com.mayousheng.www.jsondecodepojo.pojo.JokeResponse;
import com.mayousheng.www.jsondecodepojo.utils.ShowImageUtils;

/**
 * Created by ma kai on 2017/10/5.
 */

public class JokeHolder extends BaseNewsHolder<JokeResponse> {

    public JokeHolder(final Context context, View view, ShowImageUtils showImageUtils) {
        super(context, view, showImageUtils);
    }

    @Override
    public void inViewBind(final JokeResponse jokeResponse) {
        super.inViewBind(jokeResponse);
    }
}
