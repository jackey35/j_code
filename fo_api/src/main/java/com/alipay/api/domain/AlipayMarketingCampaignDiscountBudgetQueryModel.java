package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 营销立减活动预算查询
 *
 * @author auto create
 * @since 1.0, 2017-11-17 11:19:07
 */
public class AlipayMarketingCampaignDiscountBudgetQueryModel extends AlipayObject {

	private static final long serialVersionUID = 5138815932672862181L;

	/**
	 * 预算名称
	 */
	@ApiField("budget_id")
	private String budgetId;

	public String getBudgetId() {
		return this.budgetId;
	}
	public void setBudgetId(String budgetId) {
		this.budgetId = budgetId;
	}

}
