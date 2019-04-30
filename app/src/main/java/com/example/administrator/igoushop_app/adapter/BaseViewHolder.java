package com.example.administrator.igoushop_app.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by <a href="http://www.cniao5.com">菜鸟窝</a>
 * 一个专业的Android开发在线教育平台
 */
public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    private SparseArray<View> views;

    private BaseAdapter.OnItemClickListener mOnItemClickListener ;

    public BaseViewHolder(View itemView,BaseAdapter.OnItemClickListener onItemClickListener){
        super(itemView);
        itemView.setOnClickListener(this);

        this.mOnItemClickListener =onItemClickListener;
        this.views = new SparseArray<View>();
    }

    public TextView getTextView(int viewId) {
        return retrieveView(viewId);
    }
    public RadioButton getRadioButton(int viewId){
        return retrieveView(viewId);
    }
    public CheckBox getCheckBox(int viewId){
        return retrieveView(viewId);
    }
    public Button getButton(int viewId) {
        return retrieveView(viewId);
    }

    public ImageView getImageView(int viewId) {
        return retrieveView(viewId);
    }

    public View getView(int viewId) {
        return retrieveView(viewId);
    }

    public SimpleDraweeView getSimpleDraweeView(int viewId){
        return retrieveView(viewId);
    }

    protected <T extends View> T retrieveView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v,getLayoutPosition());
        }
    }
}
