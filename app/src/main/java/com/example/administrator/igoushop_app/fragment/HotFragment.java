package com.example.administrator.igoushop_app.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.example.administrator.igoushop_app.R;
import com.example.administrator.igoushop_app.tools.Hot_Tab;


/**
 * Created by Administrator on 2016-05-06.
 */
public class HotFragment extends Fragment {
    private Hot_Tab hot_tab;
    private BrandsFragment brandsFragment = new BrandsFragment();
    private BuyerShowFragment buyerShowFragment = new BuyerShowFragment();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot,container,false);
        hot_tab = (Hot_Tab) view.findViewById(R.id.hot_tab);
        // 首先显示的Fragment
        initView();
        return view;
    }

    private void initView() {

        FragmentTransaction transaction =getFragmentManager().beginTransaction();
        transaction.replace(R.id.tab_show,brandsFragment);
        transaction.addToBackStack(null);
        transaction.commit();

       hot_tab.setOnSegmentViewClickListener(new Hot_Tab.onSegmentViewClickListener() {
           @Override
           public void onSegmentViewClick(View v, int position) {
                if(position==0&&v.isSelected()){
                    FragmentTransaction transaction =getFragmentManager().beginTransaction();
                    transaction.replace(R.id.tab_show,brandsFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }else if(position==1&&v.isSelected()){
                    FragmentTransaction transaction =getFragmentManager().beginTransaction();
                    transaction.replace(R.id.tab_show,buyerShowFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
           }
       });
    }
}
