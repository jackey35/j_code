package com.jack.fo.dao.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jack.fo.model.AppUpgradeConfig;
@Repository
public interface AppUpgradeConfigRepositoryCustom {
	public List<AppUpgradeConfig> getAppUpgradeConfigByTypeChannel(int type,int channel);
	
	public List<AppUpgradeConfig> getAppUpgradeConfigList(int start,int limit);
	public int cntAppUpgradeConfig();
}
