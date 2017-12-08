package com.mayousheng.www.jsondecodepojo.holder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mayousheng.www.jsondecodepojo.base.BaseNewsHolder;
import com.mayousheng.www.jsondecodepojo.pojo.Comment;
import com.mayousheng.www.jsondecodepojo.pojo.JokeResponse;
import com.mayousheng.www.jsondecodepojo.pojo.Operate;
import com.mayousheng.www.jsondecodepojo.utils.CommonRequestUtils;
import com.mayousheng.www.jsondecodepojo.utils.OperateUtils;
import com.mayousheng.www.jsondecodepojo.utils.RC4Utils;
import com.mayousheng.www.jsondecodepojo.utils.ShowImageUtils;

/**
 * Created by ma kai on 2017/10/5.
 */

public class JokeHolder extends BaseNewsHolder<JokeResponse> {

    public JokeHolder(final Context context, View view) {
        super(context, view);
    }

    @Override
    public void inViewBind(final JokeResponse jokeResponse) {
        userImg.setTag(String.valueOf(jokeResponse.newsDesc.newsMark));
        new ShowImageUtils(itemView).setImgDescs(new ShowImageUtils.ImgDesc[]{
                new ShowImageUtils.ImgDesc(String.valueOf(jokeResponse.newsDesc.newsMark)
                        , jokeResponse.userDesc.imgUrl)}).loadImage(0, 1);
        userName.setText(jokeResponse.userDesc.nickName);
        date.setText(jokeResponse.newsDesc.createTime);
        text.setText(RC4Utils.hexStringToString(jokeResponse.text));
        loveText.setText(String.valueOf(jokeResponse.newsDesc.love));
        hateText.setText(String.valueOf(jokeResponse.newsDesc.hate));
        commentText.setText(String.valueOf(jokeResponse.newsDesc.comment));
        shareText.setText(String.valueOf(jokeResponse.newsDesc.share));
        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OperateUtils.love(context, new Operate(jokeResponse.newsDesc.newsMark
                        , jokeResponse.newsDesc.newsType), new CommonRequestUtils.Back() {
                    @Override
                    public void succeed() {
                        Toast.makeText(context, "love", Toast.LENGTH_LONG).show();
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
                OperateUtils.hate(context, new Operate(jokeResponse.newsDesc.newsMark
                        , jokeResponse.newsDesc.newsType), new CommonRequestUtils.Back() {
                    @Override
                    public void succeed() {
                        Toast.makeText(context, "hate", Toast.LENGTH_LONG).show();
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
                OperateUtils.share(context, new Operate(jokeResponse.newsDesc.newsMark
                        , jokeResponse.newsDesc.newsType), new CommonRequestUtils.Back() {
                    @Override
                    public void succeed() {
                        Toast.makeText(context, "share", Toast.LENGTH_LONG).show();
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
                OperateUtils.comment(context, new Comment(new Operate(jokeResponse.newsDesc.newsMark
                                , jokeResponse.newsDesc.newsType), "testComment")
                        , new CommonRequestUtils.Back() {
                            @Override
                            public void succeed() {
                                Toast.makeText(context, "comment", Toast.LENGTH_LONG).show();
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
