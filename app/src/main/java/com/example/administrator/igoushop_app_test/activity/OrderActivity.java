package com.example.administrator.igoushop_app_test.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.igoushop_app_test.Myapplication;
import com.example.administrator.igoushop_app_test.R;
import com.example.administrator.igoushop_app_test.activity.message_activity.AddrActivity;
import com.example.administrator.igoushop_app_test.activity.message_activity.SuccessActivity;
import com.example.administrator.igoushop_app_test.http.OKManager;
import com.example.administrator.igoushop_app_test.pojos.Address;
import com.example.administrator.igoushop_app_test.pojos.FEMOrder;
import com.example.administrator.igoushop_app_test.widget.CartNumberView;
import com.example.administrator.igoushop_app_test.widget.Contants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/4/8.
 */

public class OrderActivity extends AppCompatActivity {
//    private DynamicBox box;
    private LinearLayout address_item;
    private LinearLayout noAddress;
    private TextView consigneeName;
    private TextView consigneePhone;
    private TextView consigneeAddress;
    private TextView productName;
    private TextView productColor;
    private TextView productSize;
    private TextView productOnePrice;
    private TextView productOneNum;

    private CartNumberView numberView;
    private TextView leave_message;
    private TextView totalPrice;
    private TextView saveOrderinfo;
    private OKManager manager = OKManager.getInstance();
    private Toolbar toolbar;
    private FEMOrder femOrder ;
    private SimpleDraweeView simpleDraweeView;
    private Address address;
    private boolean flag = false;
    private FEMOrder product;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity);
    }

    @Override
    protected void onResume() {

            super.onResume();
            initView();
            requestDate();

    }

    private void initView(){
        Fresco.initialize(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        noAddress = (LinearLayout) findViewById(R.id.no_address);
        address_item = (LinearLayout) findViewById(R.id.item_msg);
        consigneeName= (TextView) findViewById(R.id.getname);
        consigneePhone= (TextView) findViewById(R.id.phone);
        consigneeAddress= (TextView) findViewById(R.id.tv_address);
        productName= (TextView) findViewById(R.id.tv_pro_name);
        productColor= (TextView) findViewById(R.id.color);
        productSize= (TextView) findViewById(R.id.size);
        productOnePrice= (TextView) findViewById(R.id.oneprice);
        productOneNum= (TextView) findViewById(R.id.num);
        numberView= (CartNumberView) findViewById(R.id.pronum);
        leave_message= (TextView) findViewById(R.id.leave_message);
        totalPrice= (TextView) findViewById(R.id.totalPrice);
        saveOrderinfo= (TextView) findViewById(R.id.saveOrderinfo);
//        box = new DynamicBox(this,R.layout.order_activity);
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.img);
//        box.setLoadingMessage("正在加载！！");
        numberView.setOnButtonClickListener(new CartNumberView.OnButtonClickListener() {
            @Override
            public void onButtonAddClick(View view, int value) {
                changeTotalPrice(value);
            }

            @Override
            public void onButtonSubClick(View view, int value) {
                changeTotalPrice(value);
            }
        });
        noAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, AddrActivity.class);
                startActivity(intent);
            }
        });
        address_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, AddrActivity.class);
                startActivity(intent);
            }
        });
        try {

            saveOrderinfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (noAddress.getVisibility() == View.VISIBLE) {
                        Toast.makeText(OrderActivity.this, "请选择收货地址", Toast.LENGTH_SHORT).show();
                    } else {
                        LemonBubble.showRoundProgress(OrderActivity.this,"正在生成订单..");
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            public void run() {
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("userId", Myapplication.getUserInfo().getId() + "");
                                map.put("userName", Myapplication.getUserInfo().getName());
                                map.put("phone", consigneePhone.getText().toString());
                                map.put("address", consigneeAddress.getText().toString());
                                map.put("proName", productName.getText().toString());
                                map.put("proSize", productSize.getText().toString());
                                map.put("proColor", productColor.getText().toString());
                                map.put("onePrice", productOnePrice.getText().toString());
                                map.put("proNum", productOneNum.getText().toString());
                                map.put("totalPrice", totalPrice.getText().toString());
                                map.put("imgURL", product.getImgUrl());
                                map.put("proId", product.getProId() + "");
                                String msg = leave_message.getText().toString();
                                if (msg.equals("")) {
                                    map.put("msg", "无");
                                } else {
                                    map.put("msg", msg);
                                }
                                manager.sendComplexForm(Contants.BASE_URL + "Order_saveOrderInfo.action", map, new OKManager.Func4() {
                                    @Override
                                    public void onResponse(JSONObject jsonObject) {
                                        try {
                                            String success = (String) jsonObject.get("success");
                                            String msg = (String) jsonObject.get("msg");
                                            if ("true".equals(success)) {
                                                LemonBubble.hide();
                                                Intent intent = new Intent(OrderActivity.this, SuccessActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(OrderActivity.this, msg, Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        }, 2000);
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void changeTotalPrice(int num) {
        productOneNum.setText("x"+num);
        totalPrice.setText(((int) (product.getOnePrice()*num))+"");
    }

    private void requestDate() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("fem");
        FEMOrder  femOrder = (FEMOrder) bundle.getSerializable("femorder");
        if(femOrder!=null){
            this.femOrder = femOrder;
            initProduct(femOrder);
        }
//        box.showLoadingLayout();
        //查找当前登录用户的默认收货地址
        Map<String,String> map = new HashMap<String,String>();
        map.put("userId", Myapplication.getUserInfo().getId()+"");
        manager.sendComplexForm(Contants.BASE_URL+"Address_findAddressByUserId.action", map, new OKManager.Func4() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Gson gson = new Gson();
                try {
                    String success = (String) jsonObject.get("success");
                    if("true".equals(success)) {
                        noAddress.setVisibility(View.GONE);
                        address_item.setVisibility(View.VISIBLE);
                        Address address = gson.fromJson(jsonObject.get("address").toString(), Address.class);
                        initAddress(address);
                    }else {
                        address_item.setVisibility(View.GONE);
                        noAddress.setVisibility(View.VISIBLE);
//                        box.hideAll();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initAddress(Address address) {
//        box.hideAll();
        this.address = address;
        consigneeName.setText(address.getName());
        consigneePhone.setText(address.getPhone());
        consigneeAddress.setText(address.getPostalcode());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("product",this.femOrder);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        this.femOrder = (FEMOrder) savedInstanceState.getSerializable("product");
        initProduct(this.femOrder);
        super.onRestoreInstanceState(savedInstanceState);

    }

    private void initProduct(FEMOrder product) {
        this.product = product;
        simpleDraweeView.setImageURI(Uri.parse(Contants.Img_URL+product.getImgUrl()));
        productName.setText(product.getName());
        productColor.setText("颜色:   "+product.getColor());
        productSize.setText("尺寸:   "+product.getSize());
        productOneNum.setText("x  "+product.getNum());
        productOnePrice.setText(product.getOnePrice()+"");
        totalPrice.setText((product.getNum()*product.getOnePrice())+"");
        numberView.setValue(product.getNum());

    }


}
