package com.jack.fo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name = "app_catecode")
public class AppCateCode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7933591201117771501L;
	@Id  
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private int cateCode;
	private String cateName;
	private String cateIcon;
	@Column(name = "parent_code")
	private int parentCateCode;
	private String createDt;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getCateCode() {
		return cateCode;
	}
	public void setCateCode(int cateCode) {
		this.cateCode = cateCode;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public String getCateIcon() {
		return cateIcon;
	}
	public void setCateIcon(String cateIcon) {
		this.cateIcon = cateIcon;
	}
	public int getParentCateCode() {
		return parentCateCode;
	}
	public void setParentCateCode(int parentCateCode) {
		this.parentCateCode = parentCateCode;
	}
	public String getCreateDt() {
		return createDt;
	}
	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}
	
	
}
