package com.example.administrator.igoushop_app.adapter.hot_adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.administrator.igoushop_app.R;
import com.example.administrator.igoushop_app.adapter.BaseAdapter;
import com.example.administrator.igoushop_app.adapter.BaseViewHolder;
import com.example.administrator.igoushop_app.adapter.SimpleAdapter;
import com.example.administrator.igoushop_app.pojos.Brand;
import com.example.administrator.igoushop_app.widget.Contants;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.List;

/**
 * Created by Administrator on 2016-06-25.
 */
public class HotOneAdapter extends SimpleAdapter<Brand>{
    private Context context;
    private List<Brand> mdate;


    public HotOneAdapter(Context context, int layoutResId,  List<Brand> dates) {
        super(context, layoutResId, dates);
        this.context = context;
        this.mdate = dates;
    }
    @Override
    protected void convert(BaseViewHolder viewHoder, Brand item) {
        viewHoder.getSimpleDraweeView(R.id.brand_image).setImageURI(Uri.parse(Contants.Img_URL+item.getImgUrl()));
        viewHoder.getTextView(R.id.brandname).setText(item.getName()+" 专场");
    }


    @Override
    public void addData(List<Brand> datas) {
//        super.addData(datas);
        clear();
        notifyItemChanged(0,datas.size());
        mdate.addAll(datas);
        notifyItemChanged(0,datas.size());
    }



}
