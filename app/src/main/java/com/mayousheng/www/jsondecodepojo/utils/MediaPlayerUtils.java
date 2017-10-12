package com.mayousheng.www.jsondecodepojo.utils;

import android.media.AudioManager;
import android.media.MediaPlayer;
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

    private synchronized void initMediaPlayer(final String url, SurfaceHolder surfaceHolder) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                currentUrl = url;
                play();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                }
                playStatus = PlayStatus.STOPTED;
            }
        });
        try {
            mediaPlayer.setDataSource(url);
            if (surfaceHolder != null) {
                mediaPlayer.setDisplay(surfaceHolder);
            } else {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);
            }
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            Log.e(TAG, "e1=" + e);
            currentUrl = null;
            playStatus = PlayStatus.STOPTED;
        }
    }

    public static MediaPlayerUtils getInstance() {
        return mediaPlayerUtils;
    }

    public PlayStatus getStatus() {
        return playStatus;
    }

    public synchronized void onClick(String url, SurfaceHolder surfaceHolder) {
        if (android.text.TextUtils.isEmpty(url)) {
            return;
        }
        if (currentUrl == null || !url.equals(currentUrl)) {
            stop(currentUrl);
            initMediaPlayer(url, surfaceHolder);
        } else {
            switch (playStatus) {
                case STOPTED:
                case PAUSE:
                    play();
                    break;
                case PLAYING:
                    pause();
                    break;
            }
        }
    }

    public synchronized void play() {
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

    public synchronized void pause() {
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

    public synchronized void stop(String url) {
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
