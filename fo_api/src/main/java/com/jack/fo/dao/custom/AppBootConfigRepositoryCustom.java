package com.jack.fo.dao.custom;

import java.util.List;

import com.jack.fo.model.AppBootConfig;

public interface AppBootConfigRepositoryCustom {
	public List<AppBootConfig> getAppBootConfigBy(int type);
}
