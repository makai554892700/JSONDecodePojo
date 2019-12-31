package com.mayousheng.www.jsondecodepojo.holder.bsbdj;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.base.BaseNewsHolder;
import com.mayousheng.www.initview.ViewDesc;
import com.mayousheng.www.jsondecodepojo.common.StaticParam;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJVideoResponse;
import com.mayousheng.www.jsondecodepojo.utils.MediaPlayerUtils;

import java.lang.ref.WeakReference;

import www.mayousheng.com.showimgutils.ShowImageUtils;

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
    @ViewDesc(viewId = R.id.play_bar)
    public RelativeLayout playBar;
    @ViewDesc(viewId = R.id.play)
    public ImageView play;
    @ViewDesc(viewId = R.id.seekBar)
    public SeekBar seekBar;
    private SurfaceHolder surfaceHolder;
    private boolean isInit;
    private boolean isBgShow = true;
    private String videoUri;
    private float seekto;
    private int TIME_SLEEP = 500;
    private MediaPlayerUtils.PlayBack playBack = new MediaPlayerUtils.PlayBack() {
        @Override
        public void onPlayChange(int currentPoint, int duration) {
            boolean isRefresh = duration != seekBar.getMax();
            if (isRefresh) {
                seekBar.setMax(duration);
            }
            if (seekto != 0) {
                currentPoint = (int) (seekto * duration);
                seekto = 0;
                if (isRefresh) {
                    seekBar.setProgress(currentPoint);
                }
                MediaPlayerUtils.getInstance().seekto(currentPoint);
            } else {
                seekBar.setProgress(currentPoint);
            }
        }
    };
    private MediaPlayerUtils.StatusBack statusBack = new MediaPlayerUtils.StatusBack() {
        @Override
        public void onStatusChange(MediaPlayerUtils.PlayStatus playStatus) {
            if (playStatus == MediaPlayerUtils.PlayStatus.PLAYING) {
                try {
                    Thread.sleep(TIME_SLEEP);//防止视频切换闪烁
                } catch (Exception e) {
                }
                showVideoBg(false);
            }
            Log.e("-----1", "VideoHolder  playStatus=" + playStatus + ";videoUri=" + videoUri);
        }
    };
    private View.OnClickListener onVideoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            VideoHolder.this.onClick();
        }
    };
    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Log.e("-----1", "progress=" + seekBar.getProgress() + ";max=" + seekBar.getMax());
            if (MediaPlayerUtils.getInstance().getPlayStatus() != MediaPlayerUtils.PlayStatus.PLAYING) {
                seekto = seekBar.getProgress() * 1.0000f / seekBar.getMax();
                VideoHolder.this.onClick();
            } else {
                MediaPlayerUtils.getInstance().seekto(seekBar.getProgress());
            }
        }
    };
    private SurfaceHolder.Callback callBack = new SurfaceHolder.Callback() {
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
    };
    private ShowImageUtils.LoadImageBack loadImageBack = new ShowImageUtils.LoadImageBack() {
        @Override
        public void onField(String message) {
            Log.e("-----1", "load imge onField mesage=" + message);
        }

        @Override
        public void onSuccess() {
            onLoad(false);
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
        if (videoResponse.height > videoResponse.width) {
            realWidth = (int) (width * StaticParam.BSBDJ_VIDEO_SCALE);
            realHeight = (int) (videoResponse.height * width / videoResponse.width
                    * StaticParam.BSBDJ_VIDEO_SCALE);
        } else {
            realWidth = width;
            realHeight = videoResponse.height * width / videoResponse.width;
        }
        final RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(realWidth, realHeight);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        videoBg.setLayoutParams(layoutParams);
        videoBg.setTag(imgTag);
        videoBg.setImageResource(R.color.black);
        playBar.setVisibility(View.GONE);
        play.setOnClickListener(onVideoClickListener);
        seekBar.setMax(videoResponse.playTime);
        seekBar.setProgress(0);
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        showImageUtils.loadImage(imgTag, new WeakReference<>(videoBg), loadImageBack);
        video.setLayoutParams(layoutParams);
        surfaceHolder = video.getHolder();
        surfaceHolder.setFixedSize(realWidth, realHeight);
        surfaceHolder.addCallback(callBack);
    }

    private void onLoad(boolean isLoading) {
        loading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        playBar.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }

    private void showVideoBg(boolean show) {
        isBgShow = show;
        videoBg.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void onClick() {
        if (isInit) {
            MediaPlayerUtils.getInstance().onClick(videoUri, surfaceHolder, statusBack);
            MediaPlayerUtils.getInstance().setPlayBack(playBack);
        } else {
            Log.e("-----1", "is not init.videoUri=" + videoUri);
        }
    }

}
