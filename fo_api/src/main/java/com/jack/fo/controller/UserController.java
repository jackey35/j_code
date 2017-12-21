package com.jack.fo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.jack.fo.dao.AppUserRepository;
import com.jack.fo.model.AppUser;
import com.jack.fo.util.IpUtil;
import com.jack.fo.util.ResponseUtil;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private AppUserRepository appUserRepository;
	 @Autowired  
	 RestTemplate restTemplate;  
	 @Autowired
	 private HttpServletRequest request;
	private final static String GET_USER_INFO="http://api.weixin.qq.com/sns/userinfo?";
	private final static String QQ_GET_USER="https://graph.qq.com/user/get_user_info?";
	
	@RequestMapping("/save")
	/**
	 * 
	 * @param accessToken
	 * @param openId
	 * @param type 1、android 2、ios
	 * @param channel
	 * @param loginType 1、微信 2、QQ
	 * @return
	 */
	public Map<String ,Object> save(String accessToken,String openId,int type,int channel,int loginType) {
		if(StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(openId)) {
			return ResponseUtil.getResponseObject(0, null, "缺少accessToke或openId");
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		AppUser user = appUserRepository.getAppUserByOpenId(openId);
		if(user == null) {
			String reqUrl = null;
			if(loginType==1) {
				reqUrl = GET_USER_INFO+"access_token="+accessToken+"&openid="+openId;
			}else {
				reqUrl = QQ_GET_USER+"access_token="+accessToken+"&openid="+openId+"&oauth_consumer_key="+((type==2)?"1106483677":"1106560534");
			}
			try {
				String res = restTemplate.getForEntity(reqUrl, String.class).getBody();  
				JSONObject json = null;
				if(!StringUtils.isEmpty(res) ) {
					json = new JSONObject(res);
					Object errCode = null;
					if(loginType == 1) {
						try {
							errCode = json.get("errcode");
						}catch(Exception e) {
							e.printStackTrace();
						}
					}else {
						errCode = json.get("ret");
					}
					
					if( (loginType == 1 && StringUtils.isEmpty(errCode)) 
							|| (loginType != 1 && "0".equals(errCode))){
						if(json != null) {
							user = new AppUser();
							user.setNickName(json.getString("nickname"));
							user.setHeadUrl(loginType == 1?json.getString("headimgurl"):json.getString("figureurl"));
							user.setOpenId(openId);
							user.setRegTime(sdf.format(new Date()));
							user.setRegChannel(channel);
							user.setRegIp(IpUtil.getIpAddr(request));
							user.setCreateDt(sdf.format(new Date()));
							user.setUpdateDt(sdf.format(new Date()));
							
							appUserRepository.save(user);
							
							return ResponseUtil.getResponseObject(1, user, "success");
						}
					}else {
						return ResponseUtil.getResponseObject(0, null, "微信验证失败");
					}
				}
				
			}catch(Exception e) {
				e.printStackTrace();
				return ResponseUtil.getResponseObject(0, null, "验证微信接口错误");
			}
		}
		
		return ResponseUtil.getResponseObject(1, user, "success");
	}
	
	@RequestMapping("/get")
	public Map<String ,Object> get(long uid) {
		AppUser user = appUserRepository.findOne(uid);
		if(user != null) {
			return ResponseUtil.getResponseObject(1, user, "success");
		}else {
			return ResponseUtil.getResponseObject(0, null, "用户不存在");
		}
	}
}
