package com.jack.fo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class SessionInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		if(request.getRequestURI().contains("admin/prelog") 
				|| request.getRequestURI().contains("admin/logout") 
				|| request.getRequestURI().contains("admin/login") ) {
 			return true;
		}
		Object sessionObj =request.getSession().getAttribute("userName");
		if(sessionObj == null) {
			response.sendRedirect("/admin/prelog.do");
			return false;
		}
		return true;
	}

}
