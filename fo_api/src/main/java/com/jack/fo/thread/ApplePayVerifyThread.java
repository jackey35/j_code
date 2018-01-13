package com.jack.fo.thread;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jack.fo.dao.AppOrderRepository;
import com.jack.fo.model.AppOrder;
import com.jack.fo.util.HttpUtil;
import com.jack.fo.util.PayConfig;

public class ApplePayVerifyThread implements Runnable {
	private Logger logger = LoggerFactory.getLogger("ApplePayVerifyThread");
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private AppOrderRepository appOrderRepository;
	private PayConfig payConfig;
	private String orderNo;
	private String receipt;
	private String transanId;
	
	public ApplePayVerifyThread(PayConfig payConfig,String orderNo,String receipt,String transanId,
			AppOrderRepository appOrderRepository){
		this.payConfig = payConfig;
		this.orderNo = orderNo;
		this.receipt = receipt;
		this.transanId = transanId;
		this.appOrderRepository = appOrderRepository;
	}
	@Override
	public void run() {
		String verifyUrl = payConfig.getProdVerify();
		String res = HttpUtil.postData(verifyUrl,"{\"receipt-data\":\"" + receipt+"\"}");
		logger.info("apple prod verify res="+res);
		JSONObject jsonObj = new JSONObject(res);
		Object resStatus = jsonObj.get("status");
		if(resStatus != null && Integer.valueOf(resStatus.toString())==21007) {
			verifyUrl = payConfig.getDevVerify();
			res = HttpUtil.postData(verifyUrl,"{\"receipt-data\":\"" + receipt+"\"}");
			logger.info("apple dev verify res="+res);
			jsonObj = new JSONObject(res);
			resStatus = jsonObj.get("status");
			if(resStatus != null && Integer.valueOf(resStatus.toString())==0) {
				AppOrder order = appOrderRepository.getAppOrderByOrderNo(orderNo);
				if(order == null || order.getStatus() == 1) {
					logger.error("orderNo="+orderNo+",is null or payed");
					return;
				}
				order.setStatus(1);
				order.setPayDt(sdf.format(new Date()));
				order.setOutOrderNo(transanId);
				order.setUpdateDt(sdf.format(new Date()));
				appOrderRepository.save(order);
				logger.info("apple pay succes,orderNo="+orderNo);
			}
		}
	}

}
