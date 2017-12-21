package com.jack.fo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jack.fo.dao.AppWishRepository;
import com.jack.fo.model.AppWish;
import com.jack.fo.util.ResponseUtil;

@RestController
@RequestMapping("/wish")
public class WishController {
	@Autowired
	private AppWishRepository appWishRepository;
	
	@RequestMapping("/list")
	public Map<String,Object> getAppBootConfigByType(long userId) {
		List<AppWish> list = appWishRepository.getAppWishByUid(userId);
		return ResponseUtil.getResponseObject(1, list, "");
	}
	
	@RequestMapping("/save")
	public Map<String ,Object> save(AppWish appWish) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long id = appWish.getId();
		AppWish wish = null;
		if(id == 0) {
			wish = appWish;
			wish.setStatus(1);
			wish.setCreateDt(sdf.format(new Date()));
			wish.setUpdateDt(sdf.format(new Date()));

			appWishRepository.save(wish);
		}else {
			wish = appWishRepository.findOne(id);
			if(wish != null && wish.getUserId()==appWish.getUserId()) {
				wish.setStatus(2);
				wish.setBackWish(appWish.getWish());
				wish.setUpdateDt(sdf.format(new Date()));
				appWishRepository.save(wish);
			}else {
				return ResponseUtil.getResponseObject(0, null, "非法用户修改");
			}
		}

		return ResponseUtil.getResponseObject(1, wish, "");
	}
}
