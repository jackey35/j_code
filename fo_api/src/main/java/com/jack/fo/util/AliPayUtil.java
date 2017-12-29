package com.jack.fo.util;


import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;

@Component
public class AliPayUtil {
	private static Logger logger = Logger.getLogger(AliPayUtil.class);
	@Autowired
	public PayConfig payConfig;
	
	public String getOrderString(String orderNo, String subject, int orderPrice) {
		// 实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient(payConfig.getAliGateway(), payConfig.getAliAppId(),
				payConfig.getAliPrivateKey(), "json", "utf-8", payConfig.getAliPublicKey(), "RSA2");
		// 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		// SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody("开运大师");
		model.setSubject(subject);
		model.setOutTradeNo(orderNo);
		model.setTimeoutExpress("30m");
		//model.setTotalAmount(orderPrice + "");//单位默认是元
		model.setTotalAmount(0.01 + "");
		model.setProductCode("QUICK_MSECURITY_PAY");
		request.setBizModel(model);
		request.setNotifyUrl(payConfig.getNotifyUrl());
		try {
			// 这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
			logger.info(response.getBody());// 就是orderString 可以直接给客户端请求，无需再做处理。
			return response.getBody();
		} catch (AlipayApiException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean validateOrderBack(Map<String, String> paramsMap){
		try {
			return AlipaySignature.rsaCheckV2(paramsMap,payConfig.getAliPublicKey(), "utf-8","RSA2");
		} catch (AlipayApiException e) {
			e.printStackTrace();
			return false;
		}
	}
}
