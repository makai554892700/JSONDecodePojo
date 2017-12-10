package com.mayousheng.www.jsondecodepojo.holder.bsbdj;

import android.content.Context;
import android.view.View;

import com.mayousheng.www.jsondecodepojo.base.BaseNewsHolder;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJPunsterResponse;
import com.mayousheng.www.jsondecodepojo.utils.ShowImageUtils;

/**
 * Created by ma kai on 2017/10/5.
 */

public class PunsterHolder extends BaseNewsHolder<BSBDJPunsterResponse> {

    public PunsterHolder(final Context context, View view, ShowImageUtils showImageUtils) {
        super(context, view, showImageUtils);
    }

    @Override
    public void inViewBind(BSBDJPunsterResponse punsterResponse) {
        super.inViewBind(punsterResponse);
    }
}
