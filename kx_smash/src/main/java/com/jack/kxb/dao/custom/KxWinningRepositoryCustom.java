package com.jack.kxb.dao.custom;

import java.util.List;

import com.jack.kxb.model.KxWinning;

public interface KxWinningRepositoryCustom {
	public List<KxWinning> getKxWinningByCond(KxWinning kxWinning,int start,int size);
	public int cntKxWinningByCond(KxWinning kxWinning);
	public KxWinning getKxWinningByOpenIdAndWinId(String openId,Long winId);
}
