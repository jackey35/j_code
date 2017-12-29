package com.jack.fo.dao.custom;

import java.util.List;

import com.jack.fo.model.AppShareConfig;

public interface AppShareConfigRepositoryCustom {
	public AppShareConfig getAppShareConfigByType(int type);
	
	public List<AppShareConfig> getAppShareConfigListByType(int type,int start,int limit);
	public int cntAppShareConfigListByType(int type);
}
