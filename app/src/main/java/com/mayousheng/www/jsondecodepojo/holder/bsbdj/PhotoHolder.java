package com.mayousheng.www.jsondecodepojo.holder.bsbdj;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.base.BaseNewsHolder;
import com.mayousheng.www.jsondecodepojo.common.StaticParam;
import com.mayousheng.www.viewinit.ViewDesc;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJPhotoResponse;
import com.mayousheng.www.jsondecodepojo.utils.RC4Utils;
import com.mayousheng.www.jsondecodepojo.utils.ShowImageUtils;

/**
 * Created by ma kai on 2017/10/5.
 */

public class PhotoHolder extends BaseNewsHolder<BSBDJPhotoResponse> {

    @ViewDesc(viewId = R.id.img)
    public ImageView img;

    public PhotoHolder(final Context context, View view) {
        super(context, view);
    }

    @Override
    public void inViewBind(BSBDJPhotoResponse photoResponse) {
        String userImgTag = StaticParam.TAG_USER_IMG_URL + photoResponse.mark;
        userImg.setTag(userImgTag);
        new ShowImageUtils(itemView).setImgDescs(new ShowImageUtils.ImgDesc[]{
                new ShowImageUtils.ImgDesc(userImgTag
                        , photoResponse.userDesc.imgUrl)}).loadImage(0, 1);
        String imgTag = StaticParam.TAG_IMG_URL + photoResponse.mark;
        img.setTag(imgTag);
        new ShowImageUtils(itemView).setImgDescs(new ShowImageUtils.ImgDesc[]{
                new ShowImageUtils.ImgDesc(imgTag
                        , photoResponse.cdnImg)}).loadImage(0, 1);
        userName.setText(photoResponse.userDesc.nikeName);
        date.setText(photoResponse.newsDesc.createTime);
        text.setText(RC4Utils.hexStringToString(photoResponse.text));
        loveText.setText(String.valueOf(photoResponse.newsDesc.love));
        hateText.setText(String.valueOf(photoResponse.newsDesc.hate));
        commentText.setText(String.valueOf(photoResponse.newsDesc.comment));
        shareText.setText(String.valueOf(photoResponse.newsDesc.share));
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
