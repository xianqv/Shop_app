package com.example.administrator.igoushop_app.adapter;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.example.administrator.igoushop_app.bean.HotTab;

import java.util.List;

/**
 * Created by Administrator on 2016-06-30.
 */
public class WareDetailAdapter extends FragmentPagerAdapter {
    private List<HotTab> mtabList;

    public WareDetailAdapter(FragmentManager fm, List<HotTab> mtabList) {
        super(fm);
        this.mtabList = mtabList;
    }


    @Override
    public Fragment getItem(int position) {
        return mtabList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mtabList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mtabList.get(position).getTitle();
    }
}
