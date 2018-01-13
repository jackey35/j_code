package com.jack.fo.dao.custom;

import java.util.List;

import com.jack.fo.model.AppProduct;

public interface AppProductRepositoryCustom {
	public List<AppProduct> getAppProductByCateCode(int cateCode);
	public List<AppProduct> getAppProductByPname(AppProduct product);
}
