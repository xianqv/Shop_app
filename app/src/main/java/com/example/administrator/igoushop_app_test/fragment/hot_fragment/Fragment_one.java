package com.example.administrator.igoushop_app_test.fragment.hot_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.administrator.igoushop_app_test.activity.ProductListActivity;
import com.example.administrator.igoushop_app_test.adapter.BaseAdapter;
import com.example.administrator.igoushop_app_test.adapter.hot_adapter.HotOneAdapter;
import com.example.administrator.igoushop_app_test.http.OKManager;
import com.example.administrator.igoushop_app_test.pojos.Brand;
import com.example.administrator.igoushop_app_test.R;
import com.example.administrator.igoushop_app_test.tools.JacksonUtils;
import com.example.administrator.igoushop_app_test.widget.Contants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-06-23.
 */
public class Fragment_one extends Fragment {
    private HotOneAdapter adapter;
    private RecyclerView mrecyclerView;
    private MaterialRefreshLayout mrefreshLayout;
    private Handler handler;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_left_brand,container,false);
        mrecyclerView = (RecyclerView) view.findViewById(R.id.hotshow);
        mrefreshLayout  = (MaterialRefreshLayout) view.findViewById(R.id.refresh);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 0x123){
                    initView((List<Brand>) msg.obj);
                }
            }
        };
    }

    private void initData() {

        Thread thread = new Thread(){
            @Override
            public void run() {
                OKManager manager = OKManager.getInstance();
                manager.asyncJsonStringByURL(Contants.BASE_URL+"Brand_toJson.action", new OKManager.Func1() {
                    @Override
                    public void onResponse(String result) throws IOException {
                        List<Brand> BrandData =  JacksonUtils.toList(result, ArrayList.class,Brand.class);
                        Message message = new Message();
                        message.what = 0x123;
                        message.obj = BrandData;
                        handler.sendMessage(message);
                    }
                });
            }
        };
        thread.start();
    }

    private void initView(final List<Brand> BrandData) {
        adapter = new HotOneAdapter(getContext(),R.layout.fragment_hot_one,BrandData);
        mrecyclerView.setAdapter(adapter);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Integer  brand_id = adapter.getItem(position).getId();
                //转跳 到指定Activity
                Intent intent = new Intent(getActivity(), ProductListActivity.class);
                //设置所携带的数据
                intent.putExtra(Contants.Brand,""+brand_id);
                //转跳activity
                startActivity(intent);
            }
        });
        mrefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                initData();
                mrefreshLayout.finishRefresh();
            }
        });
    }

}
