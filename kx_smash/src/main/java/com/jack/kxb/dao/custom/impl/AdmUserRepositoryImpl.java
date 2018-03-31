package com.jack.kxb.dao.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.jack.kxb.dao.custom.AdmUserRepositoryCustom;
import com.jack.kxb.model.AdmUser;


public class AdmUserRepositoryImpl implements AdmUserRepositoryCustom {
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public AdmUser getAdmUserByUserNamePass(String userName, String pass) {
		Query query = entityManager.createNativeQuery("select id,user_name,password,create_dt,update_dt "
				+ "from adm_user where user_name='"+userName+"' and password='"+pass+"'",AdmUser.class);
		
		List<AdmUser>  list = (List<AdmUser>)query.getResultList();
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		 return null;
	}

}
