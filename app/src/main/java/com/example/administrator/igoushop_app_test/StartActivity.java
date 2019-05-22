package com.example.administrator.igoushop_app_test;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import com.example.administrator.igoushop_app_test.pojos.UserInfo;
import com.example.administrator.igoushop_app_test.widget.DBHelperUtils;

/**
 * Created by Administrator on 2016-09-03.
 */
public class StartActivity extends Activity {
    private ImageView imageView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_start);
        imageView = (ImageView) findViewById(R.id.image_start);
        imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.start_image));
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        load();
    }

    private void load() {
        SharedPreferences sharedPreferences = getSharedPreferences("isLogin",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        DBHelperUtils utils = new DBHelperUtils(this);
        utils.open();
        UserInfo userInfo = utils.toUserInfo(utils.fetchAllUserInfo());
        if(userInfo == null){
            editor.putBoolean("isLogin",false);
            Log.d("----没有创建-----","");
        }else {
            editor.putBoolean("isLogin",true);
            Intent intent = new Intent();
            intent.setClass(StartActivity.this,MainActivity.class);
            startActivity(intent);
            utils.close();
            finish();
            Log.d("----有创建 修改-----","");
        }

    }
}
