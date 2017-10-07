package com.mayousheng.www.jsondecodepojo.holder.bsbdj;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.base.BaseRecyclerHolder;
import com.mayousheng.www.jsondecodepojo.common.ViewDesc;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJPunsterResponse;
import com.mayousheng.www.jsondecodepojo.utils.RC4Utils;
import com.mayousheng.www.jsondecodepojo.utils.ShowImageUtils;

/**
 * Created by ma kai on 2017/10/5.
 */

public class PunsterHolder extends BaseRecyclerHolder<BSBDJPunsterResponse> {

    @ViewDesc(viewId = R.id.common_user_img)
    ImageView userImg;
    @ViewDesc(viewId = R.id.common_user_name)
    TextView userName;
    @ViewDesc(viewId = R.id.common_date)
    TextView date;
    @ViewDesc(viewId = R.id.text)
    TextView text;
    @ViewDesc(viewId = R.id.common_love)
    ImageView love;
    @ViewDesc(viewId = R.id.common_love_text)
    TextView loveText;
    @ViewDesc(viewId = R.id.common_hate)
    ImageView hate;
    @ViewDesc(viewId = R.id.common_hate_text)
    TextView hateText;
    @ViewDesc(viewId = R.id.common_share)
    ImageView share;
    @ViewDesc(viewId = R.id.common_share_text)
    TextView shareText;
    @ViewDesc(viewId = R.id.common_comment)
    ImageView comment;
    @ViewDesc(viewId = R.id.common_comment_text)
    TextView commentText;

    public PunsterHolder(final Context context, View view) {
        super(context, view);
    }

    @Override
    public void inViewBind(BSBDJPunsterResponse punsterResponse) {
        userImg.setTag(String.valueOf(punsterResponse.mark));
        new ShowImageUtils(itemView).setImgDescs(new ShowImageUtils.ImgDesc[]{
                new ShowImageUtils.ImgDesc(String.valueOf(punsterResponse.mark)
                        , punsterResponse.userDesc.imgUrl)}).loadImage(0, 1);
        userName.setText(punsterResponse.userDesc.nikeName);
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
