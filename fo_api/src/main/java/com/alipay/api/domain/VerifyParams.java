package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 签约校验信息
 *
 * @author auto create
 * @since 1.0, 2017-07-11 14:31:50
 */
public class VerifyParams extends AlipayObject {

	private static final long serialVersionUID = 5515791379165561924L;

	/**
	 * 用户证件号后4位
	 */
	@ApiField("cert_no")
	private String certNo;

	public String getCertNo() {
		return this.certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

}
