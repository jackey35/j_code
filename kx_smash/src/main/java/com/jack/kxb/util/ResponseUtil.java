package com.jack.kxb.util;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
	
	   /**
     * 返回一个对象json
     * 
     * @param obj
     * @return
     */
    public static void response(Object obj,HttpServletResponse response) {
        if(obj == null) obj = new Object();
        write(GsonHelper.toGson(obj),response);
    }
    
    public static void write(String msg,HttpServletResponse response ) {
        try {
            response.setContentType("application/json; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            PrintWriter pw = response.getWriter();
            pw.write(msg);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
