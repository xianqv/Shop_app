package com.example.administrator.igoushop_app_test.pojos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Product implements Serializable {
	// 主键id
	private Integer id;
	// 商品名称
	private String name;
	// 销售数量
	private int saleNum;
	// 上市时间
	private String marketDate;
	// 商品型号
	private String model;
	// 商品价格
	private double price;
	// 产地
	private String address;
	// 是否在售 0 未售 1 在售
	private int isSale;
	// 商品与尺寸是一对多关系 1-N
	private Set<Size> sizes = new HashSet<>();
	// 商品的展示图片
	private String imgUrl;

	// 商品与品牌是多对一关系 N - 1
	private Brand brands;
	// 与商品属性是一对多关系 1-n
	private Attribute attribute;
	// 商品与商品图片是 1-n 关系
	private Set<ProductImages> images = new HashSet<>();
	// 与商品分类是多对一关系 n-1
	private ProductType type;

	public Product() {

	}

	public int getIsSale() {
		return isSale;
	}

	public void setIsSale(int isSale) {
		this.isSale = isSale;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getMarketDate() {
		return marketDate;
	}

	public void setMarketDate(String marketDate) {
		this.marketDate = marketDate;
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

	public int getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(int saleNum) {
		this.saleNum = saleNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<Size> getSizes() {
		return sizes;
	}

	public void setSizes(Set<Size> sizes) {
		this.sizes = sizes;
	}

	public Brand getBrands() {
		return brands;
	}

	public void setBrands(Brand brands) {
		this.brands = brands;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public Set<ProductImages> getImages() {
		return images;
	}

	public void setImages(Set<ProductImages> images) {
		this.images = images;
	}

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Product{" +
				"id=" + id +
				", name='" + name + '\'' +
				", saleNum=" + saleNum +
				", marketDate='" + marketDate + '\'' +
				", model='" + model + '\'' +
				", price=" + price +
				", address='" + address + '\'' +
				", isSale=" + isSale +
				", sizes=" + sizes +
				", imgUrl='" + imgUrl + '\'' +
				", brands=" + brands +
				", attribute=" + attribute +
				", images=" + images +
				", type=" + type +
				'}';
	}
}
