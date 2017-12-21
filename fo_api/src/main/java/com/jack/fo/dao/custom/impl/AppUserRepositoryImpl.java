package com.jack.fo.dao.custom.impl;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

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

}
