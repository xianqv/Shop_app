package com.example.administrator.igoushop_app.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.administrator.igoushop_app.R;
import com.example.administrator.igoushop_app.adapter.MyViewPagerAdapter;
import com.example.administrator.igoushop_app.bean.HotTab;
import com.example.administrator.igoushop_app.fragment.hot_fragment.Fragment_five;
import com.example.administrator.igoushop_app.fragment.hot_fragment.Fragment_four;
import com.example.administrator.igoushop_app.fragment.hot_fragment.Fragment_one;
import com.example.administrator.igoushop_app.fragment.hot_fragment.Fragment_three;
import com.example.administrator.igoushop_app.fragment.hot_fragment.Fragment_two;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-06-22.
 */
public class  BrandsFragment  extends Fragment {
    private List<HotTab> mdate;
    private ViewPager mviewPager;
    private TabLayout mtabLayout;
    private MyViewPagerAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brands,container,false);
        mviewPager = (ViewPager) view.findViewById(R.id.fragmentpager);
        mtabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        initView();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initView() {
        mdate = new ArrayList<>();
        initDate();
        adapter = new MyViewPagerAdapter(getChildFragmentManager(),mdate,getContext());
        mviewPager.setAdapter(adapter);
        mtabLayout.setupWithViewPager(mviewPager);
        mtabLayout.setTabMode(TabLayout.MODE_FIXED);
        mtabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(getContext(),""+tab.getPosition(),Toast.LENGTH_SHORT).show();
                mviewPager.setCurrentItem(tab.getPosition(),true);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initDate() {
        HotTab tab1 = new HotTab("大牌",new Fragment_one());
        HotTab tab2 = new HotTab("男装",new Fragment_two());
        HotTab tab3 = new HotTab("女装",new Fragment_three());
        HotTab tab4 = new HotTab("运动",new Fragment_four());
        HotTab tab5 = new HotTab("鞋包",new Fragment_five());
        mdate.add(tab1);
        mdate.add(tab2);
        mdate.add(tab3);
        mdate.add(tab4);
        mdate.add(tab5);

    }

}
