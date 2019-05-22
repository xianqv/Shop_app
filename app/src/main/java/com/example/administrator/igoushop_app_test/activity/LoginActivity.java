package com.example.administrator.igoushop_app_test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.igoushop_app_test.Myapplication;
import com.example.administrator.igoushop_app_test.R;
import com.example.administrator.igoushop_app_test.http.OKManager;
import com.example.administrator.igoushop_app_test.pojos.UserInfo;
import com.example.administrator.igoushop_app_test.tools.JSONUtils;
import com.example.administrator.igoushop_app_test.widget.Contants;
import com.example.administrator.igoushop_app_test.widget.CustomToolBar;
import com.example.administrator.igoushop_app_test.widget.DBHelperUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016-08-28.
 */
public class LoginActivity extends AppCompatActivity {
    private CustomToolBar logintoolbar;
    private EditText username;
    private EditText userpass;
    private TextView login;
    private  TextView regist;
    private CustomToolBar toolBar;
    private OKManager manager;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 200){
                saveUser((UserInfo) msg.obj);
            }
        }
    } ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        toolBar = (CustomToolBar) findViewById(R.id.logintoolbar);
        setSupportActionBar(toolBar);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        logintoolbar = (CustomToolBar) findViewById(R.id.logintoolbar);
        username = (EditText) findViewById(R.id.username);
        userpass = (EditText) findViewById(R.id.userpass);
        login = (TextView) findViewById(R.id.login);
        regist  = (TextView) findViewById(R.id.regist);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegistActvity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = username.getText().toString();
                final String pass = userpass.getText().toString();
                if("".equals(name)||"".equals(pass)){
                    Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    manager = OKManager.getInstance();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Map<String, String> map = new HashMap<>();
                            map.put("username", name);
                            map.put("userpass", pass);
                            manager.sendComplexForm(Contants.BASE_URL+"UserInfo_login.action", map, new OKManager.Func4() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    if (jsonObject.toString().equals("{}")) {
                                        Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                                    } else {
                                        UserInfo user = JSONUtils.fromJson(jsonObject.toString(), UserInfo.class);
                                        Message message = new Message();
                                        message.what = 200;
                                        message.obj = user;
                                        handler.sendMessage(message);
                                    }
                                }
                            });

                        }
                    }).start();
                }
            }
        });
    }

    private void saveUser(UserInfo user) {
        DBHelperUtils dbHelperUtils = new DBHelperUtils(this);
        dbHelperUtils.open();
        long rowId = dbHelperUtils.Login(user);
        if(rowId!=-1) {
            Myapplication.setOnline(true);
            Myapplication.setUserInfo(user);
            dbHelperUtils.close();
            finish();
        }
    }
}