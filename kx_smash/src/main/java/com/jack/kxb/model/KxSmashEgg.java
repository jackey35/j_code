package com.jack.kxb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="kx_smash_egg")
public class KxSmashEgg {
	private Long id;
	private String openId;
	private int status;
	private int winLevel;
	private String smashDt;
	private String createDt;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getWinLevel() {
		return winLevel;
	}
	public void setWinLevel(int winLevel) {
		this.winLevel = winLevel;
	}
	public String getSmashDt() {
		return smashDt;
	}
	public void setSmashDt(String smashDt) {
		this.smashDt = smashDt;
	}
	public String getCreateDt() {
		return createDt;
	}
	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}
	
	
}
