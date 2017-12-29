package com.jack.fo.dao.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.jack.fo.dao.custom.AppShareConfigRepositoryCustom;
import com.jack.fo.model.AppShareConfig;

public class AppShareConfigRepositoryImpl implements AppShareConfigRepositoryCustom {
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public AppShareConfig getAppShareConfigByType(int type) {
		Query query = entityManager.createNativeQuery("select id,title,sub_title,download_url,icon,status,type"
				+ ",create_dt,update_dt "
				+ "from app_share_config where status=1 and type='"+type+"' order by id desc limit 0,2",AppShareConfig.class);
		
		List<AppShareConfig>  list = (List<AppShareConfig>)query.getResultList();
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		 return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppShareConfig> getAppShareConfigListByType(int type, int start,int limit) {
		Query query = entityManager.createNativeQuery("select id,title,sub_title,download_url,icon,status,type," + 
				"create_dt,update_dt" + 
				" from app_share_config order by id desc limit "+start+","+limit,
				AppShareConfig.class);
		
		return (List<AppShareConfig>)query.getResultList();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int cntAppShareConfigListByType(int type) {
		Query query = entityManager.createNativeQuery("select count(1) "
				+ "from app_share_config ");
		List list = query.getResultList();
		if(list != null && list.size() > 0) {
			return Integer.valueOf(list.get(0).toString());
		}
		
		return 0;
	}

}
