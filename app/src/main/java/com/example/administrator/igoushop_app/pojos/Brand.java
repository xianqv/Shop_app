package com.example.administrator.igoushop_app.pojos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Brand implements Serializable {
	// 主键ID
	private Integer id;
	// 商品名称
	private String name;
	// 品牌广告图片 URL
	private String imgUrl;
	// 是否名牌标识 0 不是 1 是
	private int isbig;
	// 商品与品牌是多对一关系 N - 1
	private Set<Product> products = new HashSet<>();

	public Brand() {
	}

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

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public int getIsbig() {
		return isbig;
	}

	public void setIsbig(int isbig) {
		this.isbig = isbig;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

}
