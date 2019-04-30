package com.example.administrator.igoushop_app.widget;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.igoushop_app.R;
import com.example.administrator.igoushop_app.adapter.ProductDetailAdapter;
import com.example.administrator.igoushop_app.pojos.Attribute;
import com.example.administrator.igoushop_app.pojos.Product;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Administrator on 2016-08-18.
 */
public class ProductDetail extends BottomSheet {
    private  Product product;
    private Attribute attribute;
    private ListView attrlist;
    private TextView close;
    private List<String> items  ;
    private Context context;
    private Map<String,String> Amap = new TreeMap<>();
    private ProductDetailAdapter adapter;
    public ProductDetail(Context context, Product product) {
        super(context);
        this.context = context;
        this.product = product;
        this.attribute = product.getAttribute();
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.product_attribute;
    }

    @Override
    public void initView(View root) {
        //初始化 商品属性
        //初始化 RecycleView 的列表项 与对应 属性
        Amap = initMap(Amap);
        items = attributeToString(this.attribute);
        Log.d("---------->>>>",""+items.size()+"---xxxx--"+attribute.getSeason());
        attrlist = (ListView) root.findViewById(R.id.attr_show);
        close = (TextView) root.findViewById(R.id.dissen);
        adapter = new ProductDetailAdapter(getContext(),R.layout.product_attribute_item,items);
        attrlist.setAdapter(adapter);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    private Map<String, String> initMap(Map<String, String> amap) {
        amap.put("材质成份","Material");
        amap.put("款式","Style");
        amap.put("适用对象","UserObject");
        amap.put("适用季节","Season");
        amap.put("CUP型号","CupModel");
        amap.put("内存大小","ScreenSize");
        amap.put("操作系统","OperatingSystem");
        amap.put("内存说明","Memory");
        amap.put("附加说明","Additional");
        amap.put("分辨率","ScreenResolution");
//        amap.put("描述","Describle");
        return amap;
    }

    //通过  反射机制  将 attribute 属性转化成 字符串
    private List<String> attributeToString(Attribute attribute) {
        items = new ArrayList<>();
        items.add("上市时间: "+ product.getMarketDate());
        Method metd;
        Class cla = attribute.getClass();
        try {

            for (String str : Amap.keySet()){
                metd = cla.getMethod("get"+Amap.get(str));
                String value = (String) metd.invoke(this.attribute);
                if(!value.equals("")){
                    items.add(str+":    "+value);
                }
            }
            items.add("描述"+"   "+attribute.getDescrible());

        }catch (Exception e){
            e.printStackTrace();
        }
        return items;
    }

    private String toUpper(String fdname) {
        char[] cs=fdname.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }

}
