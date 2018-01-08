package com.jack.fo.dao.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.jack.fo.dao.custom.AppProductRepositoryCustom;
import com.jack.fo.model.AppProduct;

public class AppProductRepositoryImpl implements AppProductRepositoryCustom {
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AppProduct> getAppProductByCateCode(int cateCode) {
		Query query = entityManager.createNativeQuery("select id,p_name,gf_desc,p_pic,price,cate_code,status, icon,create_dt,update_dt "
				+ "from app_product where status=1 and cate_code="+cateCode,AppProduct.class);
		
		return (List<AppProduct>)query.getResultList();
	}

}
