package com.jack.fo.model;

import java.util.ArrayList;
import java.util.List;
public class PayUser {
	public static List<PayUser> list = new ArrayList<PayUser>();
	
	private String userName;
	private int payMoney;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(int payMoney) {
		this.payMoney = payMoney;
	}
	
}
