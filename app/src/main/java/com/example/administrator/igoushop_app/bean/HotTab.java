package com.example.administrator.igoushop_app.bean;

import android.support.v4.app.Fragment;
import android.view.View;



/**
 * Created by Administrator on 2016-06-21.
 */
public class HotTab  {
    private String title;
    private Fragment fragment;

    public HotTab(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
