package com.example.administrator.igoushop_app_test.pojos;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-08-28.
 */
public class Cart  implements Serializable {
//    ID User对象 尺码，颜色 数量 ，总价
    private Integer id;
    private UserInfo user;
    private String size;
    private String color;
    private int num;
    private double totalPrice;
    private int userId;
    private int proId;
    private Product product;
    private Integer userinfo_id;

    public Integer getUserinfo_id() {
        return userinfo_id;
    }

    public void setUserinfo_id(Integer userinfo_id) {
        this.userinfo_id = userinfo_id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", user=" + user +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", num=" + num +
                ", totalPrice=" + totalPrice +
                ", userId=" + userId +
                ", proId=" + proId +
                '}';
    }
}
