package com.mayousheng.www.jsondecodepojo.holder.bsbdj;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.base.BaseNewsHolder;
import com.mayousheng.www.jsondecodepojo.common.StaticParam;
import com.mayousheng.www.initview.ViewDesc;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJVoiceResponse;
import com.mayousheng.www.jsondecodepojo.utils.MediaPlayerUtils;

import java.lang.ref.WeakReference;

import www.mayousheng.com.showimgutils.ShowImageUtils;

/**
 * Created by ma kai on 2017/10/5.
 */

public class VoiceHolder extends BaseNewsHolder<BSBDJVoiceResponse> {

    @ViewDesc(viewId = R.id.img)
    public ImageView img;
    private String voiceuri;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MediaPlayerUtils.getInstance().onClick(voiceuri, null, new MediaPlayerUtils.StatusBack() {
                @Override
                public void onStatusChange(MediaPlayerUtils.PlayStatus playStatus) {
                    Log.e("-----1", "VoiceHolder  playStatus=" + playStatus + ";voiceuri=" + voiceuri);
                }
            });
        }
    };

    public VoiceHolder(final Context context, View view, ShowImageUtils showImageUtils) {
        super(context, view, showImageUtils);
    }

    @Override
    public void inViewBind(BSBDJVoiceResponse voiceResponse) {
        super.inViewBind(voiceResponse);
        voiceuri = voiceResponse.voiceuri;
        String imgTag = StaticParam.TAG_IMG_URL + voiceResponse.newsDesc.newsMark;
        img.setImageResource(R.color.black);
        img.setTag(imgTag);
        img.setOnClickListener(onClickListener);
        showImageUtils.loadImage(imgTag, new WeakReference<ImageView>(img));
    }
}
