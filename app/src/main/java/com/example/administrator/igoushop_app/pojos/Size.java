package com.example.administrator.igoushop_app.pojos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Size implements Serializable {
	// 主键ID
	private Integer id;
	// 尺寸的名称
	private String name;
	// 选中状态 0 未选中状态 1 选中状态
	private int state;
	// 尺寸与颜色是一对多关系 N-1
	private Set<Color> colors = new HashSet<>();
	// 商品与尺寸是一对多关系 1-N
	private Product product;

	public Size() {
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

	public int isState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Set<Color> getColors() {
		return colors;
	}

	public void setColors(Set<Color> colors) {
		this.colors = colors;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String show() {
		String mes = "";
		if (!(colors.size() == 0)) {
			for (Color c : colors) {
				mes = mes + c.getName() + " " + c.getNum() + ",";
			}
			System.out.println(mes);
		} else {
			mes = "";
		}
		return mes;
	}
}
