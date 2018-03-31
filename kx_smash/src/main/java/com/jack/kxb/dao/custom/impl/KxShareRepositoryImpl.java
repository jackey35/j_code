package com.jack.kxb.dao.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.jack.kxb.dao.custom.KxShareRepositoryCustom;
import com.jack.kxb.model.KxShare;

public class KxShareRepositoryImpl implements KxShareRepositoryCustom {
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public KxShare getKxShareByOpenIdAndShareDt(String openId, String shareDt) {
		Query query = entityManager.createNativeQuery("select id,open_id,share_dt,create_dt "
				+ "from kx_share where open_id='"+openId+"' and share_dt='"+shareDt+"'",KxShare.class);
		
		List<KxShare>  list = (List<KxShare>)query.getResultList();
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		 return null;
	}

}
