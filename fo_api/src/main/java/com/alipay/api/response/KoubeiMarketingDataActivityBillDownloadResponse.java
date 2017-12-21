package com.alipay.api.response;

import com.alipay.api.internal.mapping.ApiField;

import com.alipay.api.AlipayResponse;

/**
 * ALIPAY API: koubei.marketing.data.activity.bill.download response.
 * 
 * @author auto create
 * @since 1.0, 2017-09-29 15:08:57
 */
public class KoubeiMarketingDataActivityBillDownloadResponse extends AlipayResponse {

	private static final long serialVersionUID = 5396499242489122763L;

	/** 
	 * 账单下载地址
	 */
	@ApiField("url")
	private String url;

	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl( ) {
		return this.url;
	}

}
