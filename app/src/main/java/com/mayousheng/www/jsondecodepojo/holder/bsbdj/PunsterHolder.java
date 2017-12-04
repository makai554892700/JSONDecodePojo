package com.mayousheng.www.jsondecodepojo.holder.bsbdj;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.mayousheng.www.jsondecodepojo.base.BaseNewsHolder;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJPunsterResponse;
import com.mayousheng.www.jsondecodepojo.utils.RC4Utils;
import com.mayousheng.www.jsondecodepojo.utils.ShowImageUtils;

/**
 * Created by ma kai on 2017/10/5.
 */

public class PunsterHolder extends BaseNewsHolder<BSBDJPunsterResponse> {

    public PunsterHolder(final Context context, View view) {
        super(context, view);
    }

    @Override
    public void inViewBind(BSBDJPunsterResponse punsterResponse) {
        userImg.setTag(String.valueOf(punsterResponse.newsDesc.newsMark));
        new ShowImageUtils(itemView).setImgDescs(new ShowImageUtils.ImgDesc[]{
                new ShowImageUtils.ImgDesc(String.valueOf(punsterResponse.newsDesc.newsMark)
                        , punsterResponse.userDesc.imgUrl)}).loadImage(0, 1);
        userName.setText(punsterResponse.userDesc.nickName);
        date.setText(punsterResponse.newsDesc.createTime);
        text.setText(RC4Utils.hexStringToString(punsterResponse.text));
        loveText.setText(String.valueOf(punsterResponse.newsDesc.love));
        hateText.setText(String.valueOf(punsterResponse.newsDesc.hate));
        commentText.setText(String.valueOf(punsterResponse.newsDesc.comment));
        shareText.setText(String.valueOf(punsterResponse.newsDesc.share));
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
