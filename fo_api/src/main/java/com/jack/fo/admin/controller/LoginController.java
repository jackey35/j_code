package com.jack.fo.admin.controller;


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

import com.jack.fo.dao.AdmUserRepository;
import com.jack.fo.model.AdmUser;
import com.jack.fo.util.MD5Util;
import com.jack.fo.util.ResponseUtil;

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
		return "redirect:/admin/user/list.do?start=1";
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
		admUserRepository.save(admUser);
		
		return ResponseUtil.getResponseObject(1, null, "注册成功");
	}
	

	@RequestMapping("/predit")
	public String preEdit(HttpServletRequest request) {
		
		return "changepwd";
	}
	
	@RequestMapping("/updatePwd")
	public String updtePwd(ModelMap map ,HttpServletRequest request,String newPwd,String newPwd1,String oldPwd) {
		if(StringUtils.isEmpty(newPwd) || !newPwd.equals(newPwd1) ||StringUtils.isEmpty(oldPwd)) {
			map.put("msg", "请输入密码或确认密码不正确");
			return "changepwd";
		}
		String userName = (String)request.getSession().getAttribute("userName");
		String pass = MD5Util.MD5Encode(oldPwd, "");
		AdmUser user = admUserRepository.getAdmUserByUserNamePass(userName, pass);
		if(user != null) {
			user.setPassword(MD5Util.MD5Encode(newPwd, ""));
			admUserRepository.save(user);
			map.put("msg", "密码修改成功");
		}else {
			map.put("msg", "原密码错误");
		}
		return "changepwd";
	}
}
