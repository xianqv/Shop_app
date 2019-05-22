package com.example.administrator.igoushop_app_test.pojos;

import java.io.Serializable;

public class Attribute implements Serializable {
	// 生成主键ID
	private Integer id;
	// 材质
	private String material;
	// 款式
	private String style;
	// 适用对象
	private String userObject;
	// 适用季节
	private String season;
	// 描述
	private String describle;
	// CUP型号
	private String cupModel;
	// 屏幕尺寸
	private String screenSize;
	// 操作系统
	private String operatingSystem;
	// 内存说明
	private String memory;
	// 屏幕分辨率
	private String screenResolution;
	// 附加说明
	private String additional;

	public Attribute() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getUserObject() {
		return userObject;
	}

	public void setUserObject(String userObject) {
		this.userObject = userObject;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getDescrible() {
		return describle;
	}

	public void setDescrible(String describle) {
		this.describle = describle;
	}

	public String getCupModel() {
		return cupModel;
	}

	public void setCupModel(String cupModel) {
		this.cupModel = cupModel;
	}

	public String getScreenSize() {
		return screenSize;
	}

	public void setScreenSize(String screenSize) {
		this.screenSize = screenSize;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getScreenResolution() {
		return screenResolution;
	}

	public void setScreenResolution(String screenResolution) {
		this.screenResolution = screenResolution;
	}

	public String getAdditional() {
		return additional;
	}

	public void setAdditional(String additional) {
		this.additional = additional;
	}

}
