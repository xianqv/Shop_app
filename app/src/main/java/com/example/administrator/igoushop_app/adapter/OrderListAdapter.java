package com.example.administrator.igoushop_app.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.igoushop_app.R;
import com.example.administrator.igoushop_app.pojos.Order;
import com.example.administrator.igoushop_app.widget.Contants;

import java.util.List;

/**
 * Created by Administrator on 2017/4/22.
 */

public class OrderListAdapter extends SimpleAdapter<Order> {
    private Context context;
    private onDeleteItemListener deleteItemListener;
    private onRemoveOrderListener removeOrderListener;
    private onBuyAgainListener buyAgainListener;
    public void setOnBuyAgainListener(onBuyAgainListener listener){
        this.buyAgainListener = listener;
    }
    public void setOnDeleteItemListener(onDeleteItemListener listener){
        this.deleteItemListener = listener;
    }
    public void setOnRemoveistener(onRemoveOrderListener listener){
        this.removeOrderListener = listener;
    }
    public OrderListAdapter(Context context, int layoutResId, List<Order> datas) {
        super(context, layoutResId, datas);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, final Order item) {
        viewHoder.getImageView(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItemListener.delete(item.getId());
            }
        });

        viewHoder.getTextView(R.id.tv_pro_name).setText(item.getProName());
        viewHoder.getTextView(R.id.color).setText(item.getColor());
        viewHoder.getTextView(R.id.size).setText(item.getSize());
        viewHoder.getTextView(R.id.oneprice).setText(item.getOnePrice()+"");
        viewHoder.getTextView(R.id.num).setText("x "+item.getNum());
        viewHoder.getTextView(R.id.order_id).setText(item.getId()+"");
        viewHoder.getTextView(R.id.tv_buy_msg).setText("共"+item.getNum()+"件商品实付：");
        viewHoder.getTextView(R.id.tv_buy_totailprice).setText("¥ "+item.getTotalPrice());
        TextView buy_evaluate = viewHoder.getTextView(R.id.buy_evaluate);
        final TextView buy_again = viewHoder.getTextView(R.id.buy_again);
        TextView remove_order = viewHoder.getTextView(R.id.remove_order);
        remove_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeOrderListener.remove(item.getId(),-1);
            }
        });
        final TextView isGet = viewHoder.getTextView(R.id.isGet);
        isGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeOrderListener.remove(item.getId(),2);
            }
        });
        buy_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyAgainListener.click(item.getProId());
            }
        });
        viewHoder.getSimpleDraweeView(R.id.order_img).setImageURI(Uri.parse(Contants.Img_URL+item.getImgURL()));
        int isDeliver = item.getIsDeliver();
        String status = null;
        if (isDeliver == -1){
            status = "已取消";
            buy_evaluate.setVisibility(View.GONE);
            buy_again.setVisibility(View.GONE);
            remove_order.setVisibility(View.GONE);
            isGet.setVisibility(View.GONE);
        }
        if(isDeliver == 0){
            status = "未发货";
            buy_evaluate.setVisibility(View.GONE);
            buy_again.setVisibility(View.GONE);
            remove_order.setVisibility(View.VISIBLE);
            isGet.setVisibility(View.GONE);
        }else if(isDeliver == 1){
            status = "已发货";
            buy_evaluate.setVisibility(View.GONE);
            buy_again.setVisibility(View.GONE);
            remove_order.setVisibility(View.GONE);
            isGet.setVisibility(View.VISIBLE);
        }else if(isDeliver == 2){
            status="已收货";
            buy_evaluate.setVisibility(View.VISIBLE);
            buy_again.setVisibility(View.VISIBLE);
            remove_order.setVisibility(View.GONE);
            isGet.setVisibility(View.GONE);
        }
        viewHoder.getTextView(R.id.status).setText(status);
    }
    public interface onDeleteItemListener{
        void delete(int orderId);
    }
    public interface onRemoveOrderListener{
        void remove(int orderId,int status);
    }
    public interface onBuyAgainListener{
        void click(int id);
    }
}
