package com.example.administrator.igoushop_app.fragment.WareDetailFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.administrator.igoushop_app.R;

/**
 * Created by Administrator on 2016-07-12.
 */
public class WareDetailPicture extends Fragment {
    private WebView webView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.waredetailpicture,container,false);
        webView = (WebView) view.findViewById(R.id.webview);
        initView();
        return view;
    }

    private void initView() {
        webView.loadUrl("http://172.16.38.121:8080/test/views.html");

    }
}
