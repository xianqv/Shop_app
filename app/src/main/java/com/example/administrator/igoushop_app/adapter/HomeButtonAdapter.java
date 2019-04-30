package com.example.administrator.igoushop_app.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


import com.example.administrator.igoushop_app.R;
import com.example.administrator.igoushop_app.bean.HomeButton;
import com.example.administrator.igoushop_app.pojos.FunctionButton;
import com.example.administrator.igoushop_app.widget.Contants;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016-06-05.
 */
public class HomeButtonAdapter extends android.widget.BaseAdapter {
    private Context context;
    private List<FunctionButton> date;
    private LayoutInflater inflater;
    public HomeButtonAdapter(Context context,List<FunctionButton> date ){
        this.context = context;
        this.date = date;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return date.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder;
//        if(convertView!=null){
//            convertView = inflater.inflate(R.layout.home_serverbutton,null);
//            holder = new ViewHolder();
//            holder.tv=(TextView) convertView.findViewById(R.id.title);
//            holder.img=(ImageView) convertView.findViewById(R.id.image);
//            convertView.setTag(holder);
//        }else{
//            holder=(ViewHolder) convertView.getTag();
//        }
//        holder.tv.setText(date.get(position).getName());
//        Picasso.with(context).load(Contants.Img_URL+date.get(position).getImageUrl()).into(holder.img);
//        return convertView;
//    }
//
//
//    private class ViewHolder {
//        TextView tv=null;
//        ImageView img=null;
//        public TextView getTv() {
//            return tv;
//        }
//        public void setTv(TextView tv) {
//            this.tv = tv;
//        }
//        public ImageView getImg() {
//            return img;
//        }
//        public void setImg(ImageView img) {
//            this.img = img;
//        }

        convertView=  inflater.inflate(R.layout.home_serverbutton,null);

        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        TextView name= (TextView) convertView.findViewById(R.id.title);
        Picasso.with(context).load(Uri.parse(Contants.Img_URL+date.get(position).getImageUrl())).into(image);
//        image.setImageURI(Uri.parse(Contants.Img_URL+date.get(position).getImageUrl()));
        name.setText(date.get(position).getName());
        return convertView;
    }
}
