package com.dong.easy.video;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dong.easy.R;
import com.dong.easy.util.UIUtils;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2018/2/5.
 */
public class VideoFragment extends Fragment {

    private static final String ARG_POSITION = "section_number";

    private String bgColor = "";

    private IJKVideoPlayer video_player;

    private String[] urlArr = {
            "http://jmvideo.jumei.com/MTk1NDM_E/MTUxNzA2MzgyMTA0NQ_E_E/MTU4MzEyNg_E_E/anVtZWkubXA0_default.mp4",
            "http://jmvideo.jumei.com/MTk1NTA_E/MTUxNzA2NzI2ODQwMg_E_E/MTU4MzEyNg_E_E/anVtZWkubXA0_default.mp4",
            "http://jmvideo.jumei.com/MTk1NTA_E/MTUxNjc4ODM5MzA3Ng_E_E/MTU3NjAxNg_E_E/anVtZWkubXA0_default.mp4",
            "http://jmvideo.jumei.com/MTk1NTA_E/MTUxNjc4ODIyOTIyNA_E_E/MTU3NjAxNg_E_E/anVtZWkubXA0_default.mp4",
            "http://jmvideo.jumei.com/MTk1NDM_E/MTUxNjg0NzU0MTgwMQ_E_E/MTU4MzEyNg_E_E/anVtZWkubXA0_default.mp4",
            "http://jmvideo.jumei.com/MTk1NDM_E/MTUxNzEyNTA0MzI0Mw_E_E/MTkzNTIzNg_E_E/anVtZWkubXA0_default.mp4",
            "http://jmvideo.jumei.com/MTk1NDM_E/MTUxNzEyNTA0MzI0Mw_E_E/MTkzNTIzNg_E_E/anVtZWkubXA0_default.mp4",
            "http://jmvideo.jumei.com/MTk1ODE_E/MTUxNzA2NjY2NjYwMQ_E_E/MzIyMDQ0MQ_E_E/anVtZWkubXA0_default.mp4",
            "http://jmvideo.jumei.com/MTk1NDk_E/MTUxNjc5MTAzOTQxOA_E_E/MTU3NjAxNg_E_E/anVtZWkubXA0_default.mp4",
            "http://jmvideo.jumei.com/MTk1OTU_E/MTUxNzIwMjA4OTIyNA_E_E/NDUxMDA4NQ_E_E/anVtZWkubXA0_default.mp4",
            "http://jmvideo.jumei.com/MTk2NDc_E/MTUxNzU1Mzg4MDE1MQ_E_E/NDUxMDA4NQ_E_E/anVtZWkubXA0_default.mp4",
            "http://jmvideo.jumei.com/MTkwNzI_E/MTUxNzQ3NDc4OTczMQ_E_E/MjgzNDA5Mw_E_E/anVtZWkubXA0_default.mp4",
            "http://jmvideo.jumei.com/MTk1NTA_E/MTUxNzA2ODA4NDk5NA_E_E/MTU4MzEyNg_E_E/anVtZWkubXA0_default.mp4",
            "http://jmvideo.jumei.com/MTk1NTA_E/MTUxNzA2ODQ3ODUyNw_E_E/MTU4MzEyNg_E_E/anVtZWkubXA0_default.mp4",
            "http://jmvideo.jumei.com/MTk1NDM_E/MTUxNzE5MTI0ODMzMw_E_E/MTU4MzEyNg_E_E/anVtZWkubXA0_default.mp4",
            "",
            "",
            "",
            "",
            "",
            "",
    };

    public static VideoFragment newInstance(int position) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (TextUtils.isEmpty(bgColor)) {
            bgColor = UIUtils.INSTANCE.randomColor();
        }


        View rootView = inflater.inflate(R.layout.fragment_video, container, false);
//        RelativeLayout rl_root_view = (RelativeLayout) rootView.findViewById(R.id.rl_root_view);
//        rl_root_view.setBackgroundColor(Color.parseColor(bgColor));
        video_player = (IJKVideoPlayer) rootView.findViewById(R.id.video_player);

        rootView.findViewById(R.id.btn_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlay();
            }
        });

        return rootView;
    }

    public void startPlay() {
        int pos = getArguments().getInt(ARG_POSITION);
        String url = urlArr[pos];
        if (!TextUtils.isEmpty(url)) {
            video_player.startPlay(url);
        }
    }

    public void stopPlay() {
        video_player.stop();
    }

    public void preLoading() {
        startPlay();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //video_player.pause();
            }
        }, 500);
    }

    @Override
    public void onPause() {
        super.onPause();
        video_player.pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        video_player.stop();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
