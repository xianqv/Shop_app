package com.example.administrator.igoushop_app_test.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.igoushop_app_test.Myapplication;
import com.example.administrator.igoushop_app_test.R;
import com.example.administrator.igoushop_app_test.activity.LoginActivity;
import com.example.administrator.igoushop_app_test.activity.message_activity.AddrActivity;
import com.example.administrator.igoushop_app_test.activity.message_activity.OrderListActivity;
import com.example.administrator.igoushop_app_test.activity.message_activity.SettingActivity;
import com.example.administrator.igoushop_app_test.http.OKManager;
import com.example.administrator.igoushop_app_test.pojos.UserInfo;
import com.example.administrator.igoushop_app_test.widget.ChioesView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016-06-13.
 */
public class MineFragment extends Fragment implements View.OnClickListener{
    private ChioesView orders;
    private ChioesView set;
    private CircleImageView hand_img;
    private ChioesView store;
    private OKManager okManager = OKManager.getInstance();
    private Button login;
    private TextView username;
    private UserInfo userInfo;
    private boolean isLogin;
    private ChioesView MyAddress;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine,container,false);
        set = (ChioesView) view.findViewById(R.id.setting);
        orders = (ChioesView) view.findViewById(R.id.orders);
        login = (Button) view.findViewById(R.id.login);
        username = (TextView) view.findViewById(R.id.userName);
        MyAddress = (ChioesView) view.findViewById(R.id.address);
        orders.setOnClickListener(this);
        set.setOnClickListener(this);
        MyAddress.setOnClickListener(this);
        hand_img = (CircleImageView) view.findViewById(R.id.img_head);
        store = (ChioesView) view.findViewById(R.id.store);
        store.setOnClickListener(this);
        login.setOnClickListener(this);
        //判断是否登陆
        initView();
        return view;
    }

    private void initView() {
        isLogin = Myapplication.isOnline();
        if(isLogin){
            this.userInfo = Myapplication.getUserInfo();
            login.setVisibility(View.GONE);
            username.setVisibility(View.VISIBLE);
            username.setText(this.userInfo.getName());
        }else {
            username.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
        }
//        getUserInfo();
//        username.setText(this.userInfo.getName()+this.userInfo.getId());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting:
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.orders:
                Intent intent1 = null;
                if(isLogin){
                    //转跳到订单的Activity
                    intent1 = new Intent(getActivity(), OrderListActivity.class);
                    intent1.putExtra("user_id",this.userInfo.getId());
                }else{
                    intent1 = new Intent(getActivity(),LoginActivity.class);
                }
                startActivity(intent1);
            case R.id.store:
                break;
            case R.id.address:
                Intent addressintent = null;
                if(isLogin){
                    addressintent = new Intent(getActivity(), AddrActivity.class);
                }else {
                    addressintent = new Intent(getActivity(), LoginActivity.class);
                }
                startActivity(addressintent);
                break;
            case R.id.login:
                Intent loginIntent= new Intent(getActivity(),LoginActivity.class);
                startActivity(loginIntent);
                break;

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }
}
