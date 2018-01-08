package com.jack.fo.dao.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jack.fo.dao.custom.AppOrderRepositoryCustom;
import com.jack.fo.model.AppOrder;

public class AppOrderRepositoryImpl implements AppOrderRepositoryCustom {
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public AppOrder getAppOrderByOrderNo(String orderNo) {
		Query query = entityManager.createNativeQuery("select id,order_no,user_id,pid,p_name,order_price,order_type,order_channel,"
				+ "status, create_dt,pay_type,pay_dt,out_order_no,update_dt "
				+ "from app_order where order_no="+orderNo,AppOrder.class);
		
		List<AppOrder> list = (List<AppOrder>)query.getResultList();
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppOrder> getAppOrderByUidType(long uid, int type, int start, int limit) {
		Query query = entityManager.createNativeQuery("select id,order_no,user_id,pid,p_name,order_price,order_type,order_channel,"
				+ "status, create_dt,pay_type,pay_dt,out_order_no,update_dt "
				+ "from app_order where status=1  and user_id="+uid+" and order_type="+type+" order by id desc limit "+start+","+limit,AppOrder.class);
		
		return (List<AppOrder>)query.getResultList();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int countAppOrderByUidType(long uid, int type) {
		Query query = entityManager.createNativeQuery("select count(1) "
				+ "from app_order where status=1  and user_id="+uid+" and order_type="+type);
		List list = query.getResultList();
		if(list != null && list.size() > 0) {
			return Integer.valueOf(list.get(0).toString());
		}
		
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppOrder> getAppOrderByStatusType(int type) {
		String str = "";
		if(type != 2) {
			str = " and order_type="+type;
		}
		Query query = entityManager.createNativeQuery("select id,order_no,user_id,pid,p_name,order_price,order_type,order_channel,"
				+ "pay_type,status, create_dt,pay_dt,out_order_no,update_dt "
				+ "from app_order where status=1 "+str+
				" order by id desc limit 0,40",AppOrder.class);
		
		return (List<AppOrder>)query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppOrder> getAppOrderByPname(String pName, String startDt, String endDt, int start, int limit) {
		String cond = "1 = 1 ";
		if(!StringUtils.isEmpty(pName)) {
			cond = cond + " and p_name='"+pName+"'";
		}
		
		if(!StringUtils.isEmpty(startDt)) {
			cond = cond + " and create_dt>='"+startDt+" 00:00:00'" ;
		}
		if(!StringUtils.isEmpty(endDt)) {
			cond = cond + " and create_dt<'"+endDt+" 23:59:59'" ;
		}
		
		Query query = entityManager.createNativeQuery("select id,order_no,user_id,pid,p_name,order_price,order_type,order_channel,"
				+ "status, create_dt,pay_type,pay_dt,out_order_no,update_dt "
				+ "from app_order where "+cond+" order by id desc limit "+start+","+limit,AppOrder.class);
		
		return (List<AppOrder>)query.getResultList();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int countAppOrderByPname(String pName, String startDt, String endDt) {
		String cond = "1 = 1 ";
		if(!StringUtils.isEmpty(pName)) {
			cond = cond + " and p_name='"+pName+"'";
		}
		
		if(!StringUtils.isEmpty(startDt)) {
			cond = cond + " and create_dt>='"+startDt+" 00:00:00'" ;
		}
		if(!StringUtils.isEmpty(endDt)) {
			cond = cond + " and create_dt<'"+endDt+" 23:59:59'" ;
		}
		
		Query query = entityManager.createNativeQuery("select count(1) "
				+ "from app_order where "+cond);
		List list = query.getResultList();
		if(list != null && list.size() > 0) {
			return Integer.valueOf(list.get(0).toString());
		}
		
		return 0;
	}

}
