package com.example.administrator.igoushop_app.activity.message_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.igoushop_app.R;

/**
 * Created by Administrator on 2017/4/11.
 */

public class SuccessActivity extends AppCompatActivity {
    private TextView button ;
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_activity);
        button = (TextView) findViewById(R.id.button);
        toolbar = (Toolbar) findViewById(R.id.success_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(SuccessActivity.this, MainActivity.class);
//                startActivity(intent);
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccessActivity.this, OrderListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
