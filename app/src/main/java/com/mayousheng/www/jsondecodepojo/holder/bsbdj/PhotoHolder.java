package com.mayousheng.www.jsondecodepojo.holder.bsbdj;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.base.BaseNewsHolder;
import com.mayousheng.www.jsondecodepojo.common.StaticParam;
import com.mayousheng.www.initview.ViewDesc;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJPhotoResponse;

import java.lang.ref.WeakReference;

import www.mayousheng.com.showimgutils.ShowImageUtils;

/**
 * Created by ma kai on 2017/10/5.
 */

public class PhotoHolder extends BaseNewsHolder<BSBDJPhotoResponse> {

    @ViewDesc(viewId = R.id.img)
    public ImageView img;

    public PhotoHolder(final Context context, View view, ShowImageUtils showImageUtils) {
        super(context, view, showImageUtils);
    }

    @Override
    public void inViewBind(BSBDJPhotoResponse photoResponse) {
        super.inViewBind(photoResponse);
        String imgTag = StaticParam.TAG_IMG_URL + photoResponse.newsDesc.newsMark;
        img.setImageResource(R.color.black);
        img.setTag(imgTag);
        showImageUtils.loadImage(imgTag, new WeakReference<ImageView>(img));
    }
}
