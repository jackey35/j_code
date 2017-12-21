package com.jack.fo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jack.fo.dao.custom.AppOrderRepositoryCustom;
import com.jack.fo.model.AppOrder;

@Repository
public interface AppOrderRepository extends CrudRepository<AppOrder, Long>, AppOrderRepositoryCustom {

}
