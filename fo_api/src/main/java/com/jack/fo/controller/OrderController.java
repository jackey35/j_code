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
import com.jack.fo.util.AliPayUtil;
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
	
	private static final int PAGE_SIZE=10;
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
			if(order.getPayType()==0) {
				String orderStr = aliPayUtil.getOrderString(order.getOrderNo(),order.getpName(), order.getOrderPrice());
				order.setOrderStr(URLDecoder.decode(orderStr,"utf-8"));
			}else {
				Map<String, String> map = wxPayUtil.weixinPay(order);
				if(map != null) {
					order.setOrderStr(wxPayUtil.transMapToString(map));
				}	
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
		list = appOrderRepository.getAppOrderByUidType(userId, orderType, (start-1)*PAGE_SIZE,PAGE_SIZE);
		
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
}
