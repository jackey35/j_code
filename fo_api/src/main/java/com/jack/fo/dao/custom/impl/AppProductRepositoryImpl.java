package com.jack.fo.dao.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jack.fo.dao.custom.AppProductRepositoryCustom;
import com.jack.fo.model.AppProduct;

public class AppProductRepositoryImpl implements AppProductRepositoryCustom {
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AppProduct> getAppProductByCateCode(int cateCode) {
		Query query = entityManager.createNativeQuery("select id,p_name,gf_desc,p_pic,price,cate_code,status, icon,create_dt,update_dt, "
				+ "priority from app_product where status=1 and cate_code="+cateCode+" order by priority desc,id desc",AppProduct.class);
		
		return (List<AppProduct>)query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<AppProduct> getAppProductByPname(AppProduct product) {
		String cond = "1=1 ";
		if(!StringUtils.isEmpty(product.getpName())) {
			cond = cond + " and p_name like '%"+product.getpName()+"%'";
		}
		
		if(product.getCateCode()!=0) {
			cond = cond + " and cate_code ="+product.getCateCode();
		}
		Query query = entityManager.createNativeQuery("select id,p_name,gf_desc,p_pic,price,cate_code,status, icon,create_dt,update_dt, "
				+ "priority from app_product where "+cond+" order by id desc",AppProduct.class);
		
		return (List<AppProduct>)query.getResultList();
	}

}
