package com.jack.fo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jack.fo.dao.AppProductRepository;
import com.jack.fo.model.AppProduct;
import com.jack.fo.util.ResponseUtil;

@RestController
public class ProductController {
	@Autowired
	private AppProductRepository appProductRepository;
	
	@RequestMapping("/p/list")
	public Map<String,Object> getProductByCateCode(int cateCode){
		List<AppProduct> list = appProductRepository.getAppProductByCateCode(cateCode);
		
		return ResponseUtil.getResponseMap(1, (list!=null&&list.size()>0)?list.size():0,list,"success");
	} 
	
	@RequestMapping("/p/save")
	public void saveAppProduct(AppProduct appProduct) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		appProduct.setCreateDt(sdf.format(new Date()));
		appProduct.setUpdateDt(sdf.format(new Date()));
		appProductRepository.save(appProduct);
	}
	
	@RequestMapping("/p/get")
	public Map<String,Object> getAppProductById(Long id) {
		AppProduct product = appProductRepository.findOne(id);
		return ResponseUtil.getResponseObject(1, product, "success");
	}
}
