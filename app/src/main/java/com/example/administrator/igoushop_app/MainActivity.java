package com.example.administrator.igoushop_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.administrator.igoushop_app.activity.LoginActivity;
import com.example.administrator.igoushop_app.bean.Tab;
import com.example.administrator.igoushop_app.fragment.CartFragment;
import com.example.administrator.igoushop_app.fragment.ClassifyFragment;
import com.example.administrator.igoushop_app.fragment.HomeFragment;
import com.example.administrator.igoushop_app.fragment.HotFragment;
import com.example.administrator.igoushop_app.fragment.MineFragment;
import com.example.administrator.igoushop_app.widget.FragmentTabHost;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private FragmentTabHost mtabHost;
    private LayoutInflater minflater;
    private List<Tab> mtablist = new ArrayList<Tab>(5);
    private  List<String> tabs = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTab();
    }

    private void initTab() {
        mtabHost = (FragmentTabHost) this.findViewById(android.R.id.tabhost);
        minflater = LayoutInflater.from(this);
        mtabHost.setup(this,getSupportFragmentManager(),R.id.realtabcontent);

        //实例化Tab
        Tab home = new Tab(R.string.home,R.drawable.select_icon_home, HomeFragment.class);
        Tab hot = new Tab(R.string.hot,R.drawable.select_icon_hot, HotFragment.class);
        Tab category = new Tab(R.string.category,R.drawable.select_icon_categroy, ClassifyFragment.class);
        Tab cart = new Tab(R.string.cart,R.drawable.select_icon_cart, CartFragment.class);
        Tab mine = new Tab(R.string.mine,R.drawable.select_icon_mine, MineFragment.class);
        mtablist.add(home);
        mtablist.add(hot);
        mtablist.add(category);
        mtablist.add(cart);
        mtablist.add(mine);

        for (Tab tab :mtablist) {
            TabHost.TabSpec tabSpec = mtabHost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));
            mtabHost.addTab(tabSpec,tab.getFragment(),null);
        }
        //去掉分隔符
        mtabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mtabHost.setCurrentTab(0);
        tabs.add("首页");
        mtabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
               tabs.add(tabId);
                if (tabId.equals("购物车")) {
                    if (!Myapplication.isOnline()) {
                        WindowManager windowManager = getWindowManager();
                        Display display = windowManager.getDefaultDisplay();
                        int width = display.getWidth();
                        int height = display.getHeight();
                        final AlertDialog myDialog = new AlertDialog.Builder(MainActivity.this).create();
                        myDialog.show();
                        myDialog.getWindow().setLayout((int) (width*0.75), (int) (height/2*0.75*0.75));
                        myDialog.getWindow().setContentView(R.layout.login_alert_dialog_layout);
                        myDialog.getWindow()
                                .findViewById(R.id.alert_btn)
                                .setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                        myDialog.dismiss();
                                    }
                                });
                        myDialog.getWindow().findViewById(R.id.alert_cancel)
                                .setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        myDialog.dismiss();
                                        setCurrentTab();
                                    }
                                });
                    }else{

                    }
                }
            }
        });
    }

    private View buildIndicator(Tab tab) {
        View view = minflater.inflate(R.layout.tab_indicator,null);
        ImageView img = (ImageView) view.findViewById(R.id.icon_tab);
        TextView text = (TextView) view.findViewById(R.id.txt_indicator);
        img.setImageResource(tab.getIcon());
        text.setText(getString(tab.getTitle()));
        return view;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        setCurrentTab();
    }
    public void setCurrentTab(){
        if (tabs.size()>=2) {
            String tabName = tabs.get(tabs.size() - 2);
            if (tabName.equals("首页")){
                mtabHost.setCurrentTab(0);
            }
            else if (tabName.equals("热卖")){
                mtabHost.setCurrentTab(1);
            }
            else if (tabName.equals("分类")){
                mtabHost.setCurrentTab(2);
            }
            else if (tabName.equals("我的")){
                mtabHost.setCurrentTab(4);
            }else {
                mtabHost.setCurrentTab(0);
            }

        }
    }

}

