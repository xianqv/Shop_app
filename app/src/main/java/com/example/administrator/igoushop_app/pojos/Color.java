package com.example.administrator.igoushop_app.pojos;

import java.io.Serializable;

public class Color implements Serializable {
	// 主键ID
	private Integer id;
	// 颜色名称
	private String name;
	// 选中状态
	private int state;
	// 该颜色的商品的数量
	private int num;
	// 尺寸与颜色是一对多关系 N-1
	private Size size;

	public Color() {
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
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

}
