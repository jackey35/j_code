package com.jack.kxb.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jack.kxb.dao.KxShareRepository;
import com.jack.kxb.dao.KxUserRepository;
import com.jack.kxb.model.KxShare;
import com.jack.kxb.model.KxUser;
import com.jack.kxb.util.ResponseUtil;

@Controller
public class KxShareController {
	private Logger logger = LoggerFactory.getLogger("KxShareController");
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat sdfdt = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private KxShareRepository kxShareRepository;
	@Autowired
	private KxUserRepository kxUserRepository;
	@RequestMapping("/smash/share")
	@ResponseBody
	public Map<String, Object> save(String openId) {
		logger.info("openId={}",openId);
		if(StringUtils.isEmpty(openId)) {
			return ResponseUtil.getResponseObject(0, null, "非法用户");
		}
		KxUser kxUser = kxUserRepository.getKxUserByOpenId(openId);
		if(kxUser == null) {
			return ResponseUtil.getResponseObject(0, null, "非法用户");
		}
		
		KxShare kxShare = kxShareRepository.getKxShareByOpenIdAndShareDt(openId, sdfdt.format(new Date()));
		if(kxShare == null) {
			kxShare = new KxShare();
			kxShare.setOpenId(openId);
			kxShare.setShareDt(sdfdt.format(new Date()));
			kxShare.setCreateDt(sdf.format(new Date()));
			
			kxShareRepository.save(kxShare);
		}
		
		return ResponseUtil.getResponseObject(1, kxShare, "share success");
	}

}
