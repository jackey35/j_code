package com.jack.kxb.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jack.kxb.dao.custom.KxShareRepositoryCustom;
import com.jack.kxb.model.KxShare;

@Repository
public interface KxShareRepository extends CrudRepository<KxShare,Long>,KxShareRepositoryCustom {

}
