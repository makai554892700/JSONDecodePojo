package com.mayousheng.www.jsondecodepojo.utils;

import android.media.MediaPlayer;
import android.text.*;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by ma kai on 2017/10/11.
 */

public class MediaPlayerUtils {

    private static final String TAG = MediaPlayerUtils.class.getName();
    private static final MediaPlayerUtils mediaPlayerUtils = new MediaPlayerUtils();
    private MediaPlayer mediaPlayer;
    private String currentUrl;
    private PlayStatus playStatus = PlayStatus.STOPTED;

    private MediaPlayerUtils() {
    }

    private synchronized void initMediaPlayer(String url, SurfaceHolder surfaceHolder) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                play();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                    playStatus = PlayStatus.STOPTED;
                }
            }
        });
        try {
            mediaPlayer.setDataSource(url);
            if (surfaceHolder != null) {
                mediaPlayer.setDisplay(surfaceHolder);
            }
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            Log.e(TAG, "e1=" + e);
        }
        currentUrl = url;
    }

    public static MediaPlayerUtils getInstance() {
        return mediaPlayerUtils;
    }

    public PlayStatus getStatus() {
        return playStatus;
    }

    public void onClick(String url, SurfaceHolder surfaceHolder) {
        if (android.text.TextUtils.isEmpty(url)) {
            return;
        }
        if (currentUrl == null || !url.equals(currentUrl)) {
            stop(url);
            initMediaPlayer(url, surfaceHolder);
        } else {
            switch (playStatus) {
                case STOPTED:
                    play();
                    break;
                case PLAYING:
                    pause();
                    break;
                case PAUSE:
                    stop(url);
                    break;
            }
        }
    }

    public void play() {
        if (playStatus == PlayStatus.PLAYING) {
            return;
        }
        try {
            mediaPlayer.start();
        } catch (Exception e) {
            Log.e(TAG, "e2=" + e);
            playStatus = PlayStatus.STOPTED;
        }
        playStatus = PlayStatus.PLAYING;
    }

    public void pause() {
        if (playStatus == PlayStatus.PAUSE) {
            return;
        }
        try {
            mediaPlayer.pause();
        } catch (Exception e) {
            Log.e(TAG, "e3=" + e);
            playStatus = PlayStatus.STOPTED;
        }
        playStatus = PlayStatus.PAUSE;
    }

    public void stop(String url) {
        if (currentUrl == null || !currentUrl.equals(url) || playStatus == PlayStatus.STOPTED) {
            return;
        }
        try {
            mediaPlayer.stop();
            mediaPlayer.release();
        } catch (Exception e) {
            Log.e(TAG, "e4=" + e);
        }
        currentUrl = null;
        mediaPlayer = null;
        playStatus = PlayStatus.STOPTED;
    }

    private enum PlayStatus {
        PLAYING, PAUSE, STOPTED;
    }
}
