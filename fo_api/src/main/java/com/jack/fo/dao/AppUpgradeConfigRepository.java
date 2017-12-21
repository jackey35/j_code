package com.jack.fo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jack.fo.dao.custom.AppUpgradeConfigRepositoryCustom;
import com.jack.fo.model.AppUpgradeConfig;

@Repository
public interface AppUpgradeConfigRepository extends CrudRepository<AppUpgradeConfig, Long>,AppUpgradeConfigRepositoryCustom {

}
