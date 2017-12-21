package com.jack.fo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jack.fo.dao.custom.AppBootConfigRepositoryCustom;
import com.jack.fo.model.AppBootConfig;
@Repository
public interface AppBootConfigRepository extends CrudRepository<AppBootConfig, Long>, AppBootConfigRepositoryCustom {

}
