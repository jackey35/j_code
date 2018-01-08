package com.jack.fo.dao.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.jack.fo.dao.custom.AppBootConfigRepositoryCustom;
import com.jack.fo.model.AppBootConfig;

public class AppBootConfigRepositoryImpl implements AppBootConfigRepositoryCustom {
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	@SuppressWarnings("unchecked")
	@Override
	public List<AppBootConfig> getAppBootConfigBy(int type,boolean flag) {
		Query query = entityManager.createNativeQuery("select id,boot_url,status,type,create_dt,update_dt,boot_pay "
				+ "from app_boot_config where type="+type+(flag?" and status=1":"")+" order by id desc limit 0,30",AppBootConfig.class);
		
		return (List<AppBootConfig>)query.getResultList();
	}

}
