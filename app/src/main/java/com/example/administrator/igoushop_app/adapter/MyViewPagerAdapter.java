package com.example.administrator.igoushop_app.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.example.administrator.igoushop_app.bean.HotTab;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-06-24.
 */
public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private List<HotTab> fragment;
    private Context context;
    public MyViewPagerAdapter(FragmentManager fm, List<HotTab> fragment, Context context) {
        super(fm);
        this.fragment = fragment;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragment.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return fragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragment.get(position).getTitle();
    }
}
