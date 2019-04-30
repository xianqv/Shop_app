package com.example.administrator.igoushop_app.pojos;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-08-28.
 */
public class Address  implements Serializable {

//    ID ,name 手机号 省市区街道，邮政编码 ， 详细地址 是否默认（1/0）  User对象
    private Integer id;
    private String name;
    private String phone;
    //详细地址
    private String detail;
    //邮政编码
    private String postalcode;
    //是否为默认地址 1 是 0 不是
    private int isDefault = 1;
    private UserInfo user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public UserInfo getUser() {

        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", detail='" + detail + '\'' +
                ", postalcode='" + postalcode + '\'' +
                ", isDefault=" + isDefault +
                ", user=" + user +
                '}';
    }
}
