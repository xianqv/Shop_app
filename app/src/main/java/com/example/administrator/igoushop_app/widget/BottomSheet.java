package com.example.administrator.igoushop_app.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.administrator.igoushop_app.R;

/**
 * Created by Administrator on 2016-08-18.
 */
public  abstract class BottomSheet extends Dialog implements DialogInterface {
    public Context  context;

    public BottomSheet(Context context) {
        super(context, R.style.BottomSheet_Dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        // 必须在init()之后设置
        //获取手机屏幕的高度
        int screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        //获取系统属性 n 管理系统属性
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = (int) (screenHeight * heightPercent());
        params.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(params);
    }

    /**
     * 弹出框高度占屏幕百分比，取值范围(0,1]，1表示100%，0.5表示50%，其余类推
     *
     * @return 默认为0.5f
     */
    public float heightPercent() {
        return 0.7f;
    }

    public abstract int getContentLayoutId();
    //回调函数
    public abstract void initView(View root);

    private void init() {
        View root = View.inflate(context, getContentLayoutId(), null);
        setContentView(root);
        initView(root);
    }
}
