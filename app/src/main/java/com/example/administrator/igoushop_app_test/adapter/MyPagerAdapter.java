package com.example.administrator.igoushop_app_test.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.example.administrator.igoushop_app_test.bean.HotTab;

import java.util.List;

/**
 * Created by Administrator on 2016-06-22.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    private List<HotTab> mtablist;
    public MyPagerAdapter(FragmentManager fm,List<HotTab> mtablist) {
        super(fm);
        this.mtablist = mtablist;
    }
    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return mtablist.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mtablist.get(position).getTitle();//页卡标题
    }
}
