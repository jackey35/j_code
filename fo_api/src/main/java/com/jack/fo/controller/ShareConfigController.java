package com.jack.fo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jack.fo.dao.AppShareConfigRepository;
import com.jack.fo.model.AppShareConfig;
import com.jack.fo.util.FileUtil;
import com.jack.fo.util.PageUtil;
import com.jack.fo.util.PayConfig;
import com.jack.fo.util.ResponseUtil;

@Controller
public class ShareConfigController {
	private Logger logger = LoggerFactory.getLogger("ShareConfigController");
	@Autowired
	private AppShareConfigRepository appShareConfigRepository;
	@Autowired
	private PayConfig payConfig;
	
	@ResponseBody
	@RequestMapping("/admin/share/save")
	public void save(String fileUrl,String title,String subTitle,String downloadUrl,Integer type,HttpServletResponse response) {
		AppShareConfig shareConfig = new AppShareConfig();
		shareConfig.setTitle(title);
		shareConfig.setSubTitle(subTitle);
		shareConfig.setIcon(fileUrl);
		shareConfig.setDownloadUrl(downloadUrl);
		shareConfig.setType(type==0?1:type);
		shareConfig.setStatus(1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		shareConfig.setCreateDt(sdf.format(new Date()));
		shareConfig.setUpdateDt(sdf.format(new Date()));
		
		appShareConfigRepository.save(shareConfig);
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("error", 0);
		json.put("url", fileUrl);
 		
		ResponseUtil.response(json, response);
	}
	
	@ResponseBody
	@RequestMapping("/share/get")
	public Map<String ,Object> getByType(int type) {
		if(type == 0)
			type = 1;
		
		AppShareConfig shareConfig = appShareConfigRepository.getAppShareConfigByType(type);
		
		return ResponseUtil.getResponseObject(1, shareConfig, "success");
	}
	
	@RequestMapping("/admin/share/list")
	public String list(ModelMap map ,HttpServletRequest request,Integer start,Integer type) {
		int page = start==null?1:start.intValue();
		int shareType = type==null?1:type.intValue();
		int count = appShareConfigRepository.cntAppShareConfigListByType(shareType);
		List<AppShareConfig> list = new ArrayList<AppShareConfig>();
		list = appShareConfigRepository.getAppShareConfigListByType(shareType, (page-1)*PageUtil.PAGE_SIZE,PageUtil.PAGE_SIZE);
		map.put("count", count);
		map.put("list", list);
		map.put("pageCount", PageUtil.getPage(count, PageUtil.PAGE_SIZE));
		map.put("pageNow", page);
		request.setAttribute("pageName", page);
		request.setAttribute("pageCount", PageUtil.getPage(count, PageUtil.PAGE_SIZE));
		
		return "share/list";
	}
	
	@ResponseBody
	@RequestMapping("/admin/share/upload")
	public void upload(@RequestParam MultipartFile image,HttpServletResponse response) {
		Map<String, Object> json = new HashMap<String, Object>();
		try {
			String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			String fileUrl = FileUtil.uplaodFile(image, payConfig,fileName);
			
			
            json.put("error", 0);
            json.put("url", "http://www.5818cp.com/images/"+fileUrl);
    		
            ResponseUtil.response(json, response);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            json.put("error", 1);
            json.put("url", "");
    		
            ResponseUtil.response(json, response);
        }
	}
	
	@ResponseBody
	@RequestMapping("/admin/share/update")
	public void update(long id,int status,HttpServletResponse response) {
		Map<String, Object> json = new HashMap<String, Object>();
		if(status == 2) {
			appShareConfigRepository.delete(id);
			json.put("error", 0);
			json.put("boot", null);
			ResponseUtil.response(json, response);
			return ;
		}
		AppShareConfig share = appShareConfigRepository.findOne(id);
		
		if(share != null) {
			share.setStatus(status);
			appShareConfigRepository.save(share);
			json.put("error", 0);
			json.put("share", share);
		}else {
			json.put("error", 1);
			json.put("share", null);
		}
		 ResponseUtil.response(json, response);
	}
}
