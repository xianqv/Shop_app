package com.example.administrator.igoushop_app.widget;

import android.content.Context;
import android.support.annotation.BoolRes;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.igoushop_app.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016-06-16.
 */
public class SecondItemChiose extends LinearLayout {
    private TextView Item_Title;
    private TextView Item_Msg;
    private CircleImageView Hnad_img;
    private LayoutInflater inflater;
    public SecondItemChiose(Context context) {
        this(context,null);
    }

    public SecondItemChiose(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SecondItemChiose(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflater = LayoutInflater.from(context);
        initView();
        if(attrs!=null){
            TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(),attrs,R.styleable.SecondItemChiose,defStyleAttr,0);
            String title = a.getString(R.styleable.SecondItemChiose_item_title);
            if(title!=null){
                setTitle(title);
            }
            String Msg = a.getString(R.styleable.SecondItemChiose_item_msg);
            if(Msg!=null){
                setMsg(Msg);
            }
            Boolean isShowHandImg = a.getBoolean(R.styleable.SecondItemChiose_hand_img,false);
            if(isShowHandImg){
                Hnad_img.setVisibility(VISIBLE);
                Item_Msg.setVisibility(GONE);
            }else{
                Hnad_img.setVisibility(GONE);
                Item_Msg.setVisibility(VISIBLE);
            }
        }
    }

    public void setMsg(String msg) {
        this.Item_Msg.setText(msg);
    }

    public void setTitle(String title) {
        this.Item_Title.setText(title);
    }

    private void initView() {
        View view = inflater.inflate(R.layout.second_item_chiose,this,true);
        Item_Title = (TextView) view.findViewById(R.id.item_title);
        Item_Msg = (TextView) view.findViewById(R.id.item_msg);
        Hnad_img = (CircleImageView) view.findViewById(R.id.hand_img);

    }
}
