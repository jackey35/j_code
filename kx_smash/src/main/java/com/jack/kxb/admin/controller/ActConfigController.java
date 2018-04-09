package com.jack.kxb.admin.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jack.kxb.util.ActConfigUtil;
import com.jack.kxb.util.ResponseUtil;

@Controller
public class ActConfigController {
	private Logger logger = LoggerFactory.getLogger("ActConfigController");
	@RequestMapping(value="/admin/config/edit")
	public String edit(ModelMap map) {
		Set<String> set = ActConfigUtil.tdjOpenIds.keySet();
		Iterator<String> it = set.iterator();
		StringBuffer sb =new StringBuffer();
		while(it.hasNext()) {
			sb.append(it.next()).append(",");
		}
		map.put("opIds", sb.toString().substring(0, sb.toString().length()-1));
		map.put("sdt", ActConfigUtil.actStartEndDt.get("sdt"));
		map.put("edt", ActConfigUtil.actStartEndDt.get("edt"));
		return "config/edit";
	}
	
	@ResponseBody
	@RequestMapping(value="/admin/config/save")
	public void save(String opIds,String sdt,String edt,HttpServletResponse response) {
		logger.info("opids={},sdt={},edt={}",opIds,sdt,edt);
		ActConfigUtil.tdjOpenIds.clear();
		String[] aryOpenId = opIds.split(",");
		for(String id : aryOpenId) {
			ActConfigUtil.tdjOpenIds.put(id, id);
		}
		
		ActConfigUtil.actStartEndDt.clear();
		ActConfigUtil.actStartEndDt.put("sdt", sdt);
		ActConfigUtil.actStartEndDt.put("edt", edt);
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("error", 0);
		json.put("url", "");
         
		ResponseUtil.response(json, response);
	}
}
