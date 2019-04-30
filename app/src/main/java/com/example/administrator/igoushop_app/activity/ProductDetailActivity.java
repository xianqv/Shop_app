package com.example.administrator.igoushop_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.igoushop_app.R;
import com.example.administrator.igoushop_app.adapter.WareDetailAdapter;
import com.example.administrator.igoushop_app.bean.HotTab;
import com.example.administrator.igoushop_app.fragment.WareDetailFragment.WareDetailComment;
import com.example.administrator.igoushop_app.fragment.WareDetailFragment.WareDetailInfo;
import com.example.administrator.igoushop_app.fragment.WareDetailFragment.WareDetailPicture;
import com.example.administrator.igoushop_app.http.OKManager;
import com.example.administrator.igoushop_app.pojos.FEMOrder;
import com.example.administrator.igoushop_app.pojos.Product;
import com.example.administrator.igoushop_app.tools.JSONUtils;
import com.example.administrator.igoushop_app.widget.Contants;
import com.example.administrator.igoushop_app.widget.ProductDetail;
import com.example.administrator.igoushop_app.widget.ProductSelectAttr;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-07-11.
 */
public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private List<HotTab> tabList = new ArrayList<>();
    private TabLayout tabLayout;
    private TextView addCart,buy;
    private LinearLayout savePro;
    private ViewPager pager;
    private WareDetailAdapter adapter;
    private Toolbar toolbar;
    public ProductSelectAttr productSelectAttr;
    private ProductDetail detail;
    public Product product;
    private OKManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.waredetail);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        pager = (ViewPager) findViewById(R.id.pager);
        toolbar = (Toolbar) findViewById(R.id.back);
        addCart = (TextView) findViewById(R.id.addcart);
        buy = (TextView) findViewById(R.id.buy);
        buy.setOnClickListener(this);
        addCart.setOnClickListener(this);
        savePro = (LinearLayout) findViewById(R.id.save);
        initView();

        manager = OKManager.getInstance();
        Thread thread = new Thread(){
            @Override
            public void run() {
                Integer product_id = Integer.parseInt(getIntent().getStringExtra(Contants.Product));
                Map<String,String> map = new HashMap<>();
                map.put("product_id",""+product_id);
                manager.sendComplexForm(Contants.BASE_URL + "Product_returnProductById.action", map, new OKManager.Func4() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                      Product pro = JSONUtils.fromJson(jsonObject.toString(),Product.class);
                        if(pro!=null) {
                            detail = new ProductDetail(ProductDetailActivity.this,pro);
                            product = pro;
                            Intent intent = new Intent();
                            intent.setAction("com.example.myReceiver");
                            intent.putExtra("product", pro);
                            sendBroadcast(intent);
                            productSelectAttr = new ProductSelectAttr(ProductDetailActivity.this,pro);
                        }else{
                            addCart.setEnabled(false);
                            buy.setEnabled(false);
                            savePro.setEnabled(false);
                        }
                    }
                });
            }
        };
        thread.start();

    }


    private void initView() {
        HotTab tab_one = new HotTab("商品",new WareDetailInfo());
        HotTab tab_two = new HotTab("详情",new WareDetailPicture());
        HotTab tab_three = new HotTab("评价",new WareDetailComment());
        tabList.add(tab_one);
        tabList.add(tab_two);
        tabList.add(tab_three);
        adapter = new WareDetailAdapter(getSupportFragmentManager(),tabList);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public void detailShow(){
        detail.show();
    }
    public void SelectAttrShow(){
        productSelectAttr.show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buy:
                if(productSelectAttr!=null) {
                    this.SelectAttrShow();
                    productSelectAttr.setAddCartHide();
                    productSelectAttr.setOnGetOrderListener(new ProductSelectAttr.onGetOrderListener() {
                        @Override
                        public void get(FEMOrder order) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("femorder",order);
                            Intent intent = new Intent(ProductDetailActivity.this, OrderActivity.class);
                            intent.putExtra("fem",bundle);
                            startActivity(intent);
                        }
                    });
                }
                break;
            case R.id.addcart:
                if(productSelectAttr!=null) {
                    this.SelectAttrShow();
                    productSelectAttr.setBuyHide();
                }
                break;
        }
    }




}
