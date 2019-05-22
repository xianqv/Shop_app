package com.example.administrator.igoushop_app_test.fragment.WareDetailFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.administrator.igoushop_app_test.R;
import com.example.administrator.igoushop_app_test.activity.ProductDetailActivity;
import com.example.administrator.igoushop_app_test.http.OKManager;
import com.example.administrator.igoushop_app_test.pojos.Product;
import com.example.administrator.igoushop_app_test.widget.ChioesView;
import com.example.administrator.igoushop_app_test.widget.Contants;
import com.example.administrator.igoushop_app_test.widget.ProductDetail;
import com.example.administrator.igoushop_app_test.widget.ProductSelectAttr;

/**
 * Created by Administrator on 2016-07-12.
 */
public class  WareDetailInfo extends Fragment {
    private TextView Pro_name,Pro_num,Pro_price,Pro_address;
    private ChioesView chioesAttribute,chioseSize;
    private ProductDetail detail;
    private ProductSelectAttr productSelectAttr;
    private OKManager manager;
    private TextSliderView textSliderView;
    private SliderLayout sliderLayout;
    private ProductDetailActivity activity;
    private Product product;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProductDetailActivity activity = (ProductDetailActivity) getActivity();
        this.activity = activity;

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.myReceiver");
        activity.registerReceiver(new MyReceiver(),filter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.waredetailinfo,container,false);
        sliderLayout = (SliderLayout) view.findViewById(R.id.slider);
        Pro_name = (TextView) view.findViewById(R.id.name);
        Pro_price = (TextView) view.findViewById(R.id.price);
        Pro_num = (TextView) view.findViewById(R.id.num);
        Pro_address = (TextView) view.findViewById(R.id.address);
        chioesAttribute = (ChioesView) view.findViewById(R.id.pro_attribute);
        chioseSize = (ChioesView) view.findViewById(R.id.pro_color_size);
        return view;
    }


    private void initView( Product obj) {
            if (obj!=null) {
                textSliderView = new TextSliderView(getActivity());
                textSliderView.image(Contants.Img_URL + obj.getImgUrl());
                sliderLayout.addSlider(textSliderView);
                Pro_name.setText(obj.getName());
                Pro_price.setText("¥ " + obj.getPrice());
                Pro_num.setText("销售量: " + obj.getSaleNum());
                Pro_address.setText(obj.getAddress());
                chioesAttribute.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.detailShow();
                    }
                });
                chioseSize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.SelectAttrShow();
                        activity.productSelectAttr.showButton();
                    }
                });
            }

    }


    public  class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            initView((Product) intent.getSerializableExtra("product"));
        }
    }
}
