package com.jack.kxb.controller;

import java.text.ParseException;
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
import com.jack.kxb.dao.KxQrRepository;
import com.jack.kxb.dao.KxShareRepository;
import com.jack.kxb.dao.KxSmashEggRepository;
import com.jack.kxb.dao.KxUserRepository;
import com.jack.kxb.dao.KxWinningRepository;
import com.jack.kxb.model.KxPrize;
import com.jack.kxb.model.KxQr;
import com.jack.kxb.model.KxShare;
import com.jack.kxb.model.KxSmashEgg;
import com.jack.kxb.model.KxUser;
import com.jack.kxb.model.KxWinning;
import com.jack.kxb.util.ActConfigUtil;
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
	private KxQrRepository kxQrRepository;
	@Autowired
	private KxPrizeRepository kxPrizeRepository;
	@Autowired
	private KxWinningRepository kxWinningRepository;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat sdfdt = new SimpleDateFormat("yyyy-MM-dd");
	private ConcurrentHashMap<String,List<KxPrize>> listMap = new ConcurrentHashMap<String,List<KxPrize>>();
	/*private static Map<String,String> tdjOpenIds = new HashMap<String,String>();
	private static Map<String,String> actStartEndDt = new HashMap<String,String>();
	static {
		tdjOpenIds.put("12345", "12345");
		tdjOpenIds.put("123456", "123456");
		actStartEndDt.put("sdt", "2018-04-24 00:00:00");
		actStartEndDt.put("edt", "2018-04-30 23:59:59");
		
	}*/
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
	@RequestMapping("/smash/resetOpId")
	public Map<String, Object> setTdjOpenId(String openIds,String sdt,String edt) {
		logger.info("setTdjOpenId,openids={},sdt={},edt={}",openIds,sdt,edt);
		if(StringUtils.isEmpty(openIds)) {
			return ResponseUtil.getResponseObject(0, null, "ids is null");
		}
		
		ActConfigUtil.tdjOpenIds.clear();
		String[] aryOpenId = openIds.split(",");
		for(String id : aryOpenId) {
			ActConfigUtil.tdjOpenIds.put(id, id);
		}
		
		ActConfigUtil.actStartEndDt.clear();
		ActConfigUtil.actStartEndDt.put("sdt", sdt+" 00:00:00");
		ActConfigUtil.actStartEndDt.put("edt", edt+" 23:59:59");
		
		return ResponseUtil.getResponseObject(1, openIds, "ids set success");
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
		map.put("vtype", 0);
		
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
							map.put("vtype", 1);		
							
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
			}else {
				map.put("openId", openId);
				map.put("jsapi_ticket", "");
				String noncestr="Wm3WZYTPz0wzccnW";
				map.put("noncestr", noncestr);
				long timestamp = System.currentTimeMillis();
				map.put("timestamp", timestamp);
				map.put("url", "");
				map.put("sign", "");
			}
		}
		List<KxWinning> winList = kxWinningRepository.getKxWinningGroupByUser();
		map.put("winList", winList);
		
		return "/activity";
	}
	
	@ResponseBody
	@RequestMapping("/smash/zha")
	public Map<String, Object> smashEgg(ModelMap map ,String openId,Integer vtype){
		logger.info("smash egg,openId={},vtype={}",openId,vtype);
		if(vtype == null) {
			vtype = 0 ;
		}
		Date date = new Date();
		try {
			Date sDate = sdf.parse(ActConfigUtil.actStartEndDt.get("sdt") +" 00:00:00");
			Date eDate = sdf.parse(ActConfigUtil.actStartEndDt.get("edt")+" 23:59:59");
			if(date.compareTo(sDate) < 0 ) {
				return ResponseUtil.getResponseObject(103, null, "活动尚未开始");
			}
			if(date.compareTo(eDate) > 0){
				return ResponseUtil.getResponseObject(104, null, "活动已结束");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
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
		
		if(vtype==1) {
			if(cnt == 1) {
				KxShare kxShare = kxShareRepository.getKxShareByOpenIdAndShareDt(openId, dt);
				if(kxShare == null) {
					return ResponseUtil.getResponseObject(101, null, "分享后才能再抽奖哦！");
				}
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
		if(ActConfigUtil.tdjOpenIds.containsKey(openId)) {//指定用户第一次中奖
			Integer winCnt = kxSmashEggRepository.cntKxSmashEggListByOpenIdAndWinLevel(openId, 1);
			if(winCnt == 0 ) {
				kxPrize = list.get(0);
				winLevel = 1;
			}
			
		}
		logger.info("smash egg,openId={}，winLevel={},prizeCnt={}",openId,kxPrize.getPrizeLevel(),kxPrize.getPrizeCnt());
		
		if(kxPrize.getPrizeCnt()>0) {
			winLevel = kxPrize.getPrizeLevel();
			if(winLevel == 1 && !ActConfigUtil.tdjOpenIds.containsKey(openId)) {//非指定用户中特等奖，降级阳光普照奖
				kxPrize = list.get(7);
				winLevel = kxPrize.getPrizeLevel();
			}else if(winLevel == 1){//指定用户非第一次中奖，降级未中奖
				Integer winCnt = kxSmashEggRepository.cntKxSmashEggListByOpenIdAndWinLevel(openId, 1);
				if(winCnt > 0 ) {
					kxPrize = list.get(7);
					winLevel = kxPrize.getPrizeLevel();
				}
			}
		}else {
			kxPrize = list.get(7);
			winLevel = kxPrize.getPrizeLevel();
		}
		logger.info("smash egg,openId={}，winLevel={}",openId,winLevel);
		
		//返回中奖等级
		KxSmashEgg smashEgg = new KxSmashEgg();
		smashEgg.setOpenId(openId);
		smashEgg.setSmashDt(dt);
		if(winLevel==7) {
			smashEgg.setStatus(0);
		}else {
			smashEgg.setStatus(1);
		}
		smashEgg.setWinLevel(winLevel);
		smashEgg.setCreateDt(sdf.format(new Date()));
		
		KxSmashEgg egg = kxSmashEggRepository.save(smashEgg);
		map.put("openId", openId);
		map.put("status", status);
		map.put("winLevel", winLevel);
		map.put("winId", egg.getId());
		map.put("qrUrl", "");
		
		if(winLevel == 5) {
			KxQr qr = kxQrRepository.getKxQrByStatusLimit();
			if(qr != null) {
				map.put("qrUrl", qr.getQrUrl());
				qr.setStatus(1);
				qr.setOpenId(openId);
				kxQrRepository.save(qr);
			}
		}
		
		return ResponseUtil.getResponseObject(1, map, "success");
	}
}
