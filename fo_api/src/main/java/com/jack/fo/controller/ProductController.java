package com.jack.fo.controller;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jack.fo.dao.AppProductRepository;
import com.jack.fo.model.AppProduct;
import com.jack.fo.util.FileUtil;
import com.jack.fo.util.PayConfig;
import com.jack.fo.util.ResponseUtil;

@Controller
public class ProductController {
	@Autowired
	private AppProductRepository appProductRepository;
	@Autowired
	private PayConfig payConfig;
	
	@ResponseBody
	@RequestMapping("/p/list")
	public Map<String,Object> getProductByCateCode(int cateCode){
		List<AppProduct> list = appProductRepository.getAppProductByCateCode(cateCode);
		
		return ResponseUtil.getResponseMap(1, (list!=null&&list.size()>0)?list.size():0,list,"success");
	} 
	
	@RequestMapping("/admin/p/list")
	public String getProductList(ModelMap map ,AppProduct product){
		List<AppProduct> list =  (ArrayList<AppProduct>)appProductRepository.getAppProductByPname(product);
		map.put("product", product);
	    
		map.put("list", list);
	
		return "product/list";
	} 
	
	@ResponseBody
	@RequestMapping("/admin/p/save")
	public void saveAppProduct(AppProduct appProduct,HttpServletResponse response) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		appProduct.setCreateDt(sdf.format(new Date()));
		appProduct.setUpdateDt(sdf.format(new Date()));
		appProduct.setStatus(1);
		appProductRepository.save(appProduct);
		
		 Map<String, Object> json = new HashMap<String, Object>();
		 json.put("error", 0);
         json.put("url", "");
         
 		
         ResponseUtil.response(json, response);
	}
	
	@RequestMapping("/admin/p/predit")
	public String preditAppProduct(ModelMap map,AppProduct product) {
		long id = product.getId();
		if(id!=0) {
			product = appProductRepository.findOne(id);
			map.put("p", product);
		}
		
		return "product/edit";
	}
	
	@ResponseBody
	@RequestMapping("/admin/p/upload")
	public void mbUpload(@RequestParam MultipartFile image,HttpServletResponse response) {
		 Map<String, Object> json = new HashMap<String, Object>();
		try {
			String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			FileUtil.uplaodFile(image, payConfig,fileName);
			String fileSuff = FilenameUtils.getExtension(image.getOriginalFilename());
           
           
            json.put("error", 0);
            json.put("url", "http://www.5818cp.com/images/"+fileName+"."+fileSuff);
            
    		
            ResponseUtil.response(json, response);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", 1);
            json.put("url", "");
            
    		
            ResponseUtil.response(json, response);
        }
	}
	
	@ResponseBody
	@RequestMapping("/admin/p/update")
	public void update(long id,int status,HttpServletResponse response) {
		Map<String, Object> json = new HashMap<String, Object>();
		if(status == 2) {
			appProductRepository.delete(id);
			json.put("error", 0);
			json.put("boot", null);
			ResponseUtil.response(json, response);
			return ;
		}
		AppProduct product = appProductRepository.findOne(id);
		
		if(product != null) {
			product.setStatus(status);
			appProductRepository.save(product);
			json.put("error", 0);
			json.put("p", product);
		}else {
			json.put("error", 1);
			json.put("p", null);
		}
		 ResponseUtil.response(json, response);
	}
	
	@ResponseBody
	@RequestMapping("/admin/p/upriority")
	public void updatePriority(long id,int priority,HttpServletResponse response) {
		Map<String, Object> json = new HashMap<String, Object>();
		
		try {
			AppProduct product = appProductRepository.findOne(id);
			product.setPriority(priority);
			appProductRepository.save(product);
			json.put("error", 0);
			json.put("p", product);
		}catch(Exception e) {
			json.put("error", 1);
			json.put("p", null);
		}
		
		ResponseUtil.response(json, response);
	}
	
	@ResponseBody
	@RequestMapping("/p/get")
	public Map<String,Object> getAppProductById(Long id) {
		AppProduct product = appProductRepository.findOne(id);
		return ResponseUtil.getResponseObject(1, product, "success");
	}
}
