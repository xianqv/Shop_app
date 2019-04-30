package com.example.administrator.igoushop_app.tools;

import com.example.administrator.igoushop_app.pojos.Address;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-08-23.
 */
public class JSONUtils  {


    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();



    public static  Gson getGson(){
        return  gson;
    }



    public static <T> T fromJson(String json,Class<T> clz){

        return  gson.fromJson(json,clz);
    }


    public static <T> T fromJson(String json,Type type){

        return  gson.fromJson(json,type);
    }


    public static String toJSON(Object object){

        return gson.toJson(object);
    }

    public static List<Address> fromObjectByAddress(JSONObject jsonObject){
        List<Address> list = new ArrayList<>();
        Address address ;
        try {
            int num = jsonObject.length();
            JSONArray nameList = jsonObject.getJSONArray("address");//获取JSONArray
            int length = nameList.length();
            for(int i = 0; i < length; i++){//遍历JSONArray
                address = new Address();
                JSONObject oj = nameList.getJSONObject(i);
                address.setId(oj.getInt("id"));
                address.setName(oj.getString("name"));
                address.setPhone(oj.getString("phone"));
                address.setDetail(oj.getString("detail"));
                address.setPostalcode(oj.getString("postalcode"));
                address.setIsDefault(oj.getInt("isDefault"));
                list.add(address);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

}
