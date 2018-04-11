package com.dong.easy.video;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import com.dong.easy.R;
import com.dong.easy.util.UIUtils;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * 普通的视频播放器
 * Created by zengwendong on 2018/2/5.
 */

public class IJKVideoPlayer extends FrameLayout {

    private final String TAG = "VideoPlayerIJK";

    private IMediaPlayer mMediaPlayer = null;

    private String videoUrl = "";

    private SurfaceView surfaceView;
    private SeekBar seekBar;

    private Context mContext;

    private int UPDATE_SEEK_BAR = 0x10;

    private int UPDATE_TIME = 100;//ms

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == UPDATE_SEEK_BAR) {
                if (mMediaPlayer.isPlaying()) {
                    handler.sendEmptyMessageDelayed(UPDATE_SEEK_BAR, UPDATE_TIME);
                    long currPos = mMediaPlayer.getCurrentPosition();
                    long progress = currPos * seekBar.getMax() / mMediaPlayer.getDuration();
                    seekBar.setProgress((int) progress);
                }
            }
        }
    };

    public IJKVideoPlayer(@NonNull Context context) {
        super(context);
        initVideoView(context);
    }

    public IJKVideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initVideoView(context);
    }

    public IJKVideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVideoView(context);
    }

    private void initVideoView(Context context) {
        mContext = context;

        //获取焦点，不知道有没有必要~。~
        setFocusable(true);
    }

    public void startPlay(String path) {
        if (TextUtils.equals("", videoUrl)) {
            videoUrl = path;
            createSurfaceView();
            createSeekBar();
        } else {
            videoUrl = path;
            load();
        }
    }

    /**
     * 新建一个surfaceview
     */
    private void createSurfaceView() {
        //生成一个新的surface view
        surfaceView = new SurfaceView(mContext);
        surfaceView.getHolder().addCallback(new LmnSurfaceCallback());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT
                , LayoutParams.MATCH_PARENT, Gravity.CENTER);
        surfaceView.setLayoutParams(layoutParams);
        this.addView(surfaceView);
    }

    private void createSeekBar() {
        seekBar = (SeekBar) LayoutInflater.from(mContext).inflate(R.layout.layout_seek_bar, null);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        layoutParams.leftMargin = 30;
        layoutParams.rightMargin = 30;
        layoutParams.bottomMargin = UIUtils.INSTANCE.dip2px(5);

        this.addView(seekBar, layoutParams);
    }

    /**
     * surfaceView的监听器
     */
    private class LmnSurfaceCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            load();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
        }
    }

    /**
     * 加载视频
     */
    private void load() {
        //每次都要重新创建IMediaPlayer
        createPlayer();
        try {
            mMediaPlayer.setDataSource(videoUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //给mediaPlayer设置视图
        mMediaPlayer.setDisplay(surfaceView.getHolder());
        mMediaPlayer.setScreenOnWhilePlaying(true);
        mMediaPlayer.prepareAsync();

    }

    /**
     * 创建一个新的player
     */
    private void createPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.setDisplay(null);
            mMediaPlayer.release();
        }
        final IjkMediaPlayer ijkMediaPlayer = new IjkMediaPlayer(getContext());
        ijkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);

        //预处理完成，可获取视频宽高
        ijkMediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                Log.i(TAG, "-----onPrepared");
                mMediaPlayer.start();
                mMediaPlayer.seekTo(0);
                handler.sendEmptyMessageDelayed(UPDATE_SEEK_BAR, UPDATE_TIME);
            }
        });

        //事件发生回调
        ijkMediaPlayer.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer iMediaPlayer, int what, int extra) {
                switch (what) {
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_START://开始缓冲
                        Log.i(TAG, "-----MEDIA_INFO_BUFFERING_START");
                        //加载中...
                        break;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_END://缓冲完成
                        Log.i(TAG, "-----MEDIA_INFO_BUFFERING_END");
                        //播放中...

                        break;
                    case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH://网络带宽
                        Log.i(TAG, "-----MEDIA_INFO_NETWORK_BANDWIDTH--" + extra);
                        //下载速度 = extra
                        break;
                    case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START://开始渲染
                        Log.i(TAG, "-----MEDIA_INFO_VIDEO_RENDERING_START");
                        //播放中...

                        break;
                }

                return false;
            }
        });

        //播放完成
        ijkMediaPlayer.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                Log.i(TAG, "-----onCompletion");
                seekBar.setMax(100);
            }
        });

        //播放错误
        ijkMediaPlayer.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
                Log.i(TAG, "-----onError--" + i + "---" + i1);
                handler.removeMessages(UPDATE_SEEK_BAR);
                return false;
            }
        });

//        //开启硬解码
        //ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);

        mMediaPlayer = ijkMediaPlayer;

    }

    public void start() {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
        }
    }

    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public void pause() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
    }

    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }
    }


    public void reset() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
        }
    }


    public long getDuration() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getDuration();
        } else {
            return 0;
        }
    }


    public long getCurrentPosition() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getCurrentPosition();
        } else {
            return 0;
        }
    }


    public void seekTo(long l) {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(l);
        }
    }
}