package com.dong.easy.video.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dong.easy.video.VideoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2018/2/5.
 */
public class VideoFragmentAdapter extends FragmentPagerAdapter {

    private List<VideoFragment> fragmentList = new ArrayList<>();

    public VideoFragmentAdapter(FragmentManager fm) {
        super(fm);
        for (int i = 0; i < 15; i++) {
            fragmentList.add(VideoFragment.newInstance(i));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public VideoFragment getFragment(int position) {
        return fragmentList.get(position);
    }

    public VideoFragment getNextFragment(int position) {
        int next = position + 1;
        if (next < getCount()) {
            return fragmentList.get(next);
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "PAGE 0";
            case 1:
                return "PAGE 1";
            case 2:
                return "PAGE 2";
            case 3:
                return "PAGE 3";
            case 4:
                return "PAGE 4";
            case 5:
                return "PAGE 5";
            case 6:
                return "PAGE 6";
            case 7:
                return "PAGE 7";
            case 8:
                return "PAGE 8";
            case 9:
                return "PAGE 9";
            case 10:
                return "PAGE 10";
            case 11:
                return "PAGE 11";
            case 12:
                return "PAGE 12";
            case 13:
                return "PAGE 13";
            case 14:
                return "PAGE 14";
            case 15:
                return "PAGE 15";
            case 16:
                return "PAGE 16";
            case 17:
                return "PAGE 17";
        }
        return null;
    }

}
