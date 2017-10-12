package com.mayousheng.www.jsondecodepojo.holder.bsbdj;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.base.BaseNewsHolder;
import com.mayousheng.www.jsondecodepojo.common.StaticParam;
import com.mayousheng.www.initview.ViewDesc;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJVoiceResponse;
import com.mayousheng.www.jsondecodepojo.utils.MediaPlayerUtils;
import com.mayousheng.www.jsondecodepojo.utils.RC4Utils;
import com.mayousheng.www.jsondecodepojo.utils.ShowImageUtils;

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
            MediaPlayerUtils.getInstance().onClick(voiceuri, null);
        }
    };

    public VoiceHolder(final Context context, View view) {
        super(context, view);
    }

    @Override
    public void inViewBind(BSBDJVoiceResponse voiceResponse) {
        voiceuri = voiceResponse.voiceuri;
        String userImgTag = StaticParam.TAG_USER_IMG_URL + voiceResponse.mark;
        userImg.setTag(userImgTag);
        new ShowImageUtils(itemView).setImgDescs(new ShowImageUtils.ImgDesc[]{
                new ShowImageUtils.ImgDesc(userImgTag
                        , voiceResponse.userDesc.imgUrl)}).loadImage(0, 1);
        String imgTag = StaticParam.TAG_IMG_URL + voiceResponse.mark;
        img.setTag(imgTag);
        img.setOnClickListener(onClickListener);
        new ShowImageUtils(itemView).setImgDescs(new ShowImageUtils.ImgDesc[]{
                new ShowImageUtils.ImgDesc(imgTag
                        , voiceResponse.cdnImg)}).loadImage(0, 1);
        userName.setText(voiceResponse.userDesc.nikeName);
        date.setText(voiceResponse.newsDesc.createTime);
        text.setText(RC4Utils.hexStringToString(voiceResponse.text));
        loveText.setText(String.valueOf(voiceResponse.newsDesc.love));
        hateText.setText(String.valueOf(voiceResponse.newsDesc.hate));
        commentText.setText(String.valueOf(voiceResponse.newsDesc.comment));
        shareText.setText(String.valueOf(voiceResponse.newsDesc.share));
        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "i love it.", Toast.LENGTH_LONG).show();
            }
        });
        hate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "i hate it.", Toast.LENGTH_LONG).show();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "i share it.", Toast.LENGTH_LONG).show();
            }
        });
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "i comment it.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
