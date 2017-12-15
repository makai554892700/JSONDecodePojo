package com.mayousheng.www.jsondecodepojo.utils;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ma kai on 2017/10/11.
 */

public class MediaPlayerUtils {

    private static final String TAG = MediaPlayerUtils.class.getName();
    private static final MediaPlayerUtils mediaPlayerUtils = new MediaPlayerUtils();
    private MediaPlayer mediaPlayer;
    private String currentUrl;
    private StatusBack statusBack;
    private PlayStatus playStatus = PlayStatus.STOPTED;
    private static final int TIME_SLEEP = 1000;
    private static final int TIME_DELAY = 1000;
    private PlayBack playBack;

    private Timer timer = new Timer();
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (playStatus == PlayStatus.PLAYING && playBack != null) {
                ThreadUtils.executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                            playBack.onPlayChange(mediaPlayer.getCurrentPosition()
                                    , mediaPlayer.getDuration());
                        }
                    }
                });
            }
        }
    };

    private MediaPlayerUtils() {
        timer.schedule(timerTask, TIME_DELAY, TIME_SLEEP);
    }

    public void setPlayBack(PlayBack playBack) {
        this.playBack = playBack;
    }

    public void setPlayStatus(PlayStatus playStatus, boolean needCallBack) {
        this.playStatus = playStatus;
        if (statusBack != null && needCallBack) {
            statusBack.onStatusChange(playStatus);
        }
    }

    public PlayStatus getPlayStatus() {
        return playStatus;
    }

    public void setStatusBack(StatusBack statusBack) {
        this.statusBack = statusBack;
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
                stop(currentUrl, true);
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
            stop(currentUrl);
        }
    }

    public static MediaPlayerUtils getInstance() {
        return mediaPlayerUtils;
    }

    public synchronized void onClick(String url, SurfaceHolder surfaceHolder, StatusBack statusBack) {
        if (currentUrl == null || !url.equals(currentUrl)) {
            stop(currentUrl);
        }
        setStatusBack(statusBack);
        if (android.text.TextUtils.isEmpty(url)) {
            return;
        }
        if (currentUrl == null || !url.equals(currentUrl)) {
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

    public synchronized void seekto(int point) {
        if (playStatus == PlayStatus.PLAYING) {
            try {
                mediaPlayer.seekTo(point);
            } catch (Exception e) {
                Log.e("-----1", "e4=" + e);
            }
        }
    }

    private synchronized void play() {
        if (playStatus != PlayStatus.PLAYING) {
            try {
                mediaPlayer.start();
            } catch (Exception e) {
                Log.e(TAG, "e2=" + e);
                setPlayStatus(PlayStatus.STOPTED, true);
                return;
            }
            setPlayStatus(PlayStatus.PLAYING, true);
        }
    }

    private synchronized void pause() {
        if (playStatus == PlayStatus.PAUSE) {
            return;
        }
        try {
            mediaPlayer.pause();
        } catch (Exception e) {
            Log.e(TAG, "e3=" + e);
            setPlayStatus(PlayStatus.STOPTED, true);
        }
        setPlayStatus(PlayStatus.PAUSE, true);
    }

    public synchronized void stop(String url) {
        stop(url, false);
    }

    private synchronized void stop(String url, boolean callBack) {
        if (currentUrl == null || !currentUrl.equals(url) || playStatus == PlayStatus.STOPTED) {
            return;
        }
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
                mediaPlayer.release();
            } catch (Exception e) {
                Log.e(TAG, "e5=" + e);
            }
        }
        currentUrl = null;
        mediaPlayer = null;
        if (callBack) {
            setPlayStatus(PlayStatus.STOPTED, callBack);
        }
    }

    public enum PlayStatus {
        PLAYING, PAUSE, STOPTED;
    }

    public interface PlayBack {
        public void onPlayChange(int currentPoint, int duration);
    }

    public interface StatusBack {
        public void onStatusChange(PlayStatus playStatus);
    }

}
