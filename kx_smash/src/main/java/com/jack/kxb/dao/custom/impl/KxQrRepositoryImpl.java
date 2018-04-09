package com.jack.kxb.dao.custom.impl;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.jack.kxb.dao.custom.KxQrRepositoryCustom;
import com.jack.kxb.model.KxQr;

public class KxQrRepositoryImpl implements KxQrRepositoryCustom {
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public KxQr getKxQrByStatusLimit() {
		Query query = entityManager.createNativeQuery("select id,qr_url,open_id,status,create_dt "
				+ "from kx_qr where status=0 order by id desc limit 0,1",KxQr.class);
		
		List<KxQr>  list = (List<KxQr>)query.getResultList();
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		 return null;
	}

}
