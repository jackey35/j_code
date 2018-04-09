package com.jack.kxb.dao.custom.impl;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.jack.kxb.dao.custom.KxUserRepositoryCustom;
import com.jack.kxb.model.KxUser;

public class KxUserRepositoryImpl implements KxUserRepositoryCustom {
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public KxUser getKxUserByOpenId(String openId) {
		Query query = entityManager.createNativeQuery("select id,open_id,nick_name,head_img,create_dt "
				+ "from kx_user where open_id='"+openId+"'",KxUser.class);
		
		List<KxUser>  list = (List<KxUser>)query.getResultList();
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		 return null;
	}
}
