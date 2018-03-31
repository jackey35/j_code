package com.jack.kxb.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jack.kxb.dao.custom.KxSmashEggRepositoryCustom;
import com.jack.kxb.model.KxSmashEgg;

@Repository
public interface KxSmashEggRepository extends CrudRepository<KxSmashEgg, Long>,KxSmashEggRepositoryCustom {

}
