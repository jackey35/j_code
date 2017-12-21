package com.jack.fo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jack.fo.dao.AppShareConfigRepository;
import com.jack.fo.model.AppShareConfig;
import com.jack.fo.util.ResponseUtil;

@RestController
@RequestMapping("/share")
public class ShareConfigController {
	@Autowired
	private AppShareConfigRepository appShareConfigRepository;
	
	@RequestMapping("/save")
	public Map<String ,Object> save(AppShareConfig shareConfig) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		shareConfig.setCreateDt(sdf.format(new Date()));
		shareConfig.setUpdateDt(sdf.format(new Date()));
		
		appShareConfigRepository.save(shareConfig);
		return ResponseUtil.getResponseObject(1, shareConfig, "success");
	}
	
	@RequestMapping("/get")
	public Map<String ,Object> getByType(int type) {
		if(type == 0)
			type = 1;
		
		AppShareConfig shareConfig = appShareConfigRepository.getAppShareConfigByType(type);
		
		return ResponseUtil.getResponseObject(1, shareConfig, "success");
	}
}
