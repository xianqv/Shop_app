package com.example.administrator.igoushop_app_test.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.administrator.igoushop_app_test.R;
import com.example.administrator.igoushop_app_test.activity.HomeClickActivity;
import com.example.administrator.igoushop_app_test.adapter.HomeButtonAdapter;
import com.example.administrator.igoushop_app_test.adapter.HomeCatgoryAdapter;
import com.example.administrator.igoushop_app_test.bean.HomeButton;
import com.example.administrator.igoushop_app_test.bean.Order;
import com.example.administrator.igoushop_app_test.http.OKManager;
import com.example.administrator.igoushop_app_test.pojos.Advert;
import com.example.administrator.igoushop_app_test.pojos.FunctionButton;
import com.example.administrator.igoushop_app_test.tools.JacksonUtils;
import com.example.administrator.igoushop_app_test.widget.Contants;
import com.example.administrator.igoushop_app_test.widget.CustomGridView;
import com.example.administrator.igoushop_app_test.widget.HomeWebView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-06-02.
 */
public class HomeFragment extends Fragment {
//    @ViewInject(R.id.slider)
    private SliderLayout msliderLayout;
    private EditText search;
    private CustomGridView gridViewButton;
    private MaterialRefreshLayout refreshLayout;
    private List<Order> orderlist = new ArrayList<>();
    private OKManager manager;
    private HomeCatgoryAdapter mAdatper;
    private List<HomeButton> buttonList = new ArrayList<>();
    private HomeButtonAdapter homeButtonAdapter;
    private Handler handler;
    private HomeWebView webView;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        msliderLayout = (SliderLayout) view.findViewById(R.id.slider);
//        mrecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        gridViewButton = (CustomGridView) view.findViewById(R.id.server_button);
        refreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.homerefresh);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                initView();
                refreshLayout.finishRefresh();
            }
        });
        search = (EditText) view.findViewById(R.id.search);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //处理事件
                    Intent intent = new Intent(getActivity(),HomeClickActivity.class);
                    intent.setFlags(1);
                    intent.setType(search.getText().toString());
                    startActivity(intent);
                }
                return false;
            }
        });

        initWebView(view);
        return view;
    }
    private void initWebView(View view) {
        webView = (HomeWebView) view.findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");

        webView.addJavascriptInterface(new MyObject(getContext()), "jsObj");
        //找到Html文件，也可以用网络上的文件
        webView.loadUrl("file:///android_asset/homeWebView.html");
    }
    class MyObject{
        Context context;

        public MyObject(Context context) {
            this.context = context;
        }
        @JavascriptInterface
        public void showToast(String string){
            Intent intent = new Intent(getActivity(), HomeClickActivity.class);
            intent.setFlags(0);
            startActivity(intent);
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        LoadingButton();
        LoadingSliderImage();

    }




    private void LoadingButton() {
        manager = OKManager.getInstance();
        manager.asyncJsonStringByURL(Contants.BASE_URL+"FunctionButton_returnAllButton.action", new OKManager.Func1() {
            @Override
            public void onResponse(String result) throws IOException {
                initButton(JacksonUtils.toList(result,ArrayList.class, FunctionButton.class));
            }
        });


    }


    private void initButton(List<FunctionButton> buttons) {

        homeButtonAdapter = new HomeButtonAdapter(getActivity(),buttons);
        gridViewButton.setAdapter(homeButtonAdapter);

        gridViewButton.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridViewButton.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                homeButtonAdapter.notifyDataSetInvalidated();
            }
        });
    }



    private void LoadingSliderImage() {
        manager = OKManager.getInstance();
        manager.asyncJsonStringByURL(Contants.BASE_URL + "Advert_returnAllAdvert.action", new OKManager.Func1() {
            @Override
            public void onResponse(String result) throws IOException {
                initSliderLayout(JacksonUtils.toList(result,ArrayList.class,Advert.class));
            }
        });
    }
//jockeyjs
    private void initSliderLayout(List<Advert> advertlist) {
        TextSliderView textSliderView;
        msliderLayout.removeAllSliders();
        for(Advert advert :advertlist ){
            textSliderView = new TextSliderView(this.getActivity());
            textSliderView.description(advert.getDes())
                    .image(Contants.Img_URL+advert.getImageUrl());
            msliderLayout.addSlider(textSliderView);
        }
        msliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        msliderLayout.setCustomAnimation(new DescriptionAnimation());
        msliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        msliderLayout.setDuration(3000);
    }



}
