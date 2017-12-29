package com.jack.fo.dao.custom;


import java.util.List;

import com.jack.fo.model.AppUser;

public interface AppUserRepositoryCustom {
	public AppUser getAppUserByOpenId(String openId);
	public List<AppUser> getAppUserListByCond(AppUser user,String startDt,String endDt,int start,int limit);
	public int cntAppUserListByCond(AppUser user,String startDt,String endDt);
	public AppUser getAppUserByUserId(long userId);
}
