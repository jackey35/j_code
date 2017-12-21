package com.jack.fo.dao.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.jack.fo.dao.custom.AppUpgradeConfigRepositoryCustom;
import com.jack.fo.model.AppUpgradeConfig;

public class AppUpgradeConfigRepositoryImpl implements AppUpgradeConfigRepositoryCustom {
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AppUpgradeConfig> getAppUpgradeConfigByTypeChannel(int type, int channel) {
		Query query = entityManager.createNativeQuery("select id,version_no,memo,download_url,type,status, channel,create_dt,update_dt "
				+ "from app_upgrade_config where status=1 and type="+type+" and channel="+channel+" order by id desc",AppUpgradeConfig.class);
		
		return (List<AppUpgradeConfig>)query.getResultList();
	}

}
