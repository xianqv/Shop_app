package com.example.administrator.igoushop_app_test.pojos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ProductType implements Serializable {
	// 主键ID
	private Integer id;
	// 一级分类名称
	private String name;
	// 与商品分类是多对一关系 n-1
	private Set<Product> products = new HashSet<>();

	public ProductType() {







//	 products=[Product{id=4, name='美特斯邦威短袖T恤 2016夏季 男士纯棉印花字母图案休闲T恤', saleNum=10, marketDate='null', model='null', price=69.0, address='null', isSale=0, sizes=[], imgUrl='productShowImg/aaa.jpg', brands=null, attribute=null, images=[], type=null}, Product{id=11, name='的实打实大声道', saleNum=54, marketDate='null', model='null', price=232.0, address='null', isSale=0, sizes=[], imgUrl='productShowImg/pic3.png', brands=null, attribute=null, images=[], type=null}, \
// Product{id=8, name='大大说的大青蛙', saleNum=12, marketDate='null', model='null', price=1212.0, address='null', isSale=0, sizes=[], imgUrl='productShowImg/pic3.png', brands=null, attribute=null, images=[], type=null}, Product{id=10, name='的实打实大声道', saleNum=43, marketDate='null', model='null', price=121.0, address='null', isSale=0, sizes=[], imgUrl='productShowImg/pic2.png', brands=null, attribute=null, images=[], type=null}, Product{id=7, name='大大 大师大师大师', saleNum=12, marketDate='null', model='null', price=122.0, address='null', isSale=0, sizes=[], imgUrl='productShowImg/pic5.png', brands=null, attribute=null, images=[], type=null}, Product{id=12, name='阿打算打算', saleNum=0, marketDate='null', model='null', price=121.0, address='null', isSale=0, sizes=[], imgUrl='productShowImg/006.jpg', brands=null, attribute=null, images=[], type=null}]}







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

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "ProductType{" +
				"id=" + id +
				", name='" + name + '\'' +
				", products=" + products +
				'}';
	}
}
