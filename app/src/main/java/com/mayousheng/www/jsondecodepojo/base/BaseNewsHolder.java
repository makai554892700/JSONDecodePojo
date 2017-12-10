package com.mayousheng.www.jsondecodepojo.base;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.initview.ViewDesc;
import com.mayousheng.www.jsondecodepojo.common.StaticParam;
import com.mayousheng.www.jsondecodepojo.pojo.Comment;
import com.mayousheng.www.jsondecodepojo.pojo.Operate;
import com.mayousheng.www.jsondecodepojo.utils.CommonRequestUtils;
import com.mayousheng.www.jsondecodepojo.utils.OperateUtils;
import com.mayousheng.www.jsondecodepojo.utils.RC4Utils;
import com.mayousheng.www.jsondecodepojo.utils.ShowImageUtils;

import java.lang.ref.WeakReference;

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
    protected ShowImageUtils showImageUtils;

    public BaseNewsHolder(final Context context, View view, ShowImageUtils showImageUtils) {
        super(context, view);
        this.showImageUtils = showImageUtils;
    }

    @Override
    public void inViewBind(final T baseResponse) {
//        Log.e("-----1", "baseResponse=" + baseResponse);
        String userImgTag = StaticParam.TAG_USER_IMG_URL + baseResponse.newsDesc.newsMark;
        userImg.setImageResource(R.drawable.user);
        userImg.setTag(userImgTag);
//        Log.e("-----1", "set user img tag.tag=" + userImgTag + ";userImg=" + userImg);
        showImageUtils.loadImage(userImgTag, new WeakReference<ImageView>(userImg));
        userName.setText(baseResponse.userDesc.nickName);
        date.setText(baseResponse.newsDesc.createTime);
        if (baseResponse.text != null && !StaticParam.NULL.equals(baseResponse.text)) {
            text.setText(RC4Utils.hexStringToString(baseResponse.text));
        } else {
            text.setVisibility(View.GONE);
        }
        loveText.setText(String.valueOf(baseResponse.newsDesc.love));
        hateText.setText(String.valueOf(baseResponse.newsDesc.hate));
        commentText.setText(String.valueOf(baseResponse.newsDesc.comment));
        shareText.setText(String.valueOf(baseResponse.newsDesc.share));
        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OperateUtils.love(context, new Operate(baseResponse.newsDesc.newsMark
                        , baseResponse.newsDesc.newsType), new CommonRequestUtils.Back() {
                    @Override
                    public void succeed() {
                        Log.e("-----1", "love succeed,");
                    }

                    @Override
                    public void field(String message) {
                        Log.e("-----1", "love field,message=" + message);
                    }
                });
            }
        });
        hate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OperateUtils.hate(context, new Operate(baseResponse.newsDesc.newsMark
                        , baseResponse.newsDesc.newsType), new CommonRequestUtils.Back() {
                    @Override
                    public void succeed() {
                        Log.e("-----1", "hate succeed.");
                    }

                    @Override
                    public void field(String message) {
                        Log.e("-----1", "hate field,message=" + message);
                    }
                });
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OperateUtils.share(context, new Operate(baseResponse.newsDesc.newsMark
                        , baseResponse.newsDesc.newsType), new CommonRequestUtils.Back() {
                    @Override
                    public void succeed() {
                        Log.e("-----1", "share succeed.");
                    }

                    @Override
                    public void field(String message) {
                        Log.e("-----1", "share field,message=" + message);
                    }
                });
            }
        });
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OperateUtils.comment(context, new Comment(new Operate(baseResponse.newsDesc.newsMark
                                , baseResponse.newsDesc.newsType), "testComment")
                        , new CommonRequestUtils.Back() {
                            @Override
                            public void succeed() {
                                Log.e("-----1", "comment succeed.");
                            }

                            @Override
                            public void field(String message) {
                                Log.e("-----1", "comment field,message=" + message);
                            }
                        });
            }
        });
    }
}
