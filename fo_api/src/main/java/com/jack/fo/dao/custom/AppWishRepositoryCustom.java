package com.jack.fo.dao.custom;

import java.util.List;

import com.jack.fo.model.AppWish;

public interface AppWishRepositoryCustom {
	public List<AppWish> getAppWishByUid(long uid);
}
