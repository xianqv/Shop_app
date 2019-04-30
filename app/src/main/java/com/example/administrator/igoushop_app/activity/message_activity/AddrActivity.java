package com.example.administrator.igoushop_app.activity.message_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.administrator.igoushop_app.Myapplication;
import com.example.administrator.igoushop_app.R;
import com.example.administrator.igoushop_app.adapter.AddressAdapter;
import com.example.administrator.igoushop_app.http.OKManager;
import com.example.administrator.igoushop_app.pojos.Address;
import com.example.administrator.igoushop_app.pojos.UserInfo;
import com.example.administrator.igoushop_app.tools.JSONUtils;
import com.example.administrator.igoushop_app.widget.Contants;
import com.example.administrator.igoushop_app.widget.CustomToolBar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-09-07.
 */
public class AddrActivity extends AppCompatActivity {
    private CustomToolBar toolBar;
    private RecyclerView address_list;
    private TextView add_address;
    private AddressAdapter adapter;
    private OKManager manager ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_address);
        toolBar = (CustomToolBar) findViewById(R.id.address_manager);
        setSupportActionBar(toolBar);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        address_list = (RecyclerView) findViewById(R.id.address_list);
        add_address = (TextView) findViewById(R.id.add_address);
        add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddrActivity.this,ModifyActivity.class);
                intent.setFlags(2);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadUrlData();
    }

    private void LoadUrlData() {
        manager = OKManager.getInstance();
        Thread thread = new Thread(){
            @Override
            public void run() {
                UserInfo userInfo = Myapplication.getUserInfo();
                Map map = new HashMap();
                map.put("user_id",""+userInfo.getId());
                manager.sendComplexForm(Contants.BASE_URL + "Address_findListById.action", map, new OKManager.Func4() {
                    @Override
                    public  void onResponse(JSONObject jsonObject) {
                        List<Address> addresses = JSONUtils.fromObjectByAddress(jsonObject);
                        initView(addresses);
                    }
                });
            }
        };
        thread.start();
    }

    private void initView(List<Address> addresses) {
        adapter = new AddressAdapter(this,R.layout.layout_address_item,addresses);
        address_list.setAdapter(adapter);
        address_list.setLayoutManager(new LinearLayoutManager(this));

        adapter.setonRadioButtonClickListener(new AddressAdapter.onRadioButtonClickListener() {
            @Override
            public void onClick(int id,int userId) {
                manager = OKManager.getInstance();
                Map<String,String> map = new HashMap<String,String>();
                map.put("address_id",id+"");
                map.put("user_id",userId+"");
                manager.sendComplexForm(Contants.BASE_URL+"Address_changeDefaultAddress.action", map, new OKManager.Func4() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        List<Address> addresses = JSONUtils.fromObjectByAddress(jsonObject);
                        adapter.setDate(addresses);
                    }
                });
            }
        });
        adapter.setOnBUttonListener(new AddressAdapter.onButtonClickListener() {
            @Override
            public void onClick(final int id, final int userId, int flag) {
                if(flag == 1){
                    WindowManager windowManager = getWindowManager();
                    Display display = windowManager.getDefaultDisplay();
                    int width = display.getWidth();
                    int height = display.getHeight();
                    final AlertDialog myDialog = new AlertDialog.Builder(AddrActivity.this).create();
                    myDialog.show();
                    myDialog.getWindow().setLayout((int) (width*0.75), (int) (height/2*0.75*0.75));
                    myDialog.getWindow().setContentView(R.layout.alert_dialog_layout);
                    myDialog.getWindow()
                            .findViewById(R.id.alert_btn)
                            .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    manager = OKManager.getInstance();
                                    Map<String,String> map = new HashMap<String,String>();
                                    map.put("address_id",id+"");
                                    map.put("user_id",userId+"");
                                    manager.sendComplexForm(Contants.BASE_URL+"Address_DeleteAddressById.action", map, new OKManager.Func4() {
                                        @Override
                                        public void onResponse(JSONObject jsonObject) {
                                            List<Address> addresses = JSONUtils.fromObjectByAddress(jsonObject);
                                            adapter.setDate(addresses);
                                            myDialog.dismiss();
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
                if(flag == 2){
                    Intent intent = new Intent(AddrActivity.this, ModifyActivity.class);
                    Address address = adapter.getDateById(id);
                    if(address!=null) {
                        intent.setFlags(1);
                        intent.putExtra("address", address);
                        startActivity(intent);
                    }
                }
            }
        });


    }
    public List<Address> toList(UserInfo userInfo){
        List<Address> addresses = new ArrayList<>();
        if(userInfo.getAddress()!=null) {
            for (Address address : userInfo.getAddress()) {
                addresses.add(address);
            }
        }
        return addresses;
    }

}
