package com.jack.fo.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class AliPayOrderBack implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8827785274484143391L;
	private String gmt_create;
	private String charset;
	private String seller_email;
	private String subject;
	private String sign;
	private String body;
	private String buyer_id;
	private BigDecimal invoice_amount;
	private String notify_id;
	private String fund_bill_list;
	private String notify_type;
	private String trade_status;
	private BigDecimal receipt_amount;
	private String app_id;
	private BigDecimal buyer_pay_amount;
	private String sign_type;
	private String seller_id;
	private String gmt_payment;
	private String notify_time;
	private String version;
	private String out_trade_no;
	private BigDecimal total_amount;
	private String trade_no;
	private String auth_app_id;
	private String buyer_logon_id;
	private BigDecimal point_amount;
	
	
	public String getGmt_create() {
		return gmt_create;
	}
	public void setGmt_create(String gmt_create) {
		this.gmt_create = gmt_create;
	}
	public String getSeller_email() {
		return seller_email;
	}
	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getBuyer_id() {
		return buyer_id;
	}
	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}
	public BigDecimal getInvoice_amount() {
		return invoice_amount;
	}
	public void setInvoice_amount(BigDecimal invoice_amount) {
		this.invoice_amount = invoice_amount;
	}
	public String getFund_bill_list() {
		return fund_bill_list;
	}
	public void setFund_bill_list(String fund_bill_list) {
		this.fund_bill_list = fund_bill_list;
	}
	public BigDecimal getReceipt_amount() {
		return receipt_amount;
	}
	public void setReceipt_amount(BigDecimal receipt_amount) {
		this.receipt_amount = receipt_amount;
	}
	public BigDecimal getBuyer_pay_amount() {
		return buyer_pay_amount;
	}
	public void setBuyer_pay_amount(BigDecimal buyer_pay_amount) {
		this.buyer_pay_amount = buyer_pay_amount;
	}
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public String getGmt_payment() {
		return gmt_payment;
	}
	public void setGmt_payment(String gmt_payment) {
		this.gmt_payment = gmt_payment;
	}
	public String getAuth_app_id() {
		return auth_app_id;
	}
	public void setAuth_app_id(String auth_app_id) {
		this.auth_app_id = auth_app_id;
	}
	public String getBuyer_logon_id() {
		return buyer_logon_id;
	}
	public void setBuyer_logon_id(String buyer_logon_id) {
		this.buyer_logon_id = buyer_logon_id;
	}
	public BigDecimal getPoint_amount() {
		return point_amount;
	}
	public void setPoint_amount(BigDecimal point_amount) {
		this.point_amount = point_amount;
	}
	public String getNotify_time() {
		return notify_time;
	}
	public void setNotify_time(String notify_time) {
		this.notify_time = notify_time;
	}
	public String getNotify_type() {
		return notify_type;
	}
	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}
	public String getNotify_id() {
		return notify_id;
	}
	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getTrade_status() {
		return trade_status;
	}
	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}
	
	
	public String toString() {
		return "sign="+getSign()+",trade_no="+getTrade_no()+",out_trade_no="+getOut_trade_no()+",total_amout="+getTotal_amount();
	}
	public BigDecimal getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(BigDecimal total_amount) {
		this.total_amount = total_amount;
	}
}
