package com.jack.kxb.dao.custom;

public interface KxSmashEggRepositoryCustom {
	public Integer cntKxSmashEggListByOpenIdAndSmashDt(String openId,String smashDt);
	
	public Integer cntKxSmashEggListByOpenIdAndWinLevel(String openId,int winLevel);
}
