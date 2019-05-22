package com.example.administrator.igoushop_app_test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.administrator.igoushop_app_test.R;
import com.example.administrator.igoushop_app_test.pojos.Size;

import java.util.List;

/**
 * Created by Administrator on 2016-08-20.
 */
public class MyGridViewAdapter extends android.widget.BaseAdapter {
    private Context context;
    private List<Size> mdata;
    private int resId;
    private boolean isFlag = true ;
    public int now =0;
    public onItemClickListener itemClickListener;

    public MyGridViewAdapter(Context context, List<Size> sizelist,int resId){
        this.context = context;
        this.mdata = sizelist;
        this.resId = resId;
    }
    public void setIsFlag(boolean flag){
        this.isFlag = flag;
    }

    @Override
    public int getCount() {
        return mdata.size();
    }

    @Override
    public Object getItem(int position) {
        return mdata.get(position);
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
            convertView = LayoutInflater.from(context).inflate(resId,null);
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
        viewHolder.textView.setText(mdata.get(position).getName());
        final Size size = mdata.get(position);
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
                    if(itemClickListener!=null){
                        itemClickListener.onItemClick(mdata,now);
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
        this.itemClickListener = itemClickListener;
    }

    public interface onItemClickListener{
        public void onItemClick(List<Size> data, int position);
    }

    public String getNowSize(){
        return mdata.get(now).getName();
    }

}
