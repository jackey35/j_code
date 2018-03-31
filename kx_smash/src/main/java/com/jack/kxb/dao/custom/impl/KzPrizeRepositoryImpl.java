package com.jack.kxb.dao.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.jack.kxb.dao.custom.KxPrizeRepositoryCustom;
import com.jack.kxb.model.KxPrize;

public class KzPrizeRepositoryImpl implements KxPrizeRepositoryCustom {
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public KxPrize getKxPrizeByPrizeLevel(int winLevel) {
		Query query = entityManager.createNativeQuery("select id,prize_name,prize_cnt,prize_weight,prize_level,create_dt,update_dt "
				+ "from kx_prize where prize_level="+winLevel,KxPrize.class);
		
		List<KxPrize>  list = (List<KxPrize>)query.getResultList();
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		 return null;
	}

}
