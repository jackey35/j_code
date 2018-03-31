package com.jack.kxb.dao.custom;

import com.jack.kxb.model.KxShare;

public interface KxShareRepositoryCustom {
	public KxShare getKxShareByOpenIdAndShareDt(String openId,String shareDt);
}
