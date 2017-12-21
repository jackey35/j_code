package com.jack.fo.dao.custom.impl;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.jack.fo.dao.custom.AppWishRepositoryCustom;
import com.jack.fo.model.AppWish;

public class AppWishRepositoryImpl implements AppWishRepositoryCustom {
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AppWish> getAppWishByUid(long uid) {
		Query query = entityManager.createNativeQuery("select id,user_id,wish,back_wish,status, channel,create_dt,update_dt "
				+ "from app_wish where user_id="+uid+" order by id desc",AppWish.class);
		
		return (List<AppWish>)query.getResultList();
	}

}
