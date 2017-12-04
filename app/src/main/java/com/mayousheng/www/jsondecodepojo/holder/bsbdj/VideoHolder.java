package com.mayousheng.www.jsondecodepojo.holder.bsbdj;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.base.BaseNewsHolder;
import com.mayousheng.www.initview.ViewDesc;
import com.mayousheng.www.jsondecodepojo.common.StaticParam;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJVideoResponse;
import com.mayousheng.www.jsondecodepojo.utils.MediaPlayerUtils;
import com.mayousheng.www.jsondecodepojo.utils.RC4Utils;
import com.mayousheng.www.jsondecodepojo.utils.ShowImageUtils;

/**
 * Created by ma kai on 2017/10/5.
 */

public class VideoHolder extends BaseNewsHolder<BSBDJVideoResponse> {

    @ViewDesc(viewId = R.id.play)
    public ImageView play;
    @ViewDesc(viewId = R.id.video)
    public SurfaceView video;
    private SurfaceHolder surfaceHolder;
    private boolean isInit;
    private String videoUri;
    private View.OnClickListener onVideoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isInit) {
                MediaPlayerUtils.getInstance().onClick(videoUri, surfaceHolder);
            } else {
                Log.e("-----1", "is not init.videoUri=" + videoUri);
            }
        }
    };

    public VideoHolder(final Context context, View view) {
        super(context, view);
    }


    @Override
    public void inViewBind(final BSBDJVideoResponse videoResponse) {
        videoUri = videoResponse.videoUri;
        userImg.setTag(String.valueOf(videoResponse.newsDesc.newsMark));
        new ShowImageUtils(itemView).setImgDescs(new ShowImageUtils.ImgDesc[]{
                new ShowImageUtils.ImgDesc(String.valueOf(videoResponse.newsDesc.newsMark)
                        , videoResponse.userDesc.imgUrl)}).loadImage(0, 1);
        surfaceHolder = video.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                isInit = true;
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                MediaPlayerUtils.getInstance().stop(videoUri);
            }
        });
        video.setOnClickListener(onVideoClickListener);
        userName.setText(videoResponse.userDesc.nickName);
        date.setText(videoResponse.newsDesc.createTime);
        if (videoResponse.text != null && !StaticParam.NULL.equals(videoResponse.text)) {
            text.setText(RC4Utils.hexStringToString(videoResponse.text));
        } else {
            text.setVisibility(View.GONE);
        }
        loveText.setText(String.valueOf(videoResponse.newsDesc.love));
        hateText.setText(String.valueOf(videoResponse.newsDesc.hate));
        commentText.setText(String.valueOf(videoResponse.newsDesc.comment));
        shareText.setText(String.valueOf(videoResponse.newsDesc.share));
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
