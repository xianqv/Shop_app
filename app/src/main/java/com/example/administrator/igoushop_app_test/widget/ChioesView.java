package com.example.administrator.igoushop_app_test.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.igoushop_app_test.R;

/**
 * Created by Administrator on 2016-06-12.
 */
public class ChioesView extends LinearLayout {
    private LayoutInflater inflater;
    private ImageView leftimg;
    private TextView title;
    private TextView message;
    public ChioesView(Context context) {
        this(context,null);
    }

    public ChioesView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ChioesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflater = LayoutInflater.from(context);
        init();
        if(attrs!=null){
            TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(),attrs, R.styleable.ChioesView,defStyleAttr,0);

            String Item_title = a.getString(R.styleable.ChioesView_Item_title);
            if(Item_title!=null){
                setItemTitle(Item_title);
            }
            String Item_Msg = a.getString(R.styleable.ChioesView_Item_Msg);
            if(Item_Msg!=null){
                setItemMessage(Item_Msg);
            }
            Drawable LeftImg = a.getDrawable(R.styleable.ChioesView_LeftImg);
            if(LeftImg!=null){
                setLeftImage(LeftImg);
            }

        }
    }

    private void setLeftImage(Drawable leftImg) {
        this.leftimg.setImageDrawable(leftImg);
    }

    public void setItemMessage(String item_msg) {
        this.message.setText(item_msg);
    }

    private void setItemTitle(String item_title) {
        this.title.setText(item_title);
    }

    private void init() {
        initView();
    }

    private void initView() {
        View view = inflater.inflate(R.layout.chiose_item,this,true);
        leftimg = (ImageView) view.findViewById(R.id.leftimg);
        title = (TextView) view.findViewById(R.id.title);
        message = (TextView) view.findViewById(R.id.msg);
    }


}
