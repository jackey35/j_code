package com.jack.fo.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseUtil {

	public static <T> Map<String ,Object> getResponseMap(int status,int count,List<T> list,String msg){
		Map<String,Object> map = new HashMap<String ,Object>();
		map.put("httpStatus", status);
		map.put("count", count);
		map.put("list", list);
		map.put("msg", msg);
		
		return map;
	}
	
	public static Map<String ,Object> getResponseObject(int status,Object o,String msg){
		Map<String,Object> map = new HashMap<String ,Object>();
		map.put("httpStatus", status);
		map.put("obj", o);
		map.put("msg", msg);
		
		return map;
	}
}
