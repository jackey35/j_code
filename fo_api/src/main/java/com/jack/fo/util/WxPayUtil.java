package com.jack.fo.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.jack.fo.model.AppOrder;

@Component
public class WxPayUtil {
	private Logger logger = LoggerFactory.getLogger("WxPayUtil");
	@Autowired
	private WXPayConfig payConfig;

	public Map<String, String> weixinPay(AppOrder order) throws Exception {
		WXPay pay = new WXPay(payConfig);
		Map<String, String> reqData = new HashMap<String, String>();
		reqData.put("body", "开运大师-" + order.getpName());
		reqData.put("out_trade_no", order.getOrderNo());
		//reqData.put("total_fee", order.getOrderPrice() * 100 + "");//单位默认是分
		reqData.put("total_fee", 1 + "");
		reqData.put("spbill_create_ip", "117.13.227.46");
		reqData.put("notify_url", payConfig.getNotifyUrl());
		reqData.put("trade_type", "APP");
		
		Map<String, String> map = pay.unifiedOrder(reqData);
		String returnCode = map.get("return_code");
		
		if(!StringUtils.isEmpty(returnCode) && "SUCCESS".equals(returnCode)) {
			Map<String, String> resMap = new HashMap<String, String>();
			resMap.put("appid", map.get("appid"));
			resMap.put("partnerid", map.get("mch_id"));
			resMap.put("prepayid", map.get("prepay_id"));
			resMap.put("package", "Sign=WXPay");
			resMap.put("noncestr", map.get("nonce_str"));
			resMap.put("timestamp", System.currentTimeMillis()/1000+"");
			resMap.put("sign", WXPayUtil.generateSignature(resMap, payConfig.getKey()));
			return resMap;
		}
			
		return null;
	}

	@SuppressWarnings("rawtypes")
	public String transMapToString(Map<String,String> map) {
		java.util.Map.Entry entry;
		StringBuffer sb = new StringBuffer();
		for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
			entry = (java.util.Map.Entry) iterator.next();
			sb.append(entry.getKey().toString()).append("=")
					.append(null == entry.getValue() ? "" : entry.getValue().toString());
			sb.append("&");
		}
		return sb.toString().substring(0, sb.toString().length()-1);
	}
	
	public void postHttp(Map<String,String> map) {
		WXPay pay = new WXPay(payConfig);
		try {
			String str = pay.requestWithoutCert("http://localhost:8080/order/wx_payback.do", map, 30000	, 30000);
			logger.info("str="+str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
