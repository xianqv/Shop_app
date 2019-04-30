package com.example.administrator.igoushop_app.fragment.hot_fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.igoushop_app.R;
import com.example.administrator.igoushop_app.http.OKManager;
import com.example.administrator.igoushop_app.widget.Contants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Administrator on 2016-06-23.
 */
public class Fragment_two extends Fragment {
    private OKManager manager  ;
    private SimpleDraweeView draweeView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Fresco.initialize(getContext());
        View view = inflater.inflate(R.layout.fragment_hot_two,container,false);
        draweeView = (SimpleDraweeView) view.findViewById(R.id.brand_image);
        draweeView.setImageURI(Uri.parse(Contants.Img_URL+"productShowImg/17.jpg"));
        return view;
    }


}