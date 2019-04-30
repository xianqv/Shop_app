package com.example.administrator.igoushop_app.activity.message_activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.igoushop_app.Myapplication;
import com.example.administrator.igoushop_app.R;
import com.example.administrator.igoushop_app.pojos.UserInfo;
import com.example.administrator.igoushop_app.widget.ChioesView;
import com.example.administrator.igoushop_app.widget.DBHelperUtils;


/**
 * Created by Administrator on 2016-06-15.
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    private ChioesView Message_setting;
    private ChioesView userinfo;
    private Button Logout;
    private boolean isLogin = false;
    private UserInfo user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_setting);
        Message_setting = (ChioesView) findViewById(R.id.message_setting);
        userinfo = (ChioesView) findViewById(R.id.userinfo);
        userinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this,UserInfoActivity.class));
            }
        });
        Logout = (Button) findViewById(R.id.logout);
        Message_setting.setOnClickListener(this);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new AlertDialog.Builder(SettingActivity.this).create();
                dialog.show();
                RelativeLayout dialog_bg = (RelativeLayout) LayoutInflater.from(SettingActivity.this).inflate(R.layout.logout_dialog,null);
                dialog.getWindow().setContentView(dialog_bg);
                Button ok = (Button) dialog_bg.findViewById(R.id.ok);
                Button cancel = (Button) dialog_bg.findViewById(R.id.cancal);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                    DBHelperUtils utils = new DBHelperUtils(SettingActivity.this);
                                    utils.open();
                                    boolean flag = utils.Logout(user.getId());
                                    utils.close();
                                    if(flag){
                                    Myapplication.setOnline(false);
                                    Myapplication.setUserInfo(null);
                                    Logout.setVisibility(View.GONE);
                                    Toast.makeText(SettingActivity.this,"注销成功",Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    }
                                    }
                                    });
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

            }
        });

        initView();
    }

    private void initView() {
        isLogin = Myapplication.isOnline();
        if(isLogin){
            this.user = Myapplication.getUserInfo();
            Logout.setVisibility(View.VISIBLE);
        }else {
            Logout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.message_setting:
                Intent intent = new Intent(SettingActivity.this,NotifyActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }
}

