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
	public List<AppBootConfig> getAppBootConfigBy(int type) {
		Query query = entityManager.createNativeQuery("select id,boot_url,status,type,create_dt,update_dt "
				+ "from app_boot_config where status=1 and type="+type+" order by id desc",AppBootConfig.class);
		
		return (List<AppBootConfig>)query.getResultList();
	}

}
