package com.example.sumeetharyani.accomplisher;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.sumeetharyani.accomplisher.motivation.VideoFragment;
import com.example.sumeetharyani.accomplisher.tasks.TaskFragment;
import com.example.sumeetharyani.accomplisher.wallpaper.WallpaperFragment;

class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {


    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TaskFragment();
            case 1:
                return new VideoFragment();
            case 2:
                return new WallpaperFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}

