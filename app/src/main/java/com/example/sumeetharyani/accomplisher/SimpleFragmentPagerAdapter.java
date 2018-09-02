package com.example.sumeetharyani.accomplisher;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    Context mContext;


    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:

                return new TaskFragment();

            case 1:

                return new ProgressFragment();

            case 2:

                return new MotivatonFragment();
            default:
                return null;
        }


    }

    @Override
    public int getCount() {

        return 3;
    }
}

