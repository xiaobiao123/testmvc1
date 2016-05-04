package com.user.pojo;

import java.util.Date;

/**
 * 
 * 用户表
 */

public class User {

	private int id;

	private int state;

	private String nickname;
	private String name;
	
	private Date date=new Date();
	private boolean flag=false;
	public int getId() {

		return id;

	}

	public void setId(int id) {

		this.id = id;

	}

	public int getState() {

		return state;

	}

	public void setState(int state) {

		this.state = state;

	}

	public String getNickname() {

		return nickname;

	}

	public void setNickname(String nickname) {

		this.nickname = nickname;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
