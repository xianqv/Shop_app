package com.example.administrator.igoushop_app_test.adapter;

import android.content.Context;
import android.net.Uri;

import com.example.administrator.igoushop_app_test.R;
import com.example.administrator.igoushop_app_test.pojos.Product;
import com.example.administrator.igoushop_app_test.widget.Contants;

import java.util.List;

/**
 * Created by Administrator on 2016-08-17.
 */
public class ProductListAdapter extends SimpleAdapter<Product> {
    private List<Product> proList;
    private  Context context;
    public ProductListAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }


    public ProductListAdapter(Context context, int layoutResId, List datas) {
        super(context, layoutResId, datas);
        this.proList = datas;
        this.context = context;

    }
    @Override
    protected void convert(BaseViewHolder viewHoder, Product item) {
        viewHoder.getSimpleDraweeView(R.id.pro_image).setImageURI(Uri.parse(Contants.Img_URL+item.getImgUrl()));

        if(item.getName().length()>10){
            viewHoder.getTextView(R.id.pro_name).setText(item.getName().substring(0,11)+"...");
        }else {
            viewHoder.getTextView(R.id.pro_name).setText(item.getName());
        }
        viewHoder.getTextView(R.id.product_price).setText("¥ "+item.getPrice());
        viewHoder.getTextView(R.id.salNum).setText("销售量: "+item.getSaleNum());
    }
    public List<Product> getProList(){
        return proList;
    }
    public void updateProList(List<Product> list){
        this.proList = list;
        notifyDataSetChanged();
    }





}
