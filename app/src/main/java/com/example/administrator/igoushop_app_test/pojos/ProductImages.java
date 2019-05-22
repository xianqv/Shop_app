package com.example.administrator.igoushop_app_test.pojos;

import java.io.Serializable;

public class ProductImages implements Serializable {
	// 主键ID
	private Integer id;
	// 商品的图片URl
	private String imageUrl;
	// 商品与商品图片是一对多关系 1-N
	private Product product;

	public ProductImages() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
