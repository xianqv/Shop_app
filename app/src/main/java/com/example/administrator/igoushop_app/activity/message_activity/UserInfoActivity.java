package com.example.administrator.igoushop_app.activity.message_activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.igoushop_app.Myapplication;
import com.example.administrator.igoushop_app.R;
import com.example.administrator.igoushop_app.pojos.UserInfo;

/**
 * Created by Administrator on 2017/5/23.
 */

public class UserInfoActivity extends AppCompatActivity {
    private TextView userName,phone;
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDate();
    }

    private void initDate() {
        UserInfo userInfo = Myapplication.getUserInfo();
        if(userInfo!=null){
            initView(userInfo);
        }
    }

    private void initView(UserInfo userInfo) {
        toolbar = (Toolbar) findViewById(R.id.userinfo_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userName = (TextView) findViewById(R.id.tv_zy_value);
        phone = (TextView) findViewById(R.id.tv_phone_value);
        userName.setText(userInfo.getName());
        phone.setText(userInfo.getPhone());
    }
}
