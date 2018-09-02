package com.example.sumeetharyani.accomplisher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MotivatonFragment extends Fragment {


    public MotivatonFragment() {

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View motivationView = inflater.inflate(R.layout.fragment_motivaton, container, false);

        MotivationVerticalViewPager motivationVerticalViewPager = motivationView.findViewById(R.id.viewPager);
        motivationVerticalViewPager.setAdapter(new MotivationVerticalViewPagerAdapter(getActivity()));

        return motivationView;


    }


}
