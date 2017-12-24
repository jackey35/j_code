package com.jack.fo.dao.custom.impl;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jack.fo.dao.custom.AppUserRepositoryCustom;
import com.jack.fo.model.AppUser;

public class AppUserRepositoryImpl implements AppUserRepositoryCustom {
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public AppUser getAppUserByOpenId(String openId) {
		Query query = entityManager.createNativeQuery("select id,nick_name,head_url,open_id,reg_time,reg_channel,type,"
				+ "reg_ip,create_dt,update_dt "
				+ "from app_user where open_id='"+openId+"' order by id desc",AppUser.class);
		
		List<AppUser>  list = (List<AppUser>)query.getResultList();
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		 return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppUser> getAppUserListByCond(AppUser user,String startDt,String endDt, int start, int limit) {
		String cond = "1 = 1 ";
		if(!StringUtils.isEmpty(user.getNickName())) {
			cond = cond + " and nick_name='"+user.getNickName()+"'";
		}
		if(user.getRegChannel()!=0) {
			cond = cond + " and reg_channel="+user.getRegChannel();
		}
		if(!StringUtils.isEmpty(startDt)) {
			cond = cond + " and reg_time>='"+startDt+" 00:00:00'" ;
		}
		if(!StringUtils.isEmpty(endDt)) {
			cond = cond + " and reg_time<'"+endDt+" 23:59:59'" ;
		}
		
		Query query = entityManager.createNativeQuery("select id,nick_name,head_url,open_id,reg_time,reg_channel,type,"
				+ "reg_ip,create_dt,update_dt "
				+ "from app_user where "+cond+" order by id desc limit "+start+" ,"+limit,AppUser.class);
		
		return (List<AppUser>)query.getResultList();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int cntAppUserListByCond(AppUser user,String startDt,String endDt) {
		String cond = "1 = 1 ";
		if(!StringUtils.isEmpty(user.getNickName())) {
			cond = cond + " and nick_name='"+user.getNickName()+"'";
		}
		if(user.getRegChannel()!=0) {
			cond = cond + " and reg_channel="+user.getRegChannel();
		}
		if(!StringUtils.isEmpty(startDt)) {
			cond = cond + " and reg_time>='"+startDt+" 00:00:00'" ;
		}
		if(!StringUtils.isEmpty(endDt)) {
			cond = cond + " and reg_time<'"+endDt+" 23:59:59'" ;
		}
		Query query = entityManager.createNativeQuery("select count(1)"
				+ " from app_user where "+cond);
		
		List list = query.getResultList();
		if(list != null && list.size() > 0) {
			return Integer.valueOf(list.get(0).toString());
		}
		
		return 0;
	}

}
