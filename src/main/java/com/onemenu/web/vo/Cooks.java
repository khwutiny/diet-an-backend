package com.onemenu.web.vo;

import java.util.List;

import com.onemenu.entity.CookEntity;

public class Cooks {

	private String name;
	private String type;
	private List<CookEntity> foods;
	
	
	public Cooks() {
		this.type = "-1";
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CookEntity> getFoods() {
		return foods;
	}

	public void setFoods(List<CookEntity> foods) {
		this.foods = foods;
	}
	
}
