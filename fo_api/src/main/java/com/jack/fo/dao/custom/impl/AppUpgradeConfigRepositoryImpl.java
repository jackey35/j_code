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

	@SuppressWarnings("unchecked")
	@Override
	public List<AppUpgradeConfig> getAppUpgradeConfigList(int start,int limit) {
		Query query = entityManager.createNativeQuery("select id,version_no,memo,download_url,type,status, channel,create_dt,update_dt "
				+ "from app_upgrade_config order by id desc limit "+start+","+limit,AppUpgradeConfig.class);
		
		return (List<AppUpgradeConfig>)query.getResultList();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int cntAppUpgradeConfig() {
		Query query = entityManager.createNativeQuery("select count(1) "
				+ "from app_upgrade_config ");
		List list = query.getResultList();
		if(list != null && list.size() > 0) {
			return Integer.valueOf(list.get(0).toString());
		}
		
		return 0;
	}

}
