package com.jack.kxb.dao;

import org.springframework.data.repository.CrudRepository;

import com.jack.kxb.dao.custom.KxPrizeRepositoryCustom;
import com.jack.kxb.model.KxPrize;

public interface KxPrizeRepository extends CrudRepository<KxPrize, Long>,KxPrizeRepositoryCustom {

}
