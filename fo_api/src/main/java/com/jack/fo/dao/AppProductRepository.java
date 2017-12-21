package com.jack.fo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jack.fo.dao.custom.AppProductRepositoryCustom;
import com.jack.fo.model.AppProduct;

@Repository
public interface AppProductRepository extends CrudRepository<AppProduct, Long>,AppProductRepositoryCustom {
	
}
