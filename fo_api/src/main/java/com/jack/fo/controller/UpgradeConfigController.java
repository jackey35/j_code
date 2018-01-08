package com.jack.fo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jack.fo.dao.AppUpgradeConfigRepository;
import com.jack.fo.model.AppUpgradeConfig;
import com.jack.fo.util.PageUtil;
import com.jack.fo.util.ResponseUtil;


@Controller
public class UpgradeConfigController {
	@Autowired
	private AppUpgradeConfigRepository AppUpgradeConfigRepository;
	
	@ResponseBody
	@RequestMapping("/upc/upgrade")
	public Map<String,Object> isNeedUpgrade(int type,String versionNo,int channel) {
		List<AppUpgradeConfig> list = AppUpgradeConfigRepository.getAppUpgradeConfigByTypeChannel(type, channel);
		if(list != null && list.size()>0) {
			AppUpgradeConfig upgradeConfig = list.get(0);
			if(!versionNo.equals(upgradeConfig.getVersionNo())) {
				return ResponseUtil.getResponseObject(1, upgradeConfig, "");
			}
		}
		return ResponseUtil.getResponseObject(0, null, "");
	}
	
	@ResponseBody
	@RequestMapping("/admin/upc/save")
	public void save(String versionNo,String memo,String downloadUrl,Integer channel,Integer type,HttpServletResponse response) {
		Map<String, Object> json = new HashMap<String, Object>();
		
		if(StringUtils.isEmpty(versionNo) || channel == null ||(type.equals(1) && channel.equals(0))) {
			json.put("error", 1);
			json.put("url", "参数必填");
	 		
			ResponseUtil.response(json, response);
		}
		if(type.equals(2)) {
			channel = 0;
		}
		AppUpgradeConfig upgradeConfig = new AppUpgradeConfig();
		upgradeConfig.setVersionNo(versionNo);
		upgradeConfig.setMemo(memo);
		upgradeConfig.setChannel(channel);
		upgradeConfig.setType(type);
		upgradeConfig.setDownloadUrl(downloadUrl);
		upgradeConfig.setStatus(1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		upgradeConfig.setCreateDt(sdf.format(new Date()));
		upgradeConfig.setUpdateDt(sdf.format(new Date()));
		upgradeConfig.setStatus(1);
		AppUpgradeConfigRepository.save(upgradeConfig);
		
		json.put("error", 0);
		json.put("url", "添加成功");
 		
		ResponseUtil.response(json, response);

	}
	
	@RequestMapping("/admin/upc/list")
	public String list(ModelMap map ,HttpServletRequest request,Integer start) {
		int page = start==null?1:start.intValue();
		int count = AppUpgradeConfigRepository.cntAppUpgradeConfig();
		List<AppUpgradeConfig> list = new ArrayList<AppUpgradeConfig>();
		list = AppUpgradeConfigRepository.getAppUpgradeConfigList((page-1)*PageUtil.PAGE_SIZE,PageUtil.PAGE_SIZE);
		map.put("count", count);
		map.put("list", list);
		map.put("pageCount", PageUtil.getPage(count, PageUtil.PAGE_SIZE));
		map.put("pageNow", page);
		request.setAttribute("pageName", page);
		request.setAttribute("pageCount", PageUtil.getPage(count, PageUtil.PAGE_SIZE));
		
		return "upc/list";
	}
	
	@ResponseBody
	@RequestMapping("/admin/upc/update")
	public void update(long id,int status,HttpServletResponse response) {
		Map<String, Object> json = new HashMap<String, Object>();
		if(status == 2) {
			AppUpgradeConfigRepository.delete(id);
			json.put("error", 0);
			json.put("upc", null);
			ResponseUtil.response(json, response);
			return ;
		}
		AppUpgradeConfig upc = AppUpgradeConfigRepository.findOne(id);
		
		if(upc != null) {
			upc.setStatus(status);
			AppUpgradeConfigRepository.save(upc);
			json.put("error", 0);
			json.put("upc", upc);
		}else {
			json.put("error", 1);
			json.put("upc", null);
		}
		 ResponseUtil.response(json, response);
	}
}
