package com.jack.kxb.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jack.kxb.dao.KxPrizeRepository;
import com.jack.kxb.dao.KxShareRepository;
import com.jack.kxb.dao.KxSmashEggRepository;
import com.jack.kxb.dao.KxUserRepository;
import com.jack.kxb.model.KxPrize;
import com.jack.kxb.model.KxShare;
import com.jack.kxb.model.KxSmashEgg;
import com.jack.kxb.model.KxUser;
import com.jack.kxb.util.AesException;
import com.jack.kxb.util.HttpUtil;
import com.jack.kxb.util.MD5Util;
import com.jack.kxb.util.PrizeUtil;
import com.jack.kxb.util.ResponseUtil;
import com.jack.kxb.util.WxUtil;

@Controller
public class SmashController {
	private Logger logger = LoggerFactory.getLogger("SmashController");
	@Autowired
	private KxUserRepository kxUserRepository;
	@Autowired
	private KxSmashEggRepository kxSmashEggRepository;
	@Autowired
	private KxShareRepository kxShareRepository;
	@Autowired
	private KxPrizeRepository kxPrizeRepository;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat sdfdt = new SimpleDateFormat("yyyy-MM-dd");
	private ConcurrentHashMap<String,List<KxPrize>> listMap = new ConcurrentHashMap<String,List<KxPrize>>();
	private static Map<String,String> tdjOpenIds = new HashMap<String,String>();
	static {
		tdjOpenIds.put("12345", "12345");
		tdjOpenIds.put("123456", "123456");
	}
	@RequestMapping("/smash/index")
	/**
	 * 引导从定向微信授权url
	 * 以snsapi_base为scope的网页授权，就静默授权的，用户无感知
	 * 以snsapi_userinfo为scope发起的网页授权，是用来获取用户的基本信息，需要用户手动同意
	 * @return
	 */
	public String index() {
		//TODO根据前端code，获取accessToken、openId
		
		//根据accessToken、openId获取公众号关注信息
		
		//未关注需要弹关注的二维码
		
		//关注后，直接返回活动页面，并返回可砸次数
		
		return "/index";
	}
	
