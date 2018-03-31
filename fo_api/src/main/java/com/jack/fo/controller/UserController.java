package com.jack.fo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.jack.fo.dao.AppUserRepository;
import com.jack.fo.model.AppUser;
import com.jack.fo.util.HttpUtil;
import com.jack.fo.util.IpUtil;
import com.jack.fo.util.MD5Util;
import com.jack.fo.util.PageUtil;
import com.jack.fo.util.ResponseUtil;

@Controller
public class UserController {
	private Logger logger = LoggerFactory.getLogger("UserController");
	@Autowired
	private AppUserRepository appUserRepository;
	 @Autowired  
	 RestTemplate restTemplate;  
	 @Autowired
	 private HttpServletRequest request;
	private final static String GET_USER_INFO="http://api.weixin.qq.com/sns/userinfo?";
	private final static String QQ_GET_USER="https://graph.qq.com/user/get_user_info?";
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@ResponseBody
	@RequestMapping("/user/save")
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
		logger.info("user save,accessToken="+accessToken+"，openId="+openId+",loginType="+loginType+",type="+type+",channel="+channel);
		if(StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(openId)) {
			return ResponseUtil.getResponseObject(0, null, "缺少accessToke或openId");
		}
		
		AppUser user = appUserRepository.getAppUserByOpenId(openId);
		if(user == null) {
			String reqUrl = null;
			if(loginType==1) {
				reqUrl = "access_token="+accessToken+"&openid="+openId;
			}else {
				reqUrl = "access_token="+accessToken+"&openid="+openId+"&oauth_consumer_key="+((type==2)?"1106483677":"1106560534");
			}
			try {
				String res = HttpUtil.sendPost((loginType==1?GET_USER_INFO:QQ_GET_USER), reqUrl);//restTemplate.getForEntity(reqUrl, String.class).getBody();  
				logger.info("user save res="+res);
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
							|| (loginType != 1 && "0".equals(errCode.toString()))){
						if(json != null) {
							user = new AppUser();
							user.setNickName(json.getString("nickname"));
							user.setHeadUrl(loginType == 1?json.getString("headimgurl"):json.getString("figureurl_qq_1"));
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
						return ResponseUtil.getResponseObject(0, null, "登录验证失败");
					}
				}
				
			}catch(Exception e) {
				e.printStackTrace();
				logger.error("user save e="+e.getMessage());
				return ResponseUtil.getResponseObject(0, null, "验证微信接口错误");
			}
		}
		
		return ResponseUtil.getResponseObject(1, user, "success");
	}
	
	@ResponseBody
	@RequestMapping("/user/get")
	public Map<String ,Object> get(long uid) {
		AppUser user = appUserRepository.findOne(uid);
		if(user != null) {
			return ResponseUtil.getResponseObject(1, user, "success");
		}else {
			return ResponseUtil.getResponseObject(0, null, "用户不存在");
		}
	}
	
	@ResponseBody
	@RequestMapping("/user/reg")
	public Map<String ,Object> register(String name,String pass) {
		AppUser user = appUserRepository.getAppUserByNickName(name);
		if(user != null || StringUtils.isEmpty(pass) || name.length()>20) {
			return ResponseUtil.getResponseObject(0, null, "昵称已存在或注册密码不能为空");
		}else {
			user = new AppUser();
			user.setNickName(name);
			user.setPass(MD5Util.MD5Encode(pass, "utf-8"));
			user.setHeadUrl("http://q.qlogo.cn/qqapp/111111/942FEA70050EEAFBD4DCE2C1FC775E56/40");
			user.setRegTime(sdf.format(new Date()));
			user.setRegChannel(100);
			user.setRegIp(IpUtil.getIpAddr(request));
			user.setCreateDt(sdf.format(new Date()));
			user.setOpenId("100");
			user.setUpdateDt(sdf.format(new Date()));
			try {
				user = appUserRepository.save(user);
				user.setPass("");
				return ResponseUtil.getResponseObject(1, user, "注册成功");
			}catch(Exception e) {
				return ResponseUtil.getResponseObject(0, null, "注册失败");
			}
		}
	}
	
	@ResponseBody
	@RequestMapping("/user/login")
	public Map<String ,Object> login(String name,String pass) {
		pass = MD5Util.MD5Encode(pass, "utf-8");
		logger.info("login,name="+name+",pass="+pass);
		AppUser user = appUserRepository.getAppUserByNickNamePass(name, pass);
		if(user != null) {
			user.setPass("");
			return ResponseUtil.getResponseObject(1, user, "登录成功");
		}else {
			return ResponseUtil.getResponseObject(0, null, "用户不存在或密码不正确");
		}
	}
	
	
	@RequestMapping("/admin/user/list")
	public String list(ModelMap map ,HttpServletRequest request,AppUser user,String startDt,String endDt,Integer start) {
		int page = start==null?1:start.intValue();
		int count = appUserRepository.cntAppUserListByCond(user,startDt,endDt);
		List<AppUser> list = new ArrayList<AppUser>();
		list = appUserRepository.getAppUserListByCond(user,startDt,endDt,(page-1)*PageUtil.PAGE_SIZE,PageUtil.PAGE_SIZE);
		map.put("count", count);
		map.put("list", list);
		map.put("user", user);
		map.put("startDt", startDt);
		map.put("endDt", endDt);
		map.put("pageCount", PageUtil.getPage(count, PageUtil.PAGE_SIZE));
		map.put("pageNow", page);
		request.setAttribute("pageName", page);
		request.setAttribute("pageCount", PageUtil.getPage(count, PageUtil.PAGE_SIZE));
		
		return "user/list";
	}
}
