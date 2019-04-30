package com.example.administrator.igoushop_app.adapter;

import android.content.Context;

import com.example.administrator.igoushop_app.R;
import com.example.administrator.igoushop_app.pojos.ProductType;

import java.util.List;


/**
 * Created by Administrator on 2016-06-06.
 */
public class ClassFragmentLeftAdapter extends SimpleAdapter<ProductType> {
    private Context context;
    private List<ProductType> types;

    public ClassFragmentLeftAdapter(Context context, int layoutResId, List<ProductType> datas) {
        super(context, layoutResId, datas);
        this.context = context;
        this.types = datas;
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, ProductType item) {
        viewHoder.getTextView(R.id.lefttitle).setText(item.getName());
    }
}
