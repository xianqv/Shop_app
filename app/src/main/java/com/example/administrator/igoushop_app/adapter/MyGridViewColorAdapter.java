package com.example.administrator.igoushop_app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.administrator.igoushop_app.R;
import com.example.administrator.igoushop_app.activity.ProductDetailActivity;
import com.example.administrator.igoushop_app.pojos.Color;
import com.example.administrator.igoushop_app.pojos.Size;
import com.example.administrator.igoushop_app.widget.ProductSelectAttr;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2016-08-26.
 */
public class MyGridViewColorAdapter extends android.widget.BaseAdapter {
    private Context context;
    private List<Color> colors;
    private boolean isFlag = true ;
    private int now =0;
    private ProductDetailActivity activity;
    private onItemClickListener listener;
    public MyGridViewColorAdapter(Context context,List<Color> colors){
        this.context = context;
        this.colors = colors;
    }
    @Override
    public int getCount() {
        return colors.size();
    }

    @Override
    public Object getItem(int position) {
        return colors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder viewHolder;
        //初始化 ViewHolder 的 textView
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.product_select_gridview_item,null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (Button) convertView.findViewById(R.id.size);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //进行判断
        if(isFlag){
            viewHolder.textView.setBackgroundResource(R.drawable.gridview_select_true);
            isFlag = false;
        }
        viewHolder.textView.setTag("work"+position);
        viewHolder.textView.setText(colors.get(position).getName());
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(now!=position){
                    TextView tv = (TextView) parent.findViewWithTag("work"+now);
                    if(tv!=null){
                        tv.setBackgroundResource(R.drawable.gridview_select_false);
                    }
                    viewHolder.textView.setBackgroundResource(R.drawable.gridview_select_true);
                    now = position;
                    if(listener!=null){
                        listener.onItemClick(colors,now);
                    }
                }
            }
        });
        return convertView;
    }
    class ViewHolder {
        Button textView ;
    }



    public void setItemClickListener(onItemClickListener itemClickListener) {
        this.listener = itemClickListener;
    }

    public interface onItemClickListener{
        public void onItemClick(List<Color> data, int position);
    }
    public void changeColor(List<Color> c){
        colors.clear();
        for (Color color:c){
            this.colors.add(color);
        }
        notifyDataSetChanged();
    }
    public List<Color> getAllColors(){
        return this.colors;
    }

    public String getNowItem() {
        return colors.get(now).getName();
    }
}
