package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 查询场景下内容列表信息
 *
 * @author auto create
 * @since 1.0, 2017-10-30 10:40:49
 */
public class AlipaySocialBaseSceneContentQueryModel extends AlipayObject {

	private static final long serialVersionUID = 7176329522119947835L;

	/**
	 * 内容中台提供的运营后台配置场景id
	 */
	@ApiField("scene_id")
	private String sceneId;

	/**
	 * 返回文章列表的个数，目前最多10条
	 */
	@ApiField("top_size")
	private Long topSize;

	/**
	 * 蚂蚁统一会员ID
	 */
	@ApiField("user_id")
	private String userId;

	public String getSceneId() {
		return this.sceneId;
	}
	public void setSceneId(String sceneId) {
		this.sceneId = sceneId;
	}

	public Long getTopSize() {
		return this.topSize;
	}
	public void setTopSize(Long topSize) {
		this.topSize = topSize;
	}

	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
