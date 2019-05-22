package com.example.administrator.igoushop_app_test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.administrator.igoushop_app_test.R;
import com.example.administrator.igoushop_app_test.adapter.BaseAdapter;
import com.example.administrator.igoushop_app_test.adapter.ProductListAdapter;
import com.example.administrator.igoushop_app_test.http.OKManager;
import com.example.administrator.igoushop_app_test.listener.TopTrackListener;
import com.example.administrator.igoushop_app_test.pojos.Brand;
import com.example.administrator.igoushop_app_test.pojos.Product;
import com.example.administrator.igoushop_app_test.tools.JSONUtils;
import com.example.administrator.igoushop_app_test.widget.Contants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016-08-17.
 */
public class  ProductListActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView mRecyclerView;
    private ImageView back;
    private RadioButton Together;
    private RadioButton price;
    private RadioButton saleNum;
    private ProductListAdapter adapter;
    private OKManager manager;
    private  Handler handler;
    private List<Product> products;
    private MaterialRefreshLayout refreshLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        Together = (RadioButton) findViewById(R.id.together);
        price = (RadioButton) findViewById(R.id.price);
        saleNum = (RadioButton) findViewById(R.id.saleNum);
        back = (ImageView) findViewById(R.id.back);
        refreshLayout = (MaterialRefreshLayout) findViewById(R.id.listrefresh);
        back.setOnClickListener(this);
        saleNum.setOnClickListener(this);
        price.setOnClickListener(this);
        Together.setOnClickListener(this);

        LoadData();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 0x123){
                    Brand brand = (Brand) msg.obj;
                    initData(toList(brand.getProducts()));
                }
            }
        };
    }

    private void LoadData() {

        Integer brand_id = Integer.parseInt(getIntent().getStringExtra(Contants.Brand));
        manager = OKManager.getInstance();
        final Map<String,String> map = new HashMap<>();
        map.put("Brand_id",""+brand_id);
        Thread thread = new Thread(){
            @Override
            public void run() {
                manager.sendComplexForm(Contants.BASE_URL + "Brand_returnAllProById.action", map, new OKManager.Func4() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Brand brand = JSONUtils.fromJson(jsonObject.toString(),Brand.class);
                        Message message = new Message();
                        message.what=0x123;
                        message.obj = brand;
                        handler.sendMessage(message);
                    }
                });
            }
        };
        thread.start();
    }

    private void initData(List<Product> products){
        this.products = products;
        adapter = new ProductListAdapter(this,R.layout.product_list_item,products);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.addOnScrollListener(new TopTrackListener(findViewById(R.id.select)));
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Integer product_id = adapter.getItem(position).getId();
                Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
                intent.putExtra(Contants.Product,""+product_id);
                startActivity(intent);
            }
        });
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                LoadData();
                refreshLayout.finishRefresh();

            }
        });
    }


    private List<Product> toList(Set<Product> products) {
        List<Product> list = new ArrayList<>();
        for (Product p:products){
            list.add(p);
        }
        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.together:
                adapter.updateProList(this.products);
                break;
            case R.id.price:
                //按照 价格进行排序
                adapter.updateProList(sortByPrice(adapter.getProList()));
                break;
            case R.id.saleNum:
                adapter.updateProList(sortBySaleNum(adapter.getProList()));
                break;
        }
    }

    private List<Product> sortBySaleNum(List<Product> products) {
        List<Product> list = products;
        Collections.sort(list, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                if(p1.getSaleNum() < p2.getSaleNum()){
                    return 1;
                }
                if(p1.getPrice() == p2.getPrice()){
                    return 0;
                }
                return -1;
            }
        });
        return list;
    }

    private List<Product> sortByPrice(List<Product> products) {
        List<Product> list = products;
        Collections.sort(list, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                if(p1.getPrice() > p2.getPrice()){
                    return 1;
                }
                if(p1.getPrice() == p2.getPrice()){
                    return 0;
                }
                return -1;
            }
        });
        return list;
    }
}
