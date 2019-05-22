package com.example.administrator.igoushop_app_test.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.example.administrator.igoushop_app_test.Myapplication;
import com.example.administrator.igoushop_app_test.R;
import com.example.administrator.igoushop_app_test.http.OKManager;
import com.example.administrator.igoushop_app_test.pojos.Address;

import java.util.List;

/**
 * Created by Administrator on 2016-08-31.
 */
public class AddressAdapter extends SimpleAdapter<Address> {
    private OKManager manager;
    private static List<Address> addresses;
    private Context context;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0x123){
                initView((Address)msg.obj);
            }
        }
    };
    public onRadioButtonClickListener listener;
    public onButtonClickListener buttonClickListener;
    public void setOnBUttonListener(onButtonClickListener listener){
        this.buttonClickListener = listener;
    }
    public void setonRadioButtonClickListener(onRadioButtonClickListener listener){
        this.listener = listener;
    }

    public AddressAdapter(Context context, int layoutResId, List<Address> datas) {
        super(context, layoutResId, datas);
        this.addresses  = datas;
        this.context = context;
    }

    private void initView(Address address) {
        addresses.remove(address);
        notifyDataSetChanged();
    }
    @Override
    protected void convert(BaseViewHolder viewHoder, final Address item) {
        viewHoder.getTextView(R.id.name).setText(item.getName());
        viewHoder.getTextView(R.id.phone).setText(item.getPhone());
        viewHoder.getTextView(R.id.address).setText(item.getDetail());
        final RadioButton radioButton = viewHoder.getRadioButton(R.id.isdefaults);
//        radioButton.setChecked(item.getIsDefault() == 1 ? true : false);
        if (item.getIsDefault() == 1){
            radioButton.setChecked(true);
        }else {
            radioButton.setChecked(false);
        }
        viewHoder.getTextView(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        buttonClickListener.onClick(item.getId(),Myapplication.userInfo.getId(),1);

            }
        });
        viewHoder.getTextView(R.id.modify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClickListener.onClick(item.getId(),Myapplication.userInfo.getId(),2);
            }
        });

        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    listener.onClick(item.getId(), Myapplication.userInfo.getId());
                }
            }
        });


    }
    public static Boolean updateAddress(Address address){
        boolean isOK = false;
        for (Address address1 :addresses){
            if(address.getId() == address1.getId()){
                addresses.remove(address1);
                addresses.add(address);
                isOK = true;
                continue;
            }
        }
        return isOK;
    }

    public interface onRadioButtonClickListener{
        void onClick(int id,int userId);
    }
    public interface onButtonClickListener{
        void onClick(int id,int userId,int flag);
    }
    public void setDate(List<Address> addresses){
        this.datas = addresses;
        notifyDataSetChanged();
    }
    public Address getDateById(int id){
        for (Address address:datas){
            if(address.getId() == id)
            return address;
        }
        return null;
    }

}
