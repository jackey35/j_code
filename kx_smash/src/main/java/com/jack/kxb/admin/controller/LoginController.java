package com.jack.kxb.admin.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jack.kxb.dao.AdmUserRepository;
import com.jack.kxb.model.AdmUser;
import com.jack.kxb.util.MD5Util;
import com.jack.kxb.util.ResponseUtil;

@Controller
@RequestMapping("/admin/")
public class LoginController {
	@Autowired
	private AdmUserRepository admUserRepository;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@RequestMapping("/prelog")
	public String loginPage(ModelMap map) {
		return "prelogin";
	}
	
	@RequestMapping("/login")
	public String login(ModelMap map ,HttpServletRequest request,String userName,String password) {
		if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
			map.put("msg", "请输入用户名、密码");
			return "prelogin";
		}
		
		password = MD5Util.MD5Encode(password, "");
		AdmUser admUser = admUserRepository.getAdmUserByUserNamePass(userName, password);
		if(admUser == null) {
			map.put("msg", "用户名、密码不正确");
			return "prelogin";
		}
		request.getSession().setAttribute("userName", admUser.getUserName());
		return "redirect:/admin/prize/list.do";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().removeAttribute("userName");
		
		return "prelogin";
	}
	
	@ResponseBody
	@RequestMapping("/save")
	public Map<String,Object> saveAdmUser(AdmUser admUser){
		String userName = admUser.getUserName();
		String password = admUser.getPassword();
		if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
			return ResponseUtil.getResponseObject(0, null, "注册失败");
		}
		
		password = MD5Util.MD5Encode(password, "");
		admUser.setPassword(password);
		admUser.setCreateDt(sdf.format(new Date()));
		admUser.setUpdateDt(sdf.format(new Date()));
		//admUserRepository.save(admUser);
		
		return ResponseUtil.getResponseObject(1, null, "注册成功");
	}
}
