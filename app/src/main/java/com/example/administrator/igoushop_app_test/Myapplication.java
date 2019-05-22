package com.example.administrator.igoushop_app_test;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;

import com.example.administrator.igoushop_app_test.pojos.UserInfo;
import com.example.administrator.igoushop_app_test.widget.DBHelperUtils;

/**
 * Created by Administrator on 2016-08-28.
 */
public class Myapplication extends Application {
    public static Context context;
    public static boolean online;
    public static UserInfo userInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        DBHelperUtils utils = new DBHelperUtils(getContext());
        utils.open();
        Cursor cursor = utils.fetchAllUserInfo();
        UserInfo user = utils.toUserInfo(cursor);
        utils.close();
        if(user.getId()!=null){
            setOnline(true);
            setUserInfo(user);
        }else {
            setOnline(false);
            setUserInfo(null);
        }

    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        Myapplication.context = context;
    }

    public static boolean isOnline() {
        return online;
    }

    public static void setOnline(boolean online) {
        Myapplication.online = online;
    }

    public static UserInfo getUserInfo() {
        return userInfo;
    }

    public static void setUserInfo(UserInfo userInfo) {
        Myapplication.userInfo = userInfo;
    }
}
