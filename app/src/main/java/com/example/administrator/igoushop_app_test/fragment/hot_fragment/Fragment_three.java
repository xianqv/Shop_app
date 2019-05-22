package com.example.administrator.igoushop_app_test.fragment.hot_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.igoushop_app_test.R;

/**
 * Created by Administrator on 2016-06-23.
 */
public class Fragment_three extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_left_brand,container,false);

        return view;
    }
}
