package com.example.administrator.igoushop_app.pojos;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-08-28.
 */
public class Order implements Serializable {
//    时间 ID， User对象 ，尺寸 ，颜色， 数量 总价， 是否已收货（1/0） ，是否已发货（1/0），交易状态（1/0）
    private Integer id;
    private UserInfo user;
    private String size;
    private String color;
    private int num;
    private double totalPrice;
    //是否 已收货 1 是 0 没收货
    private int isGet = 0;
    //是否已发货 1 发货 0 未发货
    private int isDeliver = 0;
    //交易状态  1 已交易 0 未交易
    private int isDeal = 0;

    private Integer userId;
    private String userName;
    private String phone;
    private String address;
    private String proName;
    private double onePrice;
    private String imgURL;
    private Integer proId;

    private String msg;

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public double getOnePrice() {
        return onePrice;
    }

    public void setOnePrice(double onePrice) {
        this.onePrice = onePrice;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public int getIsGet() {
        return isGet;
    }

    public void setIsGet(int isGet) {
        this.isGet = isGet;
    }

    public int getIsDeliver() {
        return isDeliver;
    }

    public void setIsDeliver(int isDeliver) {
        this.isDeliver = isDeliver;
    }

    public int getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(int isDeal) {
        this.isDeal = isDeal;
    }
}
