package com.onemenu.entity;

import java.io.Serializable;

import com.onemenu.entity.UserSexEnum;

public class UserEntity  {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String userName;
	private String passWord;

	public UserSexEnum getSex() {
		return sex;
	}

	public void setSex(UserSexEnum sex) {
		this.sex = sex;
	}

	private UserSexEnum sex;
	private String nickName;
	private String height;
	private String weight;
	private String birthday;
	private String groupId;
	private String creatuserid;

	public String getCreatuserid() {
		return creatuserid;
	}

	public void setCreatuserid(String creatuserid) {
		this.creatuserid = creatuserid;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public UserEntity() {
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}


	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
	
	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "userName " + this.userName + ", pasword " + this.passWord + "sex " + sex.name();
	}
}

