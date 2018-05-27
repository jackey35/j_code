package com.jack.kxb.model;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="kx_winning")
public class KxWinning {
	private Long id;
	private String name;
	private String phone;
	private String address;
	private String openId;
	private Long winId;
	private String createDt;
	private int winLevel;
	private String sdt;
	private String edt;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getCreateDt() {
		return createDt;
	}
	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}
	public Long getWinId() {
		return winId;
	}
	public void setWinId(Long winId) {
		this.winId = winId;
	}
	
	public int getWinLevel() {
		return winLevel;
	}
	public void setWinLevel(int winLevel) {
		this.winLevel = winLevel;
	}
	
	@Transient
	public String getSdt() {
		return sdt;
	}
	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
	
	@Transient
	public String getEdt() {
		return edt;
	}
	public void setEdt(String edt) {
		this.edt = edt;
	}
	
	
}
