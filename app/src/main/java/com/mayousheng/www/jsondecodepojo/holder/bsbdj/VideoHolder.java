package com.mayousheng.www.jsondecodepojo.holder.bsbdj;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.base.BaseNewsHolder;
import com.mayousheng.www.jsondecodepojo.common.PlayStatEnum;
import com.mayousheng.www.initview.ViewDesc;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJVideoResponse;
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
    private PlayStatEnum playStat = PlayStatEnum.STOPED;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private SurfaceHolder surfaceHolder;
    private View.OnClickListener onVideoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (playStat) {
                case PLAYING:
                    mediaPlayer.pause();
                    playStat = PlayStatEnum.PAUSEED;
                    break;
                case PAUSEED:
                    mediaPlayer.start();
                    playStat = PlayStatEnum.PLAYING;
                    break;
                case STOPED:
                    mediaPlayer.start();
                    playStat = PlayStatEnum.PLAYING;
                    break;
            }
        }
    };

    public VideoHolder(final Context context, View view) {
        super(context, view);
    }


    @Override
    public void inViewBind(final BSBDJVideoResponse videoResponse) {
        userImg.setTag(String.valueOf(videoResponse.mark));
        new ShowImageUtils(itemView).setImgDescs(new ShowImageUtils.ImgDesc[]{
                new ShowImageUtils.ImgDesc(String.valueOf(videoResponse.mark)
                        , videoResponse.userDesc.imgUrl)}).loadImage(0, 1);
        surfaceHolder = video.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                mediaPlayer.setDisplay(surfaceHolder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });
        try {
            mediaPlayer.setDataSource(videoResponse.videoUri);
            mediaPlayer.prepare();
        } catch (Exception e) {
            Log.e("-----1", "e=" + e);
        }
//        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                mediaPlayer.setDisplay(surfaceHolder);
//            }
//        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                    playStat = PlayStatEnum.STOPED;
                }
            }
        });
        video.setOnClickListener(onVideoClickListener);

        userName.setText(videoResponse.userDesc.nikeName);
        date.setText(videoResponse.newsDesc.createTime);
        text.setText(RC4Utils.hexStringToString(videoResponse.text));
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
