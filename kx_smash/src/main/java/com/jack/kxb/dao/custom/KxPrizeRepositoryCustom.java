package com.jack.kxb.dao.custom;

import com.jack.kxb.model.KxPrize;

public interface KxPrizeRepositoryCustom {
	public KxPrize getKxPrizeByPrizeLevel(int winLevel);
}
