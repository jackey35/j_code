package com.jack.fo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jack.fo.dao.AppUpgradeConfigRepository;
import com.jack.fo.model.AppUpgradeConfig;
import com.jack.fo.util.ResponseUtil;


@RestController
@RequestMapping("/upc")
public class UpgradeConfigController {
	@Autowired
	private AppUpgradeConfigRepository AppUpgradeConfigRepository;
	
	@RequestMapping("/upgrade")
	public Map<String,Object> isNeedUpgrade(int type,String versionNo,int channel) {
		List<AppUpgradeConfig> list = AppUpgradeConfigRepository.getAppUpgradeConfigByTypeChannel(type, channel);
		boolean flag = false;
		if(list != null && list.size()>0) {
			AppUpgradeConfig upgradeConfig = list.get(0);
			if(!versionNo.equals(upgradeConfig.getVersionNo())) {
				flag = true;
			}
		}
		return ResponseUtil.getResponseObject(1, flag, "");
	}
	
	@RequestMapping("/save")
	public void save(AppUpgradeConfig upgradeConfig) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		upgradeConfig.setCreateDt(sdf.format(new Date()));
		upgradeConfig.setUpdateDt(sdf.format(new Date()));
		upgradeConfig.setStatus(1);
		AppUpgradeConfigRepository.save(upgradeConfig);

	}
}
