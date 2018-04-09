package com.jack.kxb.util;

import java.util.HashMap;
import java.util.Map;

public class ActConfigUtil {
	public static Map<String,String> tdjOpenIds = new HashMap<String,String>();
	public static Map<String,String> actStartEndDt = new HashMap<String,String>();
	static {
		tdjOpenIds.put("12345", "12345");
		tdjOpenIds.put("123456", "123456");
		actStartEndDt.put("sdt", "2018-04-24");
		actStartEndDt.put("edt", "2018-04-30");
	}
}
