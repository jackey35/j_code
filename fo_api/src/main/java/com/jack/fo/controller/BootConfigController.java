package com.jack.fo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jack.fo.dao.AppBootConfigRepository;
import com.jack.fo.model.AppBootConfig;
import com.jack.fo.util.ResponseUtil;

@RestController
@RequestMapping("/boot")
public class BootConfigController {
	@Autowired
	private AppBootConfigRepository appBootConfigRepository;
	
	@RequestMapping("/get")
	public Map<String,Object> getAppBootConfigByType(int type) {
		if(type==0)
			type = 1;
		
		List<AppBootConfig> list = appBootConfigRepository.getAppBootConfigBy(type);
		AppBootConfig bootConfig = null;
		if(list != null && list.size()>0) {
			bootConfig = list.get(0);
		}
		return ResponseUtil.getResponseObject(1, bootConfig, "");
	}
	
	@RequestMapping("/save")
	public void save(AppBootConfig bootConfig) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		bootConfig.setCreateDt(sdf.format(new Date()));
		bootConfig.setUpdateDt(sdf.format(new Date()));
		
		appBootConfigRepository.save(bootConfig);
	}
}
