package com.jack.kxb.admin.controller;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jack.kxb.dao.KxPrizeRepository;
import com.jack.kxb.dao.KxQrRepository;
import com.jack.kxb.model.KxPrize;
import com.jack.kxb.util.FileUtil;
import com.jack.kxb.util.ResponseUtil;


@Controller
public class KxPrizeController {
	private static Logger logger = LoggerFactory.getLogger("KxPrizeController");
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private KxPrizeRepository kxPrizeRepository;
	@Autowired
	private KxQrRepository kxQrRepository;
	@RequestMapping("/admin/prize/list")
	public String list(ModelMap map ,KxPrize prize) {
		List<KxPrize> list = (ArrayList<KxPrize>)kxPrizeRepository.findAll();
		map.put("prize", prize);
	    
		map.put("list", list);
	
		return "prize/list";
	}
	
	@RequestMapping("/admin/prize/predit")
	public String preditAppProduct(ModelMap map,KxPrize prize) {
		long id = prize.getId();
		if(id!=0) {
			prize = kxPrizeRepository.findOne(id);
			map.put("p", prize);
		}
		
		return "prize/edit";
	}
	
	
	@ResponseBody
	@RequestMapping("/admin/prize/save")
	public void save(KxPrize kxPrize ,HttpServletResponse response) {
		long id = kxPrize.getId();
		if(id != 0) {
			KxPrize prize = kxPrizeRepository.findOne(id);
			if(prize != null) {
				BeanUtils.copyProperties(kxPrize, prize);
				prize.setUpdateDt(sdf.format(new Date()));
				
				kxPrizeRepository.save(prize);
			}
		}else {
			kxPrize.setCreateDt(sdf.format(new Date()));
			kxPrize.setUpdateDt(sdf.format(new Date()));
			kxPrizeRepository.save(kxPrize);
		}
		
		 Map<String, Object> json = new HashMap<String, Object>();
		 json.put("error", 0);
         json.put("url", "");
         
 		
         ResponseUtil.response(json, response);
	}
	
	@RequestMapping("/admin/prize/qr")
	public String qr(ModelMap map) {
		return "prize/qr";
	}
	
	@RequestMapping("/admin/prize/saveqr")
	public void saveqr(@RequestParam MultipartFile uploadzip,HttpServletResponse response) {
		Map<String, Object> json = new HashMap<String, Object>();
		try {
			String fileName = FileUtil.uplaodFile(uploadzip);
            json.put("error", 0);
            json.put("url", fileName);
            logger.info("upload qr,path={}"+fileName);
    		
            FileUtil.unZip(fileName,kxQrRepository);
            ResponseUtil.response(json, response);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", 1);
            json.put("url", "");
            
    		
            ResponseUtil.response(json, response);
        }
	}
}
