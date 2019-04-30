package com.example.administrator.igoushop_app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.test.ApplicationTestCase;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.igoushop_app.http.OKManager;
import com.example.administrator.igoushop_app.pojos.UserInfo;
import com.example.administrator.igoushop_app.widget.Contants;
import com.example.administrator.igoushop_app.widget.DBHelperUtils;

import org.json.JSONObject;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }


    public void testisOnline(){
        boolean isonline = Myapplication.isOnline();
    }

}