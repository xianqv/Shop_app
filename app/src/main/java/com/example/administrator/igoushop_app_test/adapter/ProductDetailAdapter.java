package com.example.administrator.igoushop_app_test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.administrator.igoushop_app_test.R;

import java.util.List;

/**
 * Created by Administrator on 2016-08-19.
 */
public class ProductDetailAdapter extends android.widget.BaseAdapter {
    private List<String> mdata;
    private Context context;
    private int resId;
    public ProductDetailAdapter(Context context, int resId ,List<String> mdatas) {
        this.context = context;
        this.mdata = mdatas;
        this.resId = resId;
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
    public View getView(int position, View convertView, ViewGroup parent) {
//        TextView attr_show = new TextView(context);
//        attr_show.setTextSize(17);
//        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,50);
//        attr_show.setLayoutParams(params);
//        attr_show.setGravity(Gravity.CENTER_HORIZONTAL);
//        attr_show.setPadding(10,0,0,0);
//        attr_show.setBackgroundResource(R.color.white);
//        attr_show.setText(mdata.get(position));
            View view = LayoutInflater.from(context).inflate(resId,null);
        TextView attr_show = (TextView) view.findViewById(R.id.attribuet_show);
        attr_show.setText(mdata.get(position));
        return attr_show;
    }
}
