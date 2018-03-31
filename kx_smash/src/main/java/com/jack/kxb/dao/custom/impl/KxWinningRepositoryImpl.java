package com.jack.kxb.dao.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jack.kxb.dao.custom.KxWinningRepositoryCustom;
import com.jack.kxb.model.KxWinning;

public class KxWinningRepositoryImpl implements KxWinningRepositoryCustom {
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	@SuppressWarnings("unchecked")
	@Override
	public List<KxWinning> getKxWinningByCond(KxWinning kxWinning,int start,int size) {
		String cond = "1 = 1 ";
		if(!StringUtils.isEmpty(kxWinning.getPhone())) {
			cond = cond + " and phone='"+kxWinning.getPhone()+"'";
		}
		
		if(!StringUtils.isEmpty(kxWinning.getName())) {
			cond = cond + " and name like '%"+kxWinning.getName()+"%'";
		}
		
		
		Query query = entityManager.createNativeQuery("select id,name,phone,address,open_id,win_id,create_dt "
				+ "from kx_winning where "+cond+" limit "+start+","+size,KxWinning.class);
		
		List<KxWinning>  list = (List<KxWinning>)query.getResultList();
		if(list != null && list.size() > 0) {
			return list;
		}
		 return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public KxWinning getKxWinningByOpenIdAndWinId(String openId, Long winId) {
		Query query = entityManager.createNativeQuery("select id,name,phone,address,open_id,win_id,create_dt "
				+ "from kx_winning where open_id='"+openId+"' and win_id="+winId,KxWinning.class);
		
		List<KxWinning>  list = (List<KxWinning>)query.getResultList();
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		 return null;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public int cntKxWinningByCond(KxWinning kxWinning) {
		String cond = "1 = 1 ";
		if(!StringUtils.isEmpty(kxWinning.getPhone())) {
			cond = cond + " and phone='"+kxWinning.getPhone()+"'";
		}
		
		if(!StringUtils.isEmpty(kxWinning.getName())) {
			cond = cond + " and name like '%"+kxWinning.getName()+"%'";
		}
		
		Query query = entityManager.createNativeQuery("select count(1) "
				+ "from kx_winning where "+cond);
		List list = query.getResultList();
		if(list != null && list.size() > 0) {
			return Integer.valueOf(list.get(0).toString());
		}
		
		return 0;
	}

}
