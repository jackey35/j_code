package com.alipay.api.response;

import com.alipay.api.internal.mapping.ApiField;
import com.alipay.api.domain.IntelligentPromo;

import com.alipay.api.AlipayResponse;

/**
 * ALIPAY API: koubei.marketing.campaign.intelligent.promo.query response.
 * 
 * @author auto create
 * @since 1.0, 2017-11-22 18:56:53
 */
public class KoubeiMarketingCampaignIntelligentPromoQueryResponse extends AlipayResponse {

	private static final long serialVersionUID = 6844148626166225957L;

	/** 
	 * 查询返回的营销活动模型
	 */
	@ApiField("promo")
	private IntelligentPromo promo;

	public void setPromo(IntelligentPromo promo) {
		this.promo = promo;
	}
	public IntelligentPromo getPromo( ) {
		return this.promo;
	}

}
