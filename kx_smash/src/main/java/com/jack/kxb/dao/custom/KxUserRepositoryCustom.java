package com.jack.kxb.dao.custom;

import com.jack.kxb.model.KxUser;

public interface KxUserRepositoryCustom {
	public KxUser getKxUserByOpenId(String openId);
}
