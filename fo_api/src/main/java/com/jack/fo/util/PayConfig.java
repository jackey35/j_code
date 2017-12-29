package com.jack.fo.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "payconfig")  
@Component
public class PayConfig {
	private String aliGateway;
	private String aliAppId;
	private String aliPrivateKey;
	private String aliPublicKey;
	private String notifyUrl;
	private String wxAppId;
	private String wxNotifyUrl;
	private String wxMchId;
	private String wxPrivateKey;
	private String wxPreOrder;
	private String devVerify;
	private String prodVerify;
	private String filePath;
	
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getAliAppId() {
		return aliAppId;
	}
	public void setAliAppId(String aliAppId) {
		this.aliAppId = aliAppId;
	}
	public String getAliPrivateKey() {
		return aliPrivateKey;
	}
	public void setAliPrivateKey(String aliPrivateKey) {
		this.aliPrivateKey = aliPrivateKey;
	}
	public String getAliPublicKey() {
		return aliPublicKey;
	}
	public void setAliPublicKey(String aliPublicKey) {
		this.aliPublicKey = aliPublicKey;
	}
	public String getWxAppId() {
		return wxAppId;
	}
	public void setWxAppId(String wxAppId) {
		this.wxAppId = wxAppId;
	}
	public String getWxNotifyUrl() {
		return wxNotifyUrl;
	}
	public void setWxNotifyUrl(String wxNotifyUrl) {
		this.wxNotifyUrl = wxNotifyUrl;
	}
	public String getWxMchId() {
		return wxMchId;
	}
	public void setWxMchId(String wxMchId) {
		this.wxMchId = wxMchId;
	}
	public String getWxPrivateKey() {
		return wxPrivateKey;
	}
	public void setWxPrivateKey(String wxPrivateKey) {
		this.wxPrivateKey = wxPrivateKey;
	}
	public String getWxPreOrder() {
		return wxPreOrder;
	}
	public void setWxPreOrder(String wxPreOrder) {
		this.wxPreOrder = wxPreOrder;
	}
	public String getAliGateway() {
		return aliGateway;
	}
	public void setAliGateway(String aliGateway) {
		this.aliGateway = aliGateway;
	}
	public String getDevVerify() {
		return devVerify;
	}
	public void setDevVerify(String devVerify) {
		this.devVerify = devVerify;
	}
	public String getProdVerify() {
		return prodVerify;
	}
	public void setProdVerify(String prodVerify) {
		this.prodVerify = prodVerify;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
