package com.example.administrator.igoushop_app_test.activity.message_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.administrator.igoushop_app_test.Myapplication;
import com.example.administrator.igoushop_app_test.R;
import com.example.administrator.igoushop_app_test.activity.ProductDetailActivity;
import com.example.administrator.igoushop_app_test.adapter.BaseAdapter;
import com.example.administrator.igoushop_app_test.adapter.OrderListAdapter;
import com.example.administrator.igoushop_app_test.http.OKManager;
import com.example.administrator.igoushop_app_test.pojos.Order;
import com.example.administrator.igoushop_app_test.tools.JacksonUtils;
import com.example.administrator.igoushop_app_test.widget.Contants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/4/22.
 */

public class OrderListActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private OrderListAdapter adapter;
    private LinearLayout noDate;
    private OKManager manager = OKManager.getInstance();
    private MaterialRefreshLayout refreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderlist_layout);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
        requestDate();
    }

    private void requestDate() {
        Map<String,String> map = new HashMap<String,String>();
        map.put("userId", Myapplication.getUserInfo().getId()+"");
        manager.sendComplexForm(Contants.BASE_URL+"Order_getOrderListById.action", map, new OKManager.Func4() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String o = jsonObject.get("orders").toString();
                    if("[]".equals(o)){
                        noDate.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }else {
                        List<Order> orders = JacksonUtils.toList(jsonObject.get("orders").toString(),List.class,Order.class);
                        if(orders == null && orders.size()<=0){
                            noDate.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }else {
                            noDate.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            initDate(orders);
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void initDate(List<Order> orders) {
        adapter = new OrderListAdapter(OrderListActivity.this,R.layout.rderlist_item,orders);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Integer product_id = adapter.getItem(position).getProId();
                Intent intent = new Intent(OrderListActivity.this, ProductDetailActivity.class);
                intent.putExtra(Contants.Product,""+product_id);
                startActivity(intent);
            }
        });



        adapter.setOnDeleteItemListener(new OrderListAdapter.onDeleteItemListener() {
            @Override
            public void delete(final int orderId) {
                WindowManager windowManager = OrderListActivity.this.getWindowManager();
                Display display = windowManager.getDefaultDisplay();
                int width = display.getWidth();
                int height = display.getHeight();
                final AlertDialog myDialog = new AlertDialog.Builder(OrderListActivity.this).create();
                myDialog.show();
                myDialog.getWindow().setLayout((int) (width*0.75), (int) (height/2*0.75*0.75));
                myDialog.getWindow().setContentView(R.layout.alert_dialog_layout);
                myDialog.getWindow()
                        .findViewById(R.id.alert_btn)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Map<String,String> params = new HashMap<String, String>();
                                params.put("orderId",orderId+"");
                                manager.sendComplexForm(Contants.BASE_URL+"Order_deleteByOrderId.action", params, new OKManager.Func4() {
                                    @Override
                                    public void onResponse(JSONObject jsonObject) {
                                        try{
                                            int flag = Integer.parseInt(jsonObject.get("success").toString());
                                            if (flag == 0){
                                                String msg = (String) jsonObject.get("msg");
                                                Toast.makeText(OrderListActivity.this,msg,Toast.LENGTH_SHORT).show();
                                            }else {
                                                requestDate();
                                                myDialog.dismiss();
                                            }
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }

                                    }
                                });
                            }
                        });
                myDialog.getWindow().findViewById(R.id.alert_cancel)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myDialog.dismiss();
                            }
                        });
            }
        });
        adapter.setOnBuyAgainListener(new OrderListAdapter.onBuyAgainListener() {
            @Override
            public void click(int id) {
                Intent intent = new Intent(OrderListActivity.this, ProductDetailActivity.class);
                intent.putExtra(Contants.Product,""+id);
                startActivity(intent);
            }
        });
        adapter.setOnRemoveistener(new OrderListAdapter.onRemoveOrderListener() {
            @Override
            public void remove(final int orderId, final int status) {
                final KProgressHUD hud =KProgressHUD.create(OrderListActivity.this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel("Please wait")
                        .setCancellable(true)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f)
                        .show();
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    public void run() {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("orderId",orderId+"");
                        params.put("status",status+"");
                        manager.sendComplexForm(Contants.BASE_URL+"Order_updateStstusByOrderId.action", params, new OKManager.Func4() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                try{
                                    int flag = Integer.parseInt(jsonObject.get("success").toString());
                                    if (flag == 0){
                                        String msg = (String) jsonObject.get("msg");
                                        Toast.makeText(OrderListActivity.this,msg,Toast.LENGTH_SHORT).show();
                                    }else {
                                        Thread.sleep(2000);
                                        hud.dismiss();
                                        requestDate();
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }, 2000);
            }
        });
    }



    private void initView() {
        Fresco.initialize(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar_1);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.orderlist);
        noDate = (LinearLayout) findViewById(R.id.nodate_value);
        refreshLayout = (MaterialRefreshLayout) findViewById(R.id.refersh);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                requestDate();
                refreshLayout.finishRefresh();
            }
        });
    }
}
