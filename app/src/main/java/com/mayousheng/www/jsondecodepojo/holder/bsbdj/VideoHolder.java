package com.mayousheng.www.jsondecodepojo.holder.bsbdj;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.base.BaseNewsHolder;
import com.mayousheng.www.initview.ViewDesc;
import com.mayousheng.www.jsondecodepojo.common.StaticParam;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJVideoResponse;
import com.mayousheng.www.jsondecodepojo.utils.MediaPlayerUtils;
import com.mayousheng.www.jsondecodepojo.utils.ShowImageUtils;

import java.lang.ref.WeakReference;

/**
 * Created by ma kai on 2017/10/5.
 */

public class VideoHolder extends BaseNewsHolder<BSBDJVideoResponse> {

    @ViewDesc(viewId = R.id.video_bg)
    public ImageView videoBg;
    @ViewDesc(viewId = R.id.video)
    public SurfaceView video;
    @ViewDesc(viewId = R.id.loading)
    public ProgressBar loading;
    private SurfaceHolder surfaceHolder;
    private boolean isInit;
    private boolean isBgShow = true;
    private String videoUri;
    private View.OnClickListener onVideoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isInit) {
                MediaPlayerUtils.getInstance().onClick(videoUri, surfaceHolder, new MediaPlayerUtils.StatusBack() {
                    @Override
                    public void onStatuChange(MediaPlayerUtils.PlayStatus playStatus) {
                        if (playStatus == MediaPlayerUtils.PlayStatus.PLAYING) {
                            try {
                                Thread.sleep(500);//防止视频切换闪烁
                            } catch (Exception e) {
                            }
                            showVideoBg(false);
                        }
                        Log.e("-----1", "VideoHolder  playStatus=" + playStatus + ";videoUri=" + videoUri);
                    }
                });
            } else {
                Log.e("-----1", "is not init.videoUri=" + videoUri);
            }
        }
    };

    public VideoHolder(final Context context, View view, ShowImageUtils showImageUtils) {
        super(context, view, showImageUtils);
    }


    @Override
    public void inViewBind(final BSBDJVideoResponse videoResponse) {
        super.inViewBind(videoResponse);
        onLoad(true);
        showVideoBg(true);
        final String imgTag = StaticParam.TAG_IMG_URL + videoResponse.newsDesc.newsMark;
        videoUri = videoResponse.videoUri;
        final int realWidth;
        final int realHeight;
        if (videoResponse.height > videoResponse.width * 1.5) {
            realWidth = (int) (width * StaticParam.BSBDJ_VIDEO_SCALE);
            realHeight = (int) (videoResponse.height * width / videoResponse.width * StaticParam.BSBDJ_VIDEO_SCALE);
        } else {
            realWidth = width;
            realHeight = videoResponse.height * width / videoResponse.width;
        }
        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(realWidth, realHeight);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        videoBg.setLayoutParams(layoutParams);
        videoBg.setTag(imgTag);
        videoBg.setImageResource(R.color.black);
        showImageUtils.loadImage(imgTag, new WeakReference<ImageView>(videoBg), new ShowImageUtils.LoadImageBack() {
            @Override
            public void onField(String message) {
                Log.e("-----1", "load imge onField mesage=" + message);
            }

            @Override
            public void onSuccess() {
                onLoad(false);
            }
        });
        video.setLayoutParams(layoutParams);
        video.setOnClickListener(onVideoClickListener);
        surfaceHolder = video.getHolder();
        surfaceHolder.setFixedSize(realWidth, realHeight);
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
    }

    private void onLoad(boolean isLoading) {
        loading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    private void showVideoBg(boolean show) {
        isBgShow = show;
        videoBg.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
