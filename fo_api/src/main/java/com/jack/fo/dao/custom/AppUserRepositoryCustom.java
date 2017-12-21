package com.jack.fo.dao.custom;


import com.jack.fo.model.AppUser;

public interface AppUserRepositoryCustom {
	public AppUser getAppUserByOpenId(String openId);
}
