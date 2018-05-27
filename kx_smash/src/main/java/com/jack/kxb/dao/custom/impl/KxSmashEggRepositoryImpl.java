package com.jack.kxb.dao.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.jack.kxb.dao.custom.KxSmashEggRepositoryCustom;
import com.jack.kxb.model.KxSmashEgg;

public class KxSmashEggRepositoryImpl implements KxSmashEggRepositoryCustom {
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	@SuppressWarnings("unchecked")
	@Override
	public Integer cntKxSmashEggListByOpenIdAndSmashDt(String openId, String smashDt) {
		try {
			Query query = entityManager.createNativeQuery("select id,open_id,status,win_level,smash_dt,create_dt "
					+ "from kx_smash_egg where open_id='"+openId+"' and smash_dt='"+smashDt+"'",KxSmashEgg.class);
			
			List<KxSmashEgg>  list = (List<KxSmashEgg>)query.getResultList();
			if(list != null && list.size() > 0) {
				return list.size();
			}
			
			return 0;
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Integer cntKxSmashEggListByOpenIdAndWinLevel(String openId, int winLevel) {
		try {
			Query query = entityManager.createNativeQuery("select id,open_id,status,win_level,smash_dt,create_dt "
					+ "from kx_smash_egg where open_id='"+openId+"' and status = 1 and win_level="+winLevel,KxSmashEgg.class);
			
			List<KxSmashEgg>  list = (List<KxSmashEgg>)query.getResultList();
			if(list != null && list.size() > 0) {
				return list.size();
			}
			
			return 0;
		}catch(Exception e) {
			e.printStackTrace();
			return 10000;
		}
	}

}
