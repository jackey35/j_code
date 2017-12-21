package com.jack.fo.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="app_boot_config")
public class AppBootConfig implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5124848636949248011L;
	@Id  
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id ;
	private String bootUrl;
	private int status;
	private int type;
	private String createDt;
	private String updateDt;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getBootUrl() {
		return bootUrl;
	}
	public void setBootUrl(String bootUrl) {
		this.bootUrl = bootUrl;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCreateDt() {
		return createDt;
	}
	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}
	public String getUpdateDt() {
		return updateDt;
	}
	public void setUpdateDt(String updateDt) {
		this.updateDt = updateDt;
	}
	
}
