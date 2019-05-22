package com.example.administrator.igoushop_app_test.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.igoushop_app_test.Myapplication;
import com.example.administrator.igoushop_app_test.R;
import com.example.administrator.igoushop_app_test.activity.LoginActivity;
import com.example.administrator.igoushop_app_test.activity.OrderActivity;
import com.example.administrator.igoushop_app_test.activity.ProductDetailActivity;
import com.example.administrator.igoushop_app_test.adapter.BaseAdapter;
import com.example.administrator.igoushop_app_test.adapter.CartAdapter;
import com.example.administrator.igoushop_app_test.http.OKManager;
import com.example.administrator.igoushop_app_test.pojos.Cart;
import com.example.administrator.igoushop_app_test.pojos.FEMOrder;
import com.example.administrator.igoushop_app_test.tools.JacksonUtils;
import com.example.administrator.igoushop_app_test.widget.Contants;
import com.example.administrator.igoushop_app_test.widget.CustomToolBar;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-06-07.
 */
public class CartFragment extends Fragment{
    private RecyclerView mrecyclerView;
    private TextView Pay_button;
    private CustomToolBar mtoolBar;
    private CartAdapter mAdapter;
    private List<String> carts;
    private int cartId;
    private TextView totalPrice;
    private OKManager manager = OKManager.getInstance();
    private List<Cart> cs = new ArrayList<Cart>();
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Fresco.initialize(getActivity());
        View view  = inflater.inflate(R.layout.fragment_cart,container,false);
//        isLogin();
        mrecyclerView = (RecyclerView) view.findViewById(R.id.cart_shopping_listview);
        Pay_button = (TextView) view.findViewById(R.id.jiesuan_button);
        mtoolBar = (CustomToolBar) view.findViewById(R.id.CartToolbar);
        totalPrice = (TextView) view.findViewById(R.id.totalPrice);
        Pay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                if(cs.size()==1){
                    Bundle bundle = new Bundle();
                    FEMOrder order = new FEMOrder();
                    Cart cart = cs.get(0);
                    order.setNum(cart.getNum());
                    order.setColor(cart.getColor());
                    order.setSize(cart.getSize());
                    order.setImgUrl(cart.getProduct().getImgUrl());
                    order.setProId(cart.getProId());
                    order.setOnePrice(cart.getProduct().getPrice());
                    order.setName(cart.getProduct().getName());
                    bundle.putSerializable("femorder",order);
                    intent.putExtra("fem",bundle);
                    startActivity(intent);
                }else if(cs.size()>=2){
                    Toast.makeText(getActivity(),"请选择一件商品进行支付!",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(),"请选择商品!",Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!Myapplication.isOnline()){
            if (mAdapter!=null){
                mAdapter.clear();
            }

        }else {
            requestDate();
        }
    }
    private void requestDate() {
        if(Myapplication.getUserInfo()!=null){
            int userId = Myapplication.getUserInfo().getId();
            Map<String,String> map = new HashMap<String,String>();
            map.put("userId",userId+"");
            manager.sendComplexForm(Contants.BASE_URL+"Cart_findAllCarts.action", map, new OKManager.Func4() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    if(jsonObject!=null){
                        try {
                            String success = (String) jsonObject.get("success");
                            String result = jsonObject.get("carts").toString();
                            if("true".equals(success)){
                               List<Cart> carts  = JacksonUtils.toList(result,ArrayList.class,Cart.class);
                                initDate(carts);
                            }else{
                                Toast.makeText(getActivity(),"购物车为空",Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    private void initDate(final List<Cart> carts) {
        try {
            if (carts != null && carts.size() > 0) {
                cartId = carts.get(0).getId();
                mAdapter = new CartAdapter(getActivity(), R.layout.item_show, carts);
                mrecyclerView.setAdapter(mAdapter);
                mrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mrecyclerView.setItemAnimator(new DefaultItemAnimator());
                mAdapter.setOnItemSelectListener(new CartAdapter.onItemSelectListener() {
                    @Override
                    public void onclick(int id,boolean ischeck) {
                        if(ischeck){
                            cs.add(carts.get(id));
                            initTotalPrice();
                        }else {
                            deleteById(carts.get(id));
                            initTotalPrice();
                        }

                    }
                });
                mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }
                });
                mAdapter.setOnItemSelectListener(new CartAdapter.onItemSelectListener() {
                    @Override
                    public void onclick(int id, boolean ischeck) {
                        Integer product_id = id;
                        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                        intent.putExtra(Contants.Product,""+product_id);
                        startActivity(intent);
                    }
                });
                mAdapter.setOnItemDeleteListener(new CartAdapter.onItemDeleteListener() {
                    @Override
                    public void onclick(final int id) {
                        WindowManager windowManager = getActivity().getWindowManager();
                        Display display = windowManager.getDefaultDisplay();
                        int width = display.getWidth();
                        int height = display.getHeight();
                        final AlertDialog myDialog = new AlertDialog.Builder(getActivity()).create();
                        myDialog.show();
                        myDialog.getWindow().setLayout((int) (width*0.75), (int) (height/2*0.75*0.75));
                        myDialog.getWindow().setContentView(R.layout.alert_dialog_layout);
                        myDialog.getWindow()
                                .findViewById(R.id.alert_btn)
                                .setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Map<String,String> map = new HashMap<String, String>();
                                        map.put("id",""+id);
                                        manager.sendComplexForm(Contants.BASE_URL+"Cart_deleteById.action", map, new OKManager.Func4() {
                                            @Override
                                            public void onResponse(JSONObject jsonObject) {
                                                try {
                                                    String error = jsonObject.get("error").toString();
                                                    if("true".equals(error)){
                                                        Toast.makeText(getActivity(),""+jsonObject.get("Msg"),Toast.LENGTH_SHORT).show();
                                                        //请求数据
                                                        requestDate();
                                                        myDialog.dismiss();
                                                    }else {
                                                        Toast.makeText(getActivity(),""+jsonObject.get("Msg"),Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                    }
                                });
                        myDialog.getWindow().findViewById(R.id.alert_cancel)
                                .setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        myDialog.dismiss();
                                    }
                                });
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void deleteById(Cart cart) {
        if(cs.contains(cart)){
            cs.remove(cart);
        }
    }
    public void cleanCs(){
        for (Cart cart:cs){
            cs.remove(cart);
        }
    }


    private void initTotalPrice() {
        if(cs!=null && cs.size()>0){
            int total =0;
            for (Cart cart:cs){
                total+=cart.getNum()*cart.getProduct().getPrice();
            }
            totalPrice.setText("¥ "+total);
        }else {
            totalPrice.setText("¥ "+0);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
//        isLogin();
    }
    private void isLogin(){
        if(!Myapplication.isOnline()){
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

}
