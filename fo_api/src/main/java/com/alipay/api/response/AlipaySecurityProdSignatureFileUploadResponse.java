package com.alipay.api.response;

import com.alipay.api.internal.mapping.ApiField;

import com.alipay.api.AlipayResponse;

/**
 * ALIPAY API: alipay.security.prod.signature.file.upload response.
 * 
 * @author auto create
 * @since 1.0, 2017-09-20 10:18:26
 */
public class AlipaySecurityProdSignatureFileUploadResponse extends AlipayResponse {

	private static final long serialVersionUID = 1318437245825612238L;

	/** 
	 * 文件唯一标识，用于apply接口传入
	 */
	@ApiField("oss_file_id")
	private String ossFileId;

	public void setOssFileId(String ossFileId) {
		this.ossFileId = ossFileId;
	}
	public String getOssFileId( ) {
		return this.ossFileId;
	}

}