	@ResponseBody
	@RequestMapping("/smash/wxverf")
	public String wxverf(String signature,String timestamp,String nonce,String echostr){
		logger.info("signature={},timestamp={},nonce={},echoStr={}",signature,timestamp,nonce,echostr);
		try {
			String sign = MD5Util.getSHA1(timestamp, nonce, "");
			if(sign.equals(signature)) {
				return echostr;
			}
		} catch (AesException e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	/**
	 * 微信授权后进入活动页
	 * @param map
	 * @param code
	 * @return
	 */
	@RequestMapping("/smash/wxauth")
	public String wxauth(ModelMap map ,String code){
		logger.info("code={}",code);
		if(StringUtils.isEmpty(code)) {
			return "redirect:/smash/index.do";
		}
		String url = WxUtil.GET_OPENID_URL+"&appid="+WxUtil.APP_ID+"&secret="+WxUtil.KX_SECRET+"&code="+code;
		String resp = HttpUtil.sendGet(url);
		logger.info("get open_id,url={},resp = {}",url,resp);
		JSONObject json = new JSONObject(resp);
		int subscribe = 0;
		if(json.isNull("errcode")) {
			String openId = json.getString("openid");
			
			url = WxUtil.GET_ACCESS_TOKEN_URL+"&appid="+WxUtil.APP_ID+"&secret="+WxUtil.KX_SECRET;
			resp = HttpUtil.sendGet(url);
			logger.info("get access_token,url={},resp = {}",url,resp);
			json = new JSONObject(resp);
			if(json.isNull("errcode")) {
				String accessToken = json.getString("access_token");
				logger.info("openid={},accessToken = {}",openId,accessToken);
				
				if(!StringUtils.isEmpty(openId) && !StringUtils.isEmpty(accessToken)) {
					url = WxUtil.GET_USER_INFO+"&access_token="+accessToken+"&openid="+openId;
					resp = HttpUtil.sendGet(url);
					logger.info("get userinfo,url={},resp = {}",url,resp);
					json = new JSONObject(resp);
					if(json.isNull("errcode")) {
						subscribe = json.getInt("subscribe");
						logger.info("subscribe={},openid={},accessToken = {}",subscribe,openId,accessToken);
						if(subscribe != 0) {
							map.put("subscribe", subscribe);
							map.put("openId", openId);
							
							
							KxUser kxUser = kxUserRepository.getKxUserByOpenId(openId);
							if(kxUser == null) {
								kxUser = new KxUser();
								
								kxUser.setOpenId(openId);
								kxUser.setNickName(json.getString("nickname"));
								kxUser.setHeadImg(json.getString("headimgurl"));
								kxUser.setCreateDt(sdf.format(new Date()));
								kxUserRepository.save(kxUser);
							}else {
								logger.info("user has subscribed,openid={}",openId);
							}
						}else {
							map.put("subscribe", subscribe);
							map.put("qrcode", "http://www.jinxinsenhui.com/qrcode_ekxb.jpg");
							logger.info("user not subscribed,openid={}",openId);
						}
					}
					
					url = WxUtil.JS_API_URL+accessToken;
					resp = HttpUtil.sendGet(url);
					logger.info("get js ticket,url={},resp = {}",url,resp);
					json = new JSONObject(resp);
					if(json.getInt("errcode")==0) {
						String jsticket = json.getString("ticket");
						map.put("jsapi_ticket", jsticket);
						String noncestr="Wm3WZYTPz0wzccnW";
						map.put("noncestr", noncestr);
						long timestamp = System.currentTimeMillis();
						map.put("timestamp", timestamp);
						String pageUrl = "http://www.jinxinsenhui.com/smash/wxauth.do?code="+code+"&state=123";
						map.put("url", pageUrl);
						
						Map<String, String> sortedParams = new HashMap<String,String>();
						sortedParams.put("jsapi_ticket", jsticket);
						sortedParams.put("noncestr", noncestr);
						sortedParams.put("timestamp", timestamp+"");
						sortedParams.put("url", pageUrl);
						try {
							String sortParam = MD5Util.getSignContent(sortedParams);
							String sign = MD5Util.getSHA1(sortParam);
							logger.info("get jsticket,sortParam={},sign={}",sortParam,sign);
							map.put("sign", sign);
						} catch (AesException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return "/activity";
	}
	
	@ResponseBody
	@RequestMapping("/smash/zha")
	public Map<String, Object> smashEgg(ModelMap map ,String openId){
		logger.info("smash egg,openId={}",openId);
		if(StringUtils.isEmpty(openId)) {
			return ResponseUtil.getResponseObject(0, null, "非法用户");
		}
		KxUser kxUser = kxUserRepository.getKxUserByOpenId(openId);
		if(kxUser == null) {
			return ResponseUtil.getResponseObject(0, null, "非法用户");
		}
		String dt = sdfdt.format(new Date());
		Integer cnt = kxSmashEggRepository.cntKxSmashEggListByOpenIdAndSmashDt(openId,dt );
		if(cnt == null) {
			return ResponseUtil.getResponseObject(0, null, "系统异常");
		}
		if(cnt >= 2) {
			return ResponseUtil.getResponseObject(100, null, "今日抽奖机会已用完");
		}
		if(cnt == 1) {
			KxShare kxShare = kxShareRepository.getKxShareByOpenIdAndShareDt(openId, dt);
			if(kxShare == null) {
				return ResponseUtil.getResponseObject(101, null, "分享后才能再抽奖哦！");
			}
		}
		
		//计算中奖
		int status = 0;
		int winLevel = 0;
		List<KxPrize> list = listMap.get("prize_list");
		if(list == null || list.size()==0) {
			list =(ArrayList<KxPrize>) kxPrizeRepository.findAll();
		}
		if(list == null || list.size() == 0) {
			return ResponseUtil.getResponseObject(102, null, "未设置抽奖列表");
		}
		int index = PrizeUtil.getPrizeIndex(list);
		status = 1;
		KxPrize kxPrize = list.get(index);
		logger.info("smash egg,openId={}，winLevel={},prizeCnt={}",openId,kxPrize.getPrizeLevel(),kxPrize.getPrizeCnt());
		if(kxPrize.getPrizeCnt()>0) {
			winLevel = kxPrize.getPrizeLevel();
			if(winLevel == 1 && !tdjOpenIds.containsKey(openId)) {//非指定用户中特等奖，降级阳光普照奖
				kxPrize = list.get((System.currentTimeMillis()/2==0)?6:5);
				winLevel = kxPrize.getPrizeLevel();
			}
		}else {
			kxPrize = list.get((System.currentTimeMillis()/2==0)?6:5);
			winLevel = kxPrize.getPrizeLevel();
		}
		logger.info("smash egg,openId={}，winLevel={}",openId,winLevel);
		
		//返回中奖等级
		KxSmashEgg smashEgg = new KxSmashEgg();
		smashEgg.setOpenId(openId);
		smashEgg.setSmashDt(dt);
		smashEgg.setStatus(status);
		smashEgg.setWinLevel(winLevel);
		smashEgg.setCreateDt(sdf.format(new Date()));
		
		KxSmashEgg egg = kxSmashEggRepository.save(smashEgg);
		map.put("openId", openId);
		map.put("status", status);
		map.put("winLevel", winLevel);
		map.put("winId", egg.getId());
		return ResponseUtil.getResponseObject(1, map, "success");
	}
}
