package com.jack.kxb.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jack.kxb.dao.KxPrizeRepository;
import com.jack.kxb.dao.KxSmashEggRepository;
import com.jack.kxb.dao.KxUserRepository;
import com.jack.kxb.dao.KxWinningRepository;
import com.jack.kxb.model.KxPrize;
import com.jack.kxb.model.KxSmashEgg;
import com.jack.kxb.model.KxUser;
import com.jack.kxb.model.KxWinning;
import com.jack.kxb.util.ExcelUtil;
import com.jack.kxb.util.PageUtil;
import com.jack.kxb.util.ResponseUtil;

@Controller
public class KxWinningController {
	private Logger logger = LoggerFactory.getLogger("KxWinningController");
	@Autowired
	private KxUserRepository kxUserRepository;
	@Autowired
	private KxSmashEggRepository kxSmashEggRepository;
	@Autowired
	private KxWinningRepository kxWinningRepository;
	@Autowired
	private KxPrizeRepository kxPrizeRepository;
	
	@Autowired
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@ResponseBody
	@RequestMapping("/smash/winning")
	public Map<String, Object> save(KxWinning winning){
		logger.info("save winnig,name={},phone={},address={}",winning.getName(),winning.getPhone(),winning.getAddress());
		if(StringUtils.isEmpty(winning.getName()) || StringUtils.isEmpty(winning.getPhone())) {
			return ResponseUtil.getResponseObject(0, null, "手机号、姓名必填");
		}
		KxUser kxUser =kxUserRepository.getKxUserByOpenId(winning.getOpenId());
		if( StringUtils.isEmpty(winning.getOpenId()) || kxUser == null) {
			return ResponseUtil.getResponseObject(102, null, "非法用户");
		}
		KxSmashEgg kxSmashEgg = kxSmashEggRepository.findOne(winning.getWinId());
		if(StringUtils.isEmpty(winning.getWinId()) || kxSmashEgg == null || 
				!kxSmashEgg.getOpenId().equals(winning.getOpenId()) || 1!=kxSmashEgg.getStatus()) {
			return ResponseUtil.getResponseObject(103, null, "非法中奖信息");
		}
		KxWinning kxWinning = kxWinningRepository.getKxWinningByOpenIdAndWinId(winning.getOpenId(), winning.getWinId());
		if(kxWinning != null) {
			return ResponseUtil.getResponseObject(104, null, "中奖信息已提交");
		}
		
		KxSmashEgg smashEgg = kxSmashEggRepository.findOne(winning.getWinId());
		if(smashEgg != null) {
			winning.setWinLevel(smashEgg.getWinLevel());
		}
		winning.setCreateDt(sdf.format(new Date()));
		kxWinningRepository.save(winning);
		
		KxPrize kxPrize =kxPrizeRepository.getKxPrizeByPrizeLevel(kxSmashEgg.getWinLevel());
		if(kxPrize != null) {
			kxPrize.setPrizeCnt(kxPrize.getPrizeCnt()-1);
			kxPrizeRepository.save(kxPrize);
		}else {
			logger.info("kxPrize is null,winId={}"+winning.getWinId());
		}
		return ResponseUtil.getResponseObject(1, winning, "save succuess");
		
	}
	
	@RequestMapping("/admin/winning/list")
	public String list(ModelMap map ,HttpServletRequest request,KxWinning kxWinning,Integer start) {
		int page = start==null?1:start.intValue();
		int count = kxWinningRepository.cntKxWinningByCond(kxWinning);
		List<KxWinning> list = new ArrayList<KxWinning>();
		list = kxWinningRepository.getKxWinningByCond(kxWinning, (page-1)*PageUtil.PAGE_SIZE,PageUtil.PAGE_SIZE);
		if(list != null && list.size() > 0) {
			for(KxWinning winning : list) {
				KxSmashEgg kxSmashEgg = kxSmashEggRepository.findOne(winning.getWinId());
				if(kxSmashEgg != null) {
					winning.setWinLevel(kxSmashEgg.getWinLevel());
				}
			}
		}
		map.put("count", count);
		map.put("list", list);
		map.put("kxWinning", kxWinning);
		map.put("pageCount", PageUtil.getPage(count, PageUtil.PAGE_SIZE));
		map.put("pageNow", page);
		request.setAttribute("pageName", page);
		request.setAttribute("pageCount", PageUtil.getPage(count, PageUtil.PAGE_SIZE));
	
		return "win/list";
	}
	
	@ResponseBody
	@RequestMapping("/admin/winning/export")
	public void export(ModelMap map ,HttpServletResponse response,HttpServletRequest request,KxWinning kxWinning,Integer start) {
		List<KxWinning> list = new ArrayList<KxWinning>();
		if(start == null) {
			start = 0;
		}
		list = kxWinningRepository.getKxWinningByCond(kxWinning, start, 60000);
		
		List<String> titleList = new ArrayList<String>();
		titleList.add("姓名");
		titleList.add("手机号");
		titleList.add("中奖级别");
		titleList.add("中奖时间");
		
		Workbook wb = new HSSFWorkbook();
		ExcelUtil.exportExcel(wb,"test.xls",list,titleList);
		response.setContentType("application/vnd.ms-excel");     
		response.setHeader("Content-disposition", "attachment;filename=" + "test.xls");  
		OutputStream ouputStream = null;
		try {
			ouputStream = response.getOutputStream();
			wb.write(ouputStream);     
			ouputStream.flush();     
			ouputStream.close();   
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(ouputStream!=null) {
				try {
					ouputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}   
			}
		}  
	}
	
	@RequestMapping("/smash/winning/list")
	public String userWinningList(ModelMap map ,String openId) {
		logger.info("userWinningList openid = {}",openId);
		List<KxWinning> list = kxWinningRepository.getKxWinningByOpenId(openId);
		map.put("list", list);
	
		return "win/user_list";
	}
}
