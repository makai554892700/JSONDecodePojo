package com.mayousheng.www.jsondecodepojo.base;

import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
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
import com.mayousheng.www.jsondecodepojo.utils.db.DBOprateUtils;
import com.mayousheng.www.jsondecodepojo.utils.db.model.DBOprateInfo;

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
    protected int width, height;
    private DBOprateUtils dbOprateUtils;
    private DBOprateInfo dbOprateInfo, localOprateInfo;
    private Operate operate;
    private T data;
    private CommonRequestUtils.Back back = new CommonRequestUtils.Back() {
        @Override
        public void succeed() {
            Log.e("-----1", "operate succeed,");
            localOprateInfo.sure = true;
            dbOprateUtils.updateDBOprateInfo(localOprateInfo);
        }

        @Override
        public void field(String message) {
            dbOprateUtils.delDBOprateInfo(localOprateInfo.newsType
                    , localOprateInfo.newsMark);
            switch (dbOprateInfo.oprate) {
                case StaticParam.OPRATE_LOVE:
                    loveImg(false);
                    loveText(false);
                    break;
                case StaticParam.OPRATE_HATE:
                    hateImg(false);
                    hateText(false);
                    break;
            }
            Log.e("-----1", "operate field,message=" + message);
        }
    };

    public BaseNewsHolder(final Context context, View view, ShowImageUtils showImageUtils) {
        super(context, view);
        this.showImageUtils = showImageUtils;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            Display display = windowManager.getDefaultDisplay();
            if (display != null) {
                width = display.getWidth();
                height = display.getHeight();
            }
        }
        dbOprateUtils = new DBOprateUtils(context);
    }

    @Override
    public void inViewBind(final T baseResponse) {
//        Log.e("-----1", "baseResponse=" + baseResponse);
        data = baseResponse;
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
        dbOprateInfo = dbOprateUtils.getDBOprateInfoByTypeAndMark(baseResponse.newsDesc.newsType
                , baseResponse.newsDesc.newsMark);
        if (dbOprateInfo == null) {
            loveImg(false);
            hateImg(false);
        } else {
            switch (dbOprateInfo.oprate) {
                case StaticParam.OPRATE_LOVE:
                    loveImg(true);
                    break;
                case StaticParam.OPRATE_HATE:
                    hateImg(true);
                    break;
            }
        }
        localOprateInfo = new DBOprateInfo(baseResponse.newsDesc.newsType
                , baseResponse.newsDesc.newsMark, StaticParam.OPRATE_LOVE, false);
        operate = new Operate(baseResponse.newsDesc.newsMark
                , baseResponse.newsDesc.newsType);
        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operate(StaticParam.OPRATE_LOVE);
            }
        });
        hate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operate(StaticParam.OPRATE_HATE);
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

    private void operate(int operateType) {
        if (dbOprateInfo == null && localOprateInfo != null) {
            localOprateInfo.oprate = operateType;
            long id = dbOprateUtils.saveDBOprateInfo(localOprateInfo);
            if (id > 0) {
                dbOprateInfo = localOprateInfo;
                dbOprateInfo.id = (int) id;
            } else {
                Log.e("-----1", "save data error.");
                return;
            }
            switch (operateType) {
                case StaticParam.OPRATE_LOVE:
                    loveImg(true);
                    loveText(true);
                    OperateUtils.love(context, operate, back);
                    break;
                case StaticParam.OPRATE_HATE:
                    hateImg(true);
                    hateText(true);
                    OperateUtils.hate(context, operate, back);
                    break;
                default:
            }
        } else {//已经操作过了
            Log.e("-----1", "already operated.");
        }
    }

    private void loveImg(boolean isLove) {
        love.setImageResource(isLove ? R.drawable.love_select : R.drawable.love);
    }

    private void hateImg(boolean isHate) {
        hate.setImageResource(isHate ? R.drawable.hate_select : R.drawable.hate);
    }

    private void loveText(boolean isLove) {
        if (isLove) {
            data.newsDesc.love += 1;
        } else if (data.newsDesc.love > 0) {
            data.newsDesc.love -= 1;
        }
        loveText.setText(String.valueOf(data.newsDesc.love));
    }

    private void hateText(boolean isHate) {
        if (isHate) {
            data.newsDesc.hate += 1;
        } else if (data.newsDesc.hate > 0) {
            data.newsDesc.hate -= 1;
        }
        hateText.setText(String.valueOf(data.newsDesc.hate));
    }


}
