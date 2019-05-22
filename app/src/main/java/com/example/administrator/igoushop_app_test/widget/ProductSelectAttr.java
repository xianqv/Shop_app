package com.example.administrator.igoushop_app_test.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.igoushop_app_test.Myapplication;
import com.example.administrator.igoushop_app_test.R;
import com.example.administrator.igoushop_app_test.activity.LoginActivity;
import com.example.administrator.igoushop_app_test.adapter.MyGridViewAdapter;
import com.example.administrator.igoushop_app_test.adapter.MyGridViewColorAdapter;
import com.example.administrator.igoushop_app_test.http.OKManager;
import com.example.administrator.igoushop_app_test.pojos.Attribute;
import com.example.administrator.igoushop_app_test.pojos.Cart;
import com.example.administrator.igoushop_app_test.pojos.Color;
import com.example.administrator.igoushop_app_test.pojos.FEMOrder;
import com.example.administrator.igoushop_app_test.pojos.Product;
import com.example.administrator.igoushop_app_test.pojos.Size;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016-08-19.
 */
public class ProductSelectAttr extends BottomSheet implements View.OnClickListener{
    public MyGridView select_size,select_color;
    private CartNumberView proNum;
    private Context context;
    public Product product;
    private TextView price;
    public TextView cho_size,cho_color,buy,addCart;
    private Attribute attribute;
    private List<Size> sizes;
    private OKManager manager = OKManager.getInstance();
    private List<String> data = new ArrayList<>();
    private SimpleDraweeView image;
    private MyGridViewAdapter selectSizeadapter ;
    private MyGridViewColorAdapter colorAdapter;
    private String select = "";
    private  onGetOrderListener listener;
    public void setOnGetOrderListener(onGetOrderListener listener){
        this.listener = listener;
    }
    public ProductSelectAttr(Context context, Product product) {
        super(context);
        this.context = context;
        this.product = product;
        this.attribute = product.getAttribute();
        this.sizes = DataUtils.toArray(product.getSizes());
    }
    @Override
    public int getContentLayoutId() {
        Fresco.initialize(getContext());
        return R.layout.product_select;
    }

    @Override
    public void initView(View root) {
        image = (SimpleDraweeView) root.findViewById(R.id.pro_image);
        cho_size = (TextView) root.findViewById(R.id.cho_text1);
        cho_color = (TextView) root.findViewById(R.id.cho_text2);
        buy = (TextView) root.findViewById(R.id.buy);
        proNum = (CartNumberView) root.findViewById(R.id.pronum);
        addCart = (TextView) root.findViewById(R.id.addMyCart);
        addCart.setOnClickListener(this);
        price = (TextView) root.findViewById(R.id.pro_price);
        price.setText("¥ "+product.getPrice());
        image.setImageURI(Uri.parse(Contants.Img_URL+product.getImgUrl()));
        select_size = (MyGridView) root.findViewById(R.id.select_size);
        select_size.setNumColumns(4);
        selectSizeadapter = new MyGridViewAdapter(getContext(),DataUtils.toArray(product.getSizes()),R.layout.product_select_gridview_item);
        select_size.setAdapter(selectSizeadapter);
      selectSizeadapter.setItemClickListener(new MyGridViewAdapter.onItemClickListener() {
          @Override
          public void onItemClick(List<Size> data, int position) {
              colorAdapter.changeColor(DataUtils.toArrayFromColor(data.get(position).getColors()));
              select = colorAdapter.getNowItem();
              cho_size.setText(data.get(position).getName());
              cho_color.setText("+"+select);
          }
      });

        select_color = (MyGridView) root.findViewById(R.id.select_color);
        select_color.setNumColumns(4);
        Set<Size> sizes =  product.getSizes();
        Set<Color> colors = new HashSet<>();
        if(sizes.size()==0){
            colorAdapter = new MyGridViewColorAdapter(getContext(), DataUtils.toArrayFromColor(colors));
        }else {
            colorAdapter = new MyGridViewColorAdapter(getContext(), DataUtils.toArrayFromColor(DataUtils.toArray(product.getSizes()).get(0).getColors()));
        }
        select_color.setAdapter(colorAdapter);
        colorAdapter.setItemClickListener(new MyGridViewColorAdapter.onItemClickListener() {
            @Override
            public void onItemClick(List<Color> data, int position) {
                cho_color.setText("+"+data.get(position).getName());
            }
        });
        cho_color.setText(colorAdapter.getNowItem());
        cho_size.setText(selectSizeadapter.getNowSize());


        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Myapplication.isOnline()){
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }else {
                    FEMOrder order = new FEMOrder();
                    order.setNum(proNum.getValue());
                    order.setColor(cho_color.getText().toString());
                    order.setSize(cho_size.getText().toString());
                    order.setImgUrl(product.getImgUrl());
                    order.setProId(product.getId());
                    order.setOnePrice(product.getPrice());
                    order.setName(product.getName());
                    listener.get(order);
                }
            }
        });

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addMyCart:
                Cart cart = new Cart();
                cart.setSize(cho_size.getText().toString());
                cart.setColor(cho_color.getText().toString());
                cart.setNum(proNum.getValue());
                cart.setUserId(Myapplication.getUserInfo().getId());
                cart.setProId(product.getId());
                addCart(cart);
                break;
        }
    }

    private void addCart(Cart cart) {
        if(!Myapplication.isOnline()){
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        }else {
            Map<String, String> map = new HashMap<String, String>();
            map.put("color", cart.getColor());
            map.put("size", cart.getSize());
            map.put("num", cart.getNum() + "");
            map.put("userId", cart.getUserId() + "");
            map.put("proId", cart.getProId() + "");
            manager.sendComplexForm(Contants.BASE_URL + "Cart_addProduct.action", map, new OKManager.Func4() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        String success = jsonObject.get("success").toString();
                        String msg = jsonObject.get("msg").toString();
                        if (success.equals("true")) {
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public TextView getCho_color() {
        return cho_color;
    }

    public void setAddCartHide(){
        this.addCart.setVisibility(View.GONE);
        this.buy.setVisibility(View.VISIBLE);
    }
    public void setBuyHide(){
        this.addCart.setVisibility(View.VISIBLE);
        this.buy.setVisibility(View.GONE);
    }
    public void showButton(){
        this.addCart.setVisibility(View.VISIBLE);
        this.buy.setVisibility(View.VISIBLE);
    }
    public interface onGetOrderListener{
        void get(FEMOrder order);
    }
}
