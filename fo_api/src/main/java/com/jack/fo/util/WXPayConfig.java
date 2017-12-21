package com.jack.fo.util;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class WXPayConfig implements com.github.wxpay.sdk.WXPayConfig {
	@Autowired
	private PayConfig payConfig;
	@Override
	public String getAppID() {
		return payConfig.getWxAppId();
	}

	@Override
	public String getMchID() {
		return payConfig.getWxMchId();
	}

	@Override
	public String getKey() {
		return payConfig.getWxPrivateKey();
	}

	@Override
	public InputStream getCertStream() {
		return null;
	}

	@Override
	public int getHttpConnectTimeoutMs() {
		return 3000;
	}

	@Override
	public int getHttpReadTimeoutMs() {
		return 3000;
	}

	public String getNotifyUrl() {
		return payConfig.getWxNotifyUrl();
	}
	
	public String getPreOrder() {
		return payConfig.getWxPreOrder();
	}
}
