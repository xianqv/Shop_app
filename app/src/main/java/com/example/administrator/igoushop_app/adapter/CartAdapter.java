package com.example.administrator.igoushop_app.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.CheckBox;

import com.example.administrator.igoushop_app.R;
import com.example.administrator.igoushop_app.pojos.Cart;
import com.example.administrator.igoushop_app.widget.Contants;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2016-06-08.
 */
public class CartAdapter extends SimpleAdapter<Cart> {
    private onItemDeleteListener deleteListener;
    private onItemSelectListener listener;
    public CartAdapter(Context context, int layoutResId, List<Cart> datas) {
        super(context, layoutResId, datas);
    }

    @Override
    protected void convert(final BaseViewHolder viewHoder, final Cart item) {
        viewHoder.getTextView(R.id.text).setText(item.getProduct().getName());
        String color = item.getColor();
        if(color.substring(0).equals("+")){
            viewHoder.getTextView(R.id.waresColor).setText(color.substring(1,color.length()-1));
        }else {
            viewHoder.getTextView(R.id.waresColor).setText(color);
        }
        viewHoder.getTextView(R.id.waresSize).setText(item.getSize());
        viewHoder.getTextView(R.id.waresNum).setText("X "+item.getNum());
        viewHoder.getTextView(R.id.waresPrice).setText("¥ "+(item.getNum()*item.getProduct().getPrice())+"");
        SimpleDraweeView draweeView = viewHoder.getSimpleDraweeView(R.id.imageshow);
        draweeView.setImageURI(Uri.parse(Contants.Img_URL+item.getProduct().getImgUrl()));
        draweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onclick(item.getProId(),true);
            }
        });
        final CheckBox button = viewHoder.getCheckBox(R.id.checkbox);
        if(viewHoder.getAdapterPosition() == 1){
            button.setSelected(true);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onclick(viewHoder.getAdapterPosition(), button.isChecked());
            }
        });
        viewHoder.getTextView(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteListener.onclick(item.getId());
            }
        });
    }

    public void setOnItemSelectListener(onItemSelectListener listener){
        this.listener = listener;
    }


    public void setOnItemDeleteListener(onItemDeleteListener deleteListener){
        this.deleteListener = deleteListener;
    }
    public interface onItemSelectListener{
        void onclick(int id,boolean ischeck);
    }
    public interface onItemDeleteListener{
        void onclick(int id);
    }
    public void clear(){
        if (datas !=null && datas.size()>0) {
            datas.clear();
            notifyDataSetChanged();
        }
    }
}
