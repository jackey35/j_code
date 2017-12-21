package com.jack.fo.controller;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jack.fo.dao.AppCateCodeRepository;
import com.jack.fo.model.AppCateCode;
import com.jack.fo.util.ResponseUtil;

@RestController
@RequestMapping("/cc")
public class CateCodeController {
	@Autowired 
	private AppCateCodeRepository appCateCodeRepository;
	Logger log = LoggerFactory.getLogger("CateCodeController");
	@RequestMapping("/list")
	public Map<String,Object> cateCodeList() {
		
		ArrayList<AppCateCode> list = (ArrayList<AppCateCode>)appCateCodeRepository.findAll();

		
		return ResponseUtil.getResponseMap(1, (list!=null&&list.size()>0)?list.size():0,list,"success");
	}
	
	@RequestMapping("/save")
	public String save(AppCateCode cateCode) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		cateCode.setCreateDt(sdf.format(new Date()));
		appCateCodeRepository.save(cateCode);
		log.info(cateCode.getCateName());
		return "";
	}
}
