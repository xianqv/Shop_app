package com.example.administrator.igoushop_app_test.pojos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;



public class ProductClassify implements Serializable {
	private Integer id;
	private String title;
	private String textColor;
	private Set<Product> products = new HashSet<Product>();

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

}
