package com.example.administrator.igoushop_app.activity.message_activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.igoushop_app.Myapplication;
import com.example.administrator.igoushop_app.R;
import com.example.administrator.igoushop_app.http.OKManager;
import com.example.administrator.igoushop_app.pojos.Address;
import com.example.administrator.igoushop_app.widget.Contants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-31.
 */
public class ModifyActivity extends AppCompatActivity {
    private EditText name;
    private EditText phone;
    private EditText postalcode;
    private EditText address;
    private TextView save;
    private TextView update;
    private Toolbar toolBar;
    private OKManager manager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_modify);
        name = (EditText) findViewById(R.id.tv_tname_value);
        phone = (EditText) findViewById(R.id.tv_tphone_value);
        postalcode = (EditText) findViewById(R.id.tv_postalcode_value);
        address = (EditText) findViewById(R.id.tv_detail_value);
        save = (TextView) findViewById(R.id.save);
        update = (TextView) findViewById(R.id.update);
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        int flag = intent.getFlags();
        if(flag == 1){
            save.setVisibility(View.GONE);
            update.setVisibility(View.VISIBLE);
            final Address  userAddress = (Address) intent.getSerializableExtra("address");
            name.setText(userAddress.getName());
            phone.setText(userAddress.getPhone());
            postalcode.setText(userAddress.getPostalcode());
            address.setText(userAddress.getDetail());
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    manager = OKManager.getInstance();
                    final Map<String, String> map = new HashMap<String, String>();
                    map.put("id", userAddress.getId().toString());
                    map.put("name", name.getText().toString());
                    map.put("phone", phone.getText().toString());
                    map.put("postalcode", postalcode.getText().toString());
                    map.put("address", address.getText().toString());
                    map.put("isDef",userAddress.getIsDefault()+"");
                    map.put("userinfoId",Myapplication.getUserInfo().getId().toString());
                    manager.sendComplexForm(Contants.BASE_URL+"Address_UpdateAddress.action", map, new OKManager.Func4() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                String success = jsonObject.get("success").toString();
                                if(success.equals("true")){
                                    Toast.makeText(ModifyActivity.this,"修改成功!!",Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    Toast.makeText(ModifyActivity.this,"修改失败!!",Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
            });
        }else if(flag == 2){
            save.setVisibility(View.VISIBLE);
            update.setVisibility(View.GONE);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nameValue = name.getText().toString();
                    String phoneValue = phone.getText().toString();
                    String postalcodeValue = postalcode.getText().toString();
                    String addressValue = address.getText().toString();
                    if(!nameValue.equals("")||!phoneValue.equals("")|| !postalcodeValue.equals("")|| !addressValue.equals("")){
                        manager = OKManager.getInstance();
                        final Map<String, String> map = new HashMap<String, String>();
                        map.put("userId", Myapplication.getUserInfo().getId().toString());
                        map.put("name", nameValue);
                        map.put("phone", phoneValue);
                        map.put("postalcode",postalcodeValue);
                        map.put("address",addressValue);
                        manager.sendComplexForm(Contants.BASE_URL+"Address_AddAddress.action", map, new OKManager.Func4() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                try {
                                    String success = jsonObject.get("success").toString();
                                    if(success.equals("true")){
                                        Toast.makeText(ModifyActivity.this,"添加成功!!",Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else {
                                        Toast.makeText(ModifyActivity.this,"添加失败!!",Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }else {
                        Toast.makeText(ModifyActivity.this,"请填写完整信息!!",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }

    /**
     * 判断网络是否连接
     * @param context
     * @return
     */
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
