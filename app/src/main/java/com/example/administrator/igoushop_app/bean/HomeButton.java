package com.example.administrator.igoushop_app.bean;

/**
 * Created by Administrator on 2016-06-05.
 */
public class HomeButton {
    private int id;
    private String title;
    private String imageUrl;
    private String clickUrl;

    public HomeButton() {
    }

    public HomeButton(int id, String title, String imageUrl, String clickUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.clickUrl = clickUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }
}
