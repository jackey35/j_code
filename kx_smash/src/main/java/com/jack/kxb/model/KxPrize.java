package com.jack.kxb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="kx_prize")
public class KxPrize {
	private long id;
	private String prizeName;
	private int prizeCnt;
	private int prizeWeight;
	private int prizeLevel;
	private String createDt;
	private String updateDt;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPrizeName() {
		return prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	public int getPrizeCnt() {
		return prizeCnt;
	}
	public void setPrizeCnt(int prizeCnt) {
		this.prizeCnt = prizeCnt;
	}
	public int getPrizeWeight() {
		return prizeWeight;
	}
	public void setPrizeWeight(int prizeWeight) {
		this.prizeWeight = prizeWeight;
	}
	public String getCreateDt() {
		return createDt;
	}
	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}
	
	public int getPrizeLevel() {
		return prizeLevel;
	}
	public void setPrizeLevel(int prizeLevel) {
		this.prizeLevel = prizeLevel;
	}
	public String getUpdateDt() {
		return updateDt;
	}
	public void setUpdateDt(String updateDt) {
		this.updateDt = updateDt;
	}
	
}
