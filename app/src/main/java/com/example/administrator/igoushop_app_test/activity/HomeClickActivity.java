package com.example.administrator.igoushop_app_test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.igoushop_app_test.R;
import com.example.administrator.igoushop_app_test.adapter.HomeCatgoryAdapter;
import com.example.administrator.igoushop_app_test.http.OKManager;
import com.example.administrator.igoushop_app_test.pojos.Product;
import com.example.administrator.igoushop_app_test.pojos.ProductClassify;
import com.example.administrator.igoushop_app_test.tools.JSONUtils;
import com.example.administrator.igoushop_app_test.tools.JacksonUtils;
import com.example.administrator.igoushop_app_test.widget.CardViewtemDecortion;
import com.example.administrator.igoushop_app_test.widget.Contants;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/4/3.
 */

public class HomeClickActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private HomeCatgoryAdapter adapter;
    private OKManager manager = OKManager.getInstance();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123){
                bindDate((List<ProductClassify>) msg.obj);
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_layout);
    }

    @Override
    protected void onResume() {
        super.onRestart();
        initView();
        initDate();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initDate() {
        LemonBubble.showRoundProgress(HomeClickActivity.this,"加载中...");
        Intent intent = getIntent();
        int flag = intent.getFlags();
        if (flag == 0) {
            manager.asyncJsonStringByURL(Contants.BASE_URL + "ProductClassify_returnAllClassify.action", new OKManager.Func1() {
                @Override
                public void onResponse(String result) throws IOException {
                    try {

                        if (result != null) {
                            List<ProductClassify> dates = JacksonUtils.toList(result, ArrayList.class, ProductClassify.class);
                            if (dates.size() > 0 || dates != null) {
                                Message message = new Message();
                                message.what = 0x123;
                                message.obj = dates;
                                handler.sendMessage(message);
                                Thread.sleep(2000);
                                LemonBubble.hide();
                            }
                        } else {
                            LemonBubble.showError(HomeClickActivity.this, "未搜索到数据");
                            LemonBubble.hide();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        LemonBubble.hide();
                        LemonBubble.showError(HomeClickActivity.this,"系统异常!");
                        LemonBubble.hide();
                    }
                }
            });
        }else if (flag == 1){
            String SearchValue = intent.getType();
            Map<String,String> params = new HashMap<>();
            params.put("searchValue",SearchValue);
            manager.sendComplexForm(Contants.BASE_URL + "Product_search.action", params, new OKManager.Func4() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        Integer success = (Integer) jsonObject.get("success");
                        String message = (String) jsonObject.get("msg");
                        if(success == 1){
                            Set<Product> products = toProductSet(jsonObject);
                            Toast.makeText(HomeClickActivity.this,message,Toast.LENGTH_SHORT).show();
                            ProductClassify classify = new ProductClassify();
                            classify.setProducts(products);
                            List<ProductClassify> classifies = new ArrayList<ProductClassify>();
                            classifies.add(classify);
                            bindDate(classifies);
                            LemonBubble.hide();
                        }else if (success == 0){
                            Toast.makeText(HomeClickActivity.this,message,Toast.LENGTH_SHORT).show();
                            LemonBubble.hide();
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }



    }

    private Set<Product> toProductSet(JSONObject result) {
        Set<Product> set = null;
        try {
            set = new HashSet<>();
           JSONArray array = result.getJSONArray("products");
            int length = array.length();
            for (int i = 0; i < length; i++){
                JSONObject oj = array.getJSONObject(i);
                Product product = JSONUtils.fromJson(oj.toString(),Product.class);
                set.add(product);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return set;
    }


    private void bindDate(List<ProductClassify> dates) {
        List<Product> list = new ArrayList<>();
        for (ProductClassify classify: dates){
            list.addAll(classify.getProducts());
        }
        adapter = new HomeCatgoryAdapter(HomeClickActivity.this,R.layout.homeclick_item,list);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new CardViewtemDecortion());
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeClickActivity.this));
        adapter.setListener(new HomeCatgoryAdapter.setonItemClickListener() {
            @Override
            public void toBuy(int id) {
                Intent intent = new Intent(HomeClickActivity.this, ProductDetailActivity.class);
                intent.putExtra(Contants.Product,""+id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
