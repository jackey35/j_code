package com.jack.fo.dao.custom;

import java.util.List;

import com.jack.fo.model.AppOrder;

public interface AppOrderRepositoryCustom {
	public AppOrder getAppOrderByOrderNo(String orderNo);
	public List<AppOrder> getAppOrderByUidType(long uid,int type,int start,int limit);
	public int countAppOrderByUidType(long uid,int type);
	
	public List<AppOrder> getAppOrderByStatusType(int type);
}
