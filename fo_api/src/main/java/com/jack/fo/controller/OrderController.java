package com.jack.fo.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jack.fo.dao.AppOrderRepository;
import com.jack.fo.dao.AppProductRepository;
import com.jack.fo.dao.AppUserRepository;
import com.jack.fo.model.AliPayOrderBack;
import com.jack.fo.model.AppOrder;
import com.jack.fo.model.AppProduct;
import com.jack.fo.model.AppUser;
import com.jack.fo.model.PayUser;
import com.jack.fo.thread.ApplePayVerifyThread;
import com.jack.fo.util.AliPayUtil;
import com.jack.fo.util.PageUtil;
import com.jack.fo.util.PayConfig;
import com.jack.fo.util.ResponseUtil;
import com.jack.fo.util.WXRequestUtil;
import com.jack.fo.util.WxPayUtil;

@RestController
@RequestMapping("/order")
public class OrderController {
	private Logger logger = LoggerFactory.getLogger("OrderController");
	@Autowired
	private AppProductRepository appProductRepository;
	@Autowired
	private AppOrderRepository appOrderRepository;
	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private AliPayUtil aliPayUtil;
	@Autowired
	WxPayUtil wxPayUtil;
	@Autowired
	public PayConfig payConfig;
	private static final Executor executorchange = Executors.newFixedThreadPool(30);
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public int[] money = {888,666,88,66,8,6,10,100};
	public String[] name = {"与薄雾","背梦人","暖光","荒岛初冬","拂衣襟","沧笙踏歌","阑夜微凉","揽清幽","一半童真美","二手同情",
				"陪你流浪","妄想相拥","几度春秋","强颜欢笑","巴黎左岸的小镇","若有所念","二狗家的春天","草莓熊","陪我终","饶恕",
				"永远姗不掉的记忆","太阳女神","日落西暮","点背不能怪社会","魅世女王","女王范儿","绝情姑娘","迟钝的东西","梦里梦他","共赴未来",
				"烂漫мe谢幕","年深月久","白子画","冘归","阴天快乐","纵然怨","话不多说","迷路的男人","局外人","农村大代表"};
	
	@RequestMapping("/save")
	public Map<String,Object> save(AppOrder order){
		AppProduct product = new AppProduct();
		if(order.getOrderType() == 0) {
			product = appProductRepository.findOne(order.getPid());
			if(product != null && product.getPrice()==order.getOrderPrice()) {
				order.setpName(product.getpName());
				order.setOrderPrice(product.getPrice());
			}else {
				return ResponseUtil.getResponseObject(0, null,"商品不存在或价格不对");
			}
		}else {
			order.setpName("功德箱");
		}
		
		if(StringUtils.isEmpty(order.getUserId())) {
			return ResponseUtil.getResponseObject(0, null,"用户未登录");
		}
		String dt = sdf.format(new Date());
		order.setOrderNo(dt.substring(0,4)+System.currentTimeMillis()+dt.substring(5,10).replace("-", ""));
		order.setCreateDt(dt);
		order.setUpdateDt(dt);
		order.setStatus(0);
		try {
			if(order.getPayType()==0) {//支付宝
				String orderStr = aliPayUtil.getOrderString(order.getOrderNo(),order.getpName(), order.getOrderPrice());
				order.setOrderStr(URLDecoder.decode(orderStr,"utf-8"));
			}else if(order.getPayType()==1){//微信
				Map<String, String> map = wxPayUtil.weixinPay(order);
				if(map != null) {
					order.setOrderStr(wxPayUtil.transMapToString(map));
				}	
			} else {
				order.setOrderStr("apple pay");
			}
		}catch (Exception e) {
				e.printStackTrace();
				return ResponseUtil.getResponseObject(0, null,"签名错误");
		}
		
		appOrderRepository.save(order);
		
		
		return ResponseUtil.getResponseObject(1, order, "success");
	}
	
	@RequestMapping("/list")
	public Map<String,Object> list(long userId,int orderType,int start){
		
		int count = appOrderRepository.countAppOrderByUidType(userId, orderType);
		List<AppOrder> list = new ArrayList<AppOrder>();
		list = appOrderRepository.getAppOrderByUidType(userId, orderType, (start-1)*PageUtil.PAGE_SIZE,PageUtil.PAGE_SIZE);
		
		return ResponseUtil.getResponseMap(1, count,list, "success");
	}
	
	@RequestMapping("/paylist")
	public Map<String,Object> paylist(int type){
		List<AppOrder> list = appOrderRepository.getAppOrderByStatusType(type);
		int len = list.size();
		List<PayUser> userList = new ArrayList<PayUser>();
		for(int i=0; i<len;i++) {
			PayUser user = new PayUser();
			AppOrder order = list.get(i);
			AppUser appUser = appUserRepository.findOne(order.getUserId());
			user.setUserName(appUser.getNickName());
			user.setPayMoney(order.getOrderPrice());
			
			userList.add(user);
		}
		int userLen = userList.size();
		if(userLen< 40) {
			Random random = new Random();
			int rand = random.nextInt(userLen==0?1:userLen);
				for(int i=rand;i<rand+(40-userLen);i++) {
					PayUser user = new PayUser();
					user.setUserName(name[i]);
					user.setPayMoney(money[random.nextInt(8)]);
					userList.add(user);
				}
		}
		
		return ResponseUtil.getResponseMap(1, userList.size(),userList, "success");
	}
	
