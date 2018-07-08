package com.onemenu.entity;


import java.util.List;

public class CookEntity  {

	private int cookId;
	private String name;
	private String cookType;
	private String description;
	private String Info;
    private String icon;
	private String image;
    private int count;
    private List<MaterialEntity> cookMaterial;

	public CookEntity() {
	}

	public String getInfo() {
		return Info;
	}

	public void seInfo(String info) {
		this.Info = info;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String cookIcon) {
		this.icon = cookIcon;
	}
	
	
	public int getCookId() {
		return cookId;
	}

	public void setCookId(int cookId) {
		this.cookId = cookId;
	}

	public String getName() {
		return name;
	}

	public void setName(String cookName) {
		this.name = cookName;
	}

	public String getCookType() {
		return cookType;
	}

	public void setCookType(String cookType) {
		this.cookType = cookType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String cookDesc) {
		this.description = cookDesc;
	}

	public String getImg() {
		return image;
	}

	public void setImg(String cookImg) {
		this.image = cookImg;
	}

    public List<MaterialEntity> getCookMaterial() {
        return cookMaterial;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCookMaterial(List<MaterialEntity> cookMaterial) {
        this.cookMaterial = cookMaterial;
    }
//	@Override
//	public String toString() {
//		// TODO Auto-generated method stub
////		return "cookId :" + this.cookId 
////				    +  ", cookName: " + this.cookName 
////				    + ",cookType: " + this.cookType 
////				    + ",cookDesc: " + this.cookDesc 
////				    + "cookImg: " + this.cookImg;
//	}

}