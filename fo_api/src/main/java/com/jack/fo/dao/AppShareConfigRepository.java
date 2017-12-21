package com.jack.fo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jack.fo.dao.custom.AppShareConfigRepositoryCustom;
import com.jack.fo.model.AppShareConfig;

@Repository
public interface AppShareConfigRepository extends CrudRepository<AppShareConfig, Long>, AppShareConfigRepositoryCustom {

}
