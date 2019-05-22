package com.example.administrator.igoushop_app_test.pojos;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/9.
 */

public class FEMOrder implements Serializable{
    private String name;
    private Integer proId;
    private String size;
    private String color;
    private String ImgUrl;
    private double onePrice;
    private int num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
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

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public double getOnePrice() {
        return onePrice;
    }

    public void setOnePrice(double onePrice) {
        this.onePrice = onePrice;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
