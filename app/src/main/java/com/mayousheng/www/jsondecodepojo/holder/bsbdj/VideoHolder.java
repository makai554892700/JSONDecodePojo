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

    @ViewDesc(viewId = R.id.play)
    public ImageView play;
    @ViewDesc(viewId = R.id.video_bg)
    public ImageView videoBg;
    @ViewDesc(viewId = R.id.video)
    public SurfaceView video;
    @ViewDesc(viewId = R.id.loading)
    public ProgressBar loading;
    private SurfaceHolder surfaceHolder;
    private boolean isInit;
    private boolean isBgShow;
    private String videoUri;
    private View.OnClickListener onVideoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isInit) {
                if (!isBgShow) {
                    isBgShow = true;
                    videoBg.setVisibility(View.INVISIBLE);
                }
                MediaPlayerUtils.getInstance().onClick(videoUri, surfaceHolder);
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
        videoUri = videoResponse.videoUri;
        String imgTag = StaticParam.TAG_IMG_URL + videoResponse.newsDesc.newsMark;
        videoBg.setImageResource(R.color.black);
        videoBg.setTag(imgTag);
        showImageUtils.loadImage(imgTag, new WeakReference<ImageView>(videoBg));
        surfaceHolder = video.getHolder();
        surfaceHolder.setFixedSize(videoResponse.width, videoResponse.height);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(videoResponse.width, videoResponse.height);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        video.setLayoutParams(layoutParams);
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                isInit = true;
                loading.setVisibility(View.INVISIBLE);
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
    }

}
