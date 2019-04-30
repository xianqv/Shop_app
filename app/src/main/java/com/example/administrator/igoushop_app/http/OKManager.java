package com.example.administrator.igoushop_app.http;



        import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by luoliwen on 16/5/13.
 * 封装工具类
 */
public class OKManager {

    private OkHttpClient client;
    private volatile static OKManager manager;
    private final String TAG = OKManager.class.getSimpleName();//获得类名
    private Handler handler;
    //提交json数据
    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    //提交字符串
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");

    private OKManager() {
        client = new OkHttpClient();
        handler = new Handler(Looper.getMainLooper());

    }

    public static OKManager getInstance() {
        OKManager instance = manager;
        if (manager == null) {
            synchronized (OKManager.class) {
                if (instance == null) {
                    instance = new OKManager();
                    manager = instance;
                }
            }
        }
        return instance;
    }


    /**
     * 同步请求，在android开发中不常用，因为会阻塞UI线程
     *
     * @param url
     * @return
     */
    public String syncGetByURL(String url) {
        //构建一个request请求
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();//同步请求数据
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 请求指定的url返回的结果是json字符串
     *
     * @param url
     * @param callBack
     */
    public void asyncJsonStringByURL(String url, final Func1 callBack) {
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessJsonStringMethod(response.body().string(), callBack);
                }
            }
        });
    }




    /**
     * 请求返回的是json对象
     *
     * @param url
     * @param callBack
     */
    public void asyncJsonObjectByURL(String url, final Func4 callBack) {
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessJsonObjectMethod(response.body().string(), callBack);
                }
            }
        });
    }

    /**
     * 请求返回的是byte字节数组
     *
     * @param url
     * @param callBack
     */
    public void asyncGetByteByURL(String url, final Func2 callBack) {
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessByteMethod(response.body().bytes(), callBack);
                }
            }
        });
    }
//
//    /**
//     * 请求返回结果是imageView类型 bitmap 类型
//     *
//     * @param url
//     * @param callBack
//     */
//    public void asyncDownLoadImageByURL(String url, final Func3 callBack) {
//        Request request = new Request.Builder().url(url).build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response != null && response.isSuccessful()) {
//                    byte[] data = response.body().bytes();
//                    Bitmap bitmap = new CropSquareTrans().transform(BitmapFactory.decodeByteArray(data, 0, data.length));
//                    callBack.onResponse(bitmap);
//                }
//            }
//        });
//    }

    /**
     *  模拟表单提交
     * @param url
     * @param params
     * @param callBack
     */
    public void sendComplexForm(String url, Map<String, String> params, final Func4 callBack) {
        FormBody.Builder form_builder = new FormBody.Builder();//表单对象，包含以input开始的对象，以html表单为主
        if (params != null && !params.isEmpty()) {
            for(Map.Entry<String,String> entry : params.entrySet()){
                form_builder.add(entry.getKey(),entry.getValue());
            }
        }
        RequestBody request_body = form_builder.build();
        Request request = new Request.Builder().url(url).post(request_body).build();//采用post方式提交
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response!=null&&response.isSuccessful()){
                    String string = response.body().string();
                    Log.e(TAG, "onResponse: "+string);
                    onSuccessJsonObjectMethod(string, callBack);
                }else{
                    Log.e(TAG, "请求失败" );
                }
            }
        });
    }

    /**
     * 向服务器提交String请求
     * @param url
     * @param content
     * @param callBack
     */
    public void sendStringByPostMethod(String url,String content,final Func4 callBack){
        Request request = new Request.Builder().url(url).post(RequestBody.create(MEDIA_TYPE_MARKDOWN,content)).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response!=null&&response.isSuccessful()){
                    onSuccessJsonObjectMethod(response.body().string(),callBack);
                }
            }
        });
    }
    /**
     * 请求返回的结果是json字符串
     *
     * @param jsonValue
     * @param callBack
     */
    private void onSuccessJsonStringMethod(final String jsonValue, final Func1 callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    try {
                        callBack.onResponse(jsonValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    /**
     * 请求返回的是byte[] 数组
     *
     * @param data
     * @param callBack
     */
    private void onSuccessByteMethod(final byte[] data, final Func2 callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onResponse(data);
                }
            }
        });
    }

    /**
     * 返回响应的结果是json对象
     *
     * @param jsonValue
     * @param callBack
     */
    private void onSuccessJsonObjectMethod(final String jsonValue, final Func4 callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    try {
                        callBack.onResponse(new JSONObject(jsonValue));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public interface Func1 {
        void onResponse(String result) throws IOException;
    }

    public interface Func2 {
        void onResponse(byte[] result);
    }

    public interface Func3 {
        void onResponse(Bitmap bitmap);
    }

    public interface Func4 {
        void onResponse(JSONObject jsonObject);
    }
}
