package com.example.administrator.igoushop_app_test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.igoushop_app_test.R;
import com.example.administrator.igoushop_app_test.http.OKManager;
import com.example.administrator.igoushop_app_test.widget.Contants;
import com.example.administrator.igoushop_app_test.widget.CustomToolBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/28.
 */

public class RegistActvity extends AppCompatActivity {
    private EditText userName,userPass,phone;
    private CustomToolBar toolBar;
    private TextView regist;
    private OKManager manager = OKManager.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        toolBar = (CustomToolBar) findViewById(R.id.registtoolbar);
        setSupportActionBar(toolBar);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userName = (EditText) findViewById(R.id.tv_username);
        userPass = (EditText) findViewById(R.id.tv_userpass);
        phone = (EditText) findViewById(R.id.tv_phone);
        regist = (TextView) findViewById(R.id.tv_regist);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> params = new HashMap<String, String>();
                if (userName.getText().toString().equals("")){
                    Toast.makeText(RegistActvity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                }else if(userPass.getText().toString().equals("")){
                    Toast.makeText(RegistActvity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                }else if(phone.getText().toString().equals("")){
                    Toast.makeText(RegistActvity.this,"电话不能为空",Toast.LENGTH_SHORT).show();
                }else
                params.put("userName",userName.getText().toString());
                params.put("userPass",userPass.getText().toString());
                params.put("phone",phone.getText().toString());
                manager.sendComplexForm(Contants.BASE_URL+"UserInfo_regist.action", params, new OKManager.Func4() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            Integer success = (Integer) jsonObject.get("success");
                            String msg = (String) jsonObject.get("msg");
                            if (success == 1){
                                Toast.makeText(RegistActvity.this,msg,Toast.LENGTH_SHORT).show();
                                WindowManager windowManager = getWindowManager();
                                Display display = windowManager.getDefaultDisplay();
                                int width = display.getWidth();
                                int height = display.getHeight();
                                final AlertDialog myDialog = new AlertDialog.Builder(RegistActvity.this).create();
                                myDialog.show();
                                myDialog.getWindow().setLayout((int) (width*0.75), (int) (height/2*0.75*0.75));
                                myDialog.getWindow().setContentView(R.layout.login_alert_dialog_layout);
                                myDialog.getWindow()
                                        .findViewById(R.id.alert_btn)
                                        .setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                startActivity(new Intent(RegistActvity.this, LoginActivity.class));
                                                myDialog.dismiss();
                                                finish();
                                            }
                                        });
                                myDialog.getWindow().findViewById(R.id.alert_cancel)
                                        .setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                myDialog.dismiss();
                                                finish();
                                            }
                                        });
                            }else {
                                Toast.makeText(RegistActvity.this,msg,Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
