package com.example.administrator.igoushop_app_test.fragment;



import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.administrator.igoushop_app_test.R;
import com.example.administrator.igoushop_app_test.activity.ProductDetailActivity;
import com.example.administrator.igoushop_app_test.adapter.BaseAdapter;
import com.example.administrator.igoushop_app_test.adapter.ClassFragmentLeftAdapter;
import com.example.administrator.igoushop_app_test.adapter.ProductsAdapter;
import com.example.administrator.igoushop_app_test.http.OKManager;
import com.example.administrator.igoushop_app_test.pojos.Product;
import com.example.administrator.igoushop_app_test.pojos.ProductType;
import com.example.administrator.igoushop_app_test.tools.JacksonUtils;
import com.example.administrator.igoushop_app_test.widget.Contants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2016-06-06.
 */
public class ClassifyFragment extends Fragment {
    private SliderLayout mSliderLayout;
    private RecyclerView TitleRecyclerView;
    private RecyclerView productsRecyclerView;
    private MaterialRefreshLayout refreshLayout;
    private ClassFragmentLeftAdapter leftAdapter;
    private ProductsAdapter productsAdapter;
    private OKManager manager;
    private int currentIndex =0;
    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classify,container,false);
        Fresco.initialize(getContext());
        TitleRecyclerView = (RecyclerView) view.findViewById(R.id.categorytitle);
        productsRecyclerView = (RecyclerView) view.findViewById(R.id.showwares);
        mSliderLayout = (SliderLayout) view.findViewById(R.id.showguanggao);
        refreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.classifyrefresh);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 0x123){
                    List<ProductType> productTypes = (List<ProductType>) msg.obj;
                    requestCategoryData(productTypes);
                }
            }
        };
        init();
    }

    private void init() {
        initProductType();
        requestSliderDate();
    }

    private  void initProductType() {
        manager = OKManager.getInstance();
        Thread thread = new Thread(){
            @Override
            public void run() {
                manager.asyncJsonStringByURL(Contants.BASE_URL + "ProductType_returnAllType.action", new OKManager.Func1() {
                    @Override
                    public void onResponse(String result) throws IOException {
                        List<ProductType> productTypes = JacksonUtils.toList(result,ArrayList.class,ProductType.class);
                        Message message = new Message();
                        message.what = 0x123;
                        message.obj = productTypes;
                        handler.sendMessage(message);
                    }
                });
            }
        };
        thread.start();
    }

    private void requestSliderDate() {
        TextSliderView textSliderView = new TextSliderView(this.getActivity());
        textSliderView.description("示例")
                .image("http://5.66825.com/download/pic/000/324/82f823119473a3de2bf91b853fcbc567.jpg");
        mSliderLayout.addSlider(textSliderView);
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        mSliderLayout.setDuration(3000);
    }

    private void requestCategoryData(List<ProductType> types) {

        leftAdapter = new ClassFragmentLeftAdapter(getActivity(),R.layout.fragment_classify_title_text,types);
        TitleRecyclerView.setAdapter(leftAdapter);
        TitleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置默认动画
        TitleRecyclerView.setItemAnimator(new DefaultItemAnimator());

        initWares(types.get(currentIndex).getId());

        leftAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                currentIndex = position;
                Integer  id =  leftAdapter.getItem(currentIndex).getId();
                initWares(id);
            }
        });
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                init();
                refreshLayout.finishRefresh();
            }
        });
    }

    private void initWares(Integer id){
        Map<String,String> map = new HashMap<>();
        map.put("type.id",""+id);
        manager.sendComplexForm(Contants.BASE_URL + "ProductType_getProductsByTypeName.action", map, new OKManager.Func4() {
            @Override
            public void onResponse(JSONObject  jsonObject) {
                Gson gson = new Gson();
                ProductType item = gson.fromJson(jsonObject.toString(),ProductType.class);
                if(item!=null){
                    initProductList(toProList(item));
                }
            }
        });
    }

    private void initProductList(List<Product> Products){
        productsAdapter = new ProductsAdapter(getContext(),R.layout.product_list_item,Products);
        productsRecyclerView.setAdapter(productsAdapter);
        productsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        //设置默认动画
        productsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        productsAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Product product = productsAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                intent.putExtra(Contants.Product,""+product.getId());
                startActivity(intent);
            }
        });
    }
    private List<Product> toProList(ProductType item) {
        List<Product> list = new ArrayList<>();
        if(item.getProducts()!=null){
            for(Product product :item.getProducts()){
                list.add(product);
            }
        }
        return list;
    }
}
