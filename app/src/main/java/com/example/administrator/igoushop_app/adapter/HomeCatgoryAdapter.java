package com.example.administrator.igoushop_app.adapter;

import android.content.Context;
import android.view.View;

import com.example.administrator.igoushop_app.R;
import com.example.administrator.igoushop_app.pojos.Product;
import com.example.administrator.igoushop_app.widget.Contants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-06-04.
 */
public class HomeCatgoryAdapter  extends SimpleAdapter<Product>{
    private Context context;
    private List<Product> list ;
    public setonItemClickListener listener;
    public void setListener(setonItemClickListener listener){
        this.listener = listener;
    }

    public HomeCatgoryAdapter(Context context, int layoutResId) {
        super(context, layoutResId);

    }

    public HomeCatgoryAdapter(Context context, int layoutResId, List<Product> datas) {
        super(context, layoutResId, datas);
        this.context = context;
        this.list = new ArrayList<>();
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, final Product item) {
        Picasso.with(context).load(Contants.Img_URL+item.getImgUrl()).into(viewHoder.getImageView(R.id.tv_img));
        viewHoder.getTextView(R.id.tv_name).setText(item.getName());
        viewHoder.getTextView(R.id.tv_price).setText(item.getPrice()+"");
        viewHoder.getTextView(R.id.tv_buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.toBuy(item.getId());
            }
        });
    }
    public interface setonItemClickListener{
        void toBuy(int id);
    }


}
