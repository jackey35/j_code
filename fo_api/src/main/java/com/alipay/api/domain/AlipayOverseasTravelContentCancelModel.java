package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 飞猪内容对接内容撤回接口
 *
 * @author auto create
 * @since 1.0, 2017-11-29 17:15:51
 */
public class AlipayOverseasTravelContentCancelModel extends AlipayObject {

	private static final long serialVersionUID = 1861181448493697387L;

	/**
	 * 飞猪内容id
	 */
	@ApiField("content_id")
	private String contentId;

	/**
	 * 修改时间
	 */
	@ApiField("modified_date")
	private Long modifiedDate;

	public String getContentId() {
		return this.contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public Long getModifiedDate() {
		return this.modifiedDate;
	}
	public void setModifiedDate(Long modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
