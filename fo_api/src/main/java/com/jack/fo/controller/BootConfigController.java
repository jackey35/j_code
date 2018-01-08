package com.jack.fo.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jack.fo.dao.AppBootConfigRepository;
import com.jack.fo.model.AppBootConfig;
import com.jack.fo.util.FileUtil;
import com.jack.fo.util.PayConfig;
import com.jack.fo.util.ResponseUtil;

@Controller
public class BootConfigController {
	private Logger logger = LoggerFactory.getLogger("BootConfigController");
	@Autowired
	private AppBootConfigRepository appBootConfigRepository;
	@Autowired
	private PayConfig payConfig;
	
	@ResponseBody
	@RequestMapping("/boot/get")
	public Map<String,Object> getAppBootConfigByType(int type) {
		if(type==0)
			type = 1;
		
		List<AppBootConfig> list = appBootConfigRepository.getAppBootConfigBy(type,true);
		AppBootConfig bootConfig = null;
		if(list != null && list.size()>0) {
			bootConfig = list.get(0);
			bootConfig.setBootPay(payConfig.getBootPay());
		}
		return ResponseUtil.getResponseObject(1, bootConfig, "");
	}
	
	@ResponseBody
	@RequestMapping("/boot/save")
	public void save(AppBootConfig bootConfig) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		bootConfig.setCreateDt(sdf.format(new Date()));
		bootConfig.setUpdateDt(sdf.format(new Date()));
		bootConfig.setType(1);
		
		appBootConfigRepository.save(bootConfig);
	}
	
	@ResponseBody
	@RequestMapping("/admin/boot/update")
	public void update(long id,int status,HttpServletResponse response) {
		Map<String, Object> json = new HashMap<String, Object>();
		if(status == 2) {
			appBootConfigRepository.delete(id);
			json.put("error", 0);
			json.put("boot", null);
			ResponseUtil.response(json, response);
			return ;
		}
		AppBootConfig boot = appBootConfigRepository.findOne(id);
		
		if(boot != null) {
			boot.setStatus(status);
			appBootConfigRepository.save(boot);
			json.put("error", 0);
			json.put("boot", boot);
		}else {
			json.put("error", 1);
			json.put("boot", null);
		}
		 ResponseUtil.response(json, response);
	}
	
	
	@RequestMapping("/admin/boot/list")
	public String getAppBootConfigList(ModelMap map ,HttpServletRequest request) {
		List<AppBootConfig> list = appBootConfigRepository.getAppBootConfigBy(1,false);
		
		map.put("list", list);
		
		return "/boot/list";
	}
	
	@ResponseBody
	@RequestMapping("/admin/boot/upload")
	public void upload(@RequestParam MultipartFile image,HttpServletResponse response) {
		try {
			String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			FileUtil.uplaodFile(image, payConfig,fileName);
			String fileSuff = FilenameUtils.getExtension(image.getOriginalFilename());
            Map<String, Object> json = new HashMap<String, Object>();
            json.put("error", 0);
            json.put("url", "");
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            AppBootConfig bootConfig = new AppBootConfig();
            bootConfig.setBootUrl("http://www.5818cp.com/images/"+fileName+"."+fileSuff);
            bootConfig.setType(1);
            bootConfig.setStatus(1);
	    		bootConfig.setCreateDt(sdf.format(new Date()));
	    		bootConfig.setUpdateDt(sdf.format(new Date()));
	    		
	    		appBootConfigRepository.save(bootConfig);
    		
            ResponseUtil.response(json, response);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            ResponseUtil.getResponseObject(0, null, "failure");
        }
	}
	
	@RequestMapping("/admin/mb/list")
	public String getAppMbConfigList(ModelMap map ,HttpServletRequest request) {
		List<AppBootConfig> list = appBootConfigRepository.getAppBootConfigBy(2,false);
		
		map.put("list", list);
		
		return "/mb/list";
	}
	
	@ResponseBody
	@RequestMapping("/admin/mb/upload")
	public void mbUpload(@RequestParam MultipartFile image,HttpServletResponse response) {
		try {
			String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			FileUtil.uplaodFile(image, payConfig,fileName);
			String fileSuff = FilenameUtils.getExtension(image.getOriginalFilename());
           
            Map<String, Object> json = new HashMap<String, Object>();
            json.put("error", 0);
            json.put("url", "");
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            AppBootConfig bootConfig = new AppBootConfig();
            bootConfig.setBootUrl("http://www.5818cp.com/images/"+fileName+"."+fileSuff);
            bootConfig.setType(2);
            bootConfig.setStatus(1);
	    		bootConfig.setCreateDt(sdf.format(new Date()));
	    		bootConfig.setUpdateDt(sdf.format(new Date()));
	    		
	    		appBootConfigRepository.save(bootConfig);
    		
            ResponseUtil.response(json, response);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            ResponseUtil.getResponseObject(0, null, "failure");
        }
	}
}