	@RequestMapping("/ali_payback")
	public String aliPayBack(HttpServletRequest request,AliPayOrderBack payOrderBack) {
		Map<String, String> paramsMap = new HashMap<String,String>();
		Enumeration<String> reqParameters = request.getParameterNames();
		String paraName = "";
		while(reqParameters.hasMoreElements()) {
			paraName = reqParameters.nextElement();
			//if(!paraName.equals("sign") && !paraName.equals("sign_type")) {
			paramsMap.put(paraName, request.getParameter(paraName));
			//}
		}
		try {
			String paramStr = URLDecoder.decode(paramsMap.toString(),"utf_8");
			logger.info("paramStr="+paramStr);
			
			if(!aliPayUtil.validateOrderBack(paramsMap)){
				return "error,验签不过";
			}
			String orderNo = payOrderBack.getOut_trade_no();
			AppOrder order = appOrderRepository.getAppOrderByOrderNo(orderNo);
			if(order == null || payOrderBack.getTotal_amount() != order.getOrderPrice()
					|| order.getStatus() == 1) {
				return "error,通知订单异常或金额不对";
			}
			order.setStatus(1);
			order.setPayDt(payOrderBack.getNotify_time());
			order.setOutOrderNo(payOrderBack.getTrade_no());
			order.setUpdateDt(sdf.format(new Date()));
			 
			appOrderRepository.save(order);
			return "success";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		    return "error,解析异常";
		}
		
	}
	@RequestMapping("/wx_paybackm")
	public void wxPayBackMock() {
		Map<String,String> map = new HashMap<String,String>();
		map.put("appid", "wx2421b1c4370ec43b");
		map.put("attach", "支付测试");
		map.put("mch_id", "10000100");
		map.put("nonce_str", "5d2b6c2a8db53831f7eda20af46e531c");
		map.put("return_code", "SUCCESS");
		map.put("total_fee", "1");
		map.put("transaction_id", "1004400740201409030005092168");
		wxPayUtil.postHttp(map);
	}
	@RequestMapping("/wx_payback")
	public String wxPayBack(HttpServletRequest request) {
		ServletInputStream instream=null;
		try {
			Map<String, String> paramsMap = new HashMap<String,String>();
			Enumeration<String> reqParameters = request.getParameterNames();
			String paraName = "";
			StringBuffer sb = new StringBuffer();  
			while(reqParameters.hasMoreElements()) {
				paraName = reqParameters.nextElement();
				//if(!paraName.equals("sign") && !paraName.equals("sign_type")) {
				sb.append(paraName).append("=");
				paramsMap.put(paraName, request.getParameter(paraName));
				sb.append(paramsMap.get(paraName));
				//}
			}

			
	        SortedMap<String,String> map = WXRequestUtil.doXMLParseWithSorted(sb.toString());//接受微信的通知参数 
	        logger.info("paramStr="+wxPayUtil.transMapToString(map));
	        String returnCode = map.get("return_code");
	        if(StringUtils.isEmpty(returnCode)) {
	        		return "fails";
	        }
	        String orderNo = map.get("out_trade_no");
	        String totalFee = map.get("total_fee");
	        String transactionId = map.get("transaction_id");
			AppOrder order = appOrderRepository.getAppOrderByOrderNo(orderNo);
			if(order == null || !totalFee.equals(order.getOrderPrice()) || order.getStatus() == 1) {
				return "error,通知订单异常或金额不对";
			}
			order.setStatus(1);
			order.setPayDt(sdf.format(new Date()));
			order.setOutOrderNo(transactionId);
			order.setUpdateDt(sdf.format(new Date()));
			 
			appOrderRepository.save(order);
			return "<xml>\n" + 
					"\n" + 
					"  <return_code><![CDATA[SUCCESS]]></return_code>\n" + 
					"  <return_msg><![CDATA[OK]]></return_msg>\n" + 
					"</xml>";
	        
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			if(instream != null) {
				try {
					instream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
        
		return "";
	}
	
	@RequestMapping("/ap_verify")
	public Map<String,Object> applePayVerify(String orderNo,String productId,String status,String receipt,String transanId){
		logger.info("apple pay verify,orderNo="+orderNo+"，status="+status+",transanId="+transanId+",receipt="+receipt);
		if(StringUtils.isEmpty(status) || !"Purchased".equals(status)) {
			logger.error("apple pay err,orderNo="+orderNo+",status="+status);
			return ResponseUtil.getResponseObject(0, null, "验证订单未支付");
		}
		//receipt="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";
		
		executorchange.execute(new ApplePayVerifyThread(payConfig,orderNo,receipt,transanId,appOrderRepository));
		return ResponseUtil.getResponseObject(1, null, "sucess");
	}
}
