package com.wuyou.shop.controller;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 
* @author  jie
* @date 创建时间：2019年4月15日 下午4:58:00 
* @version 1.0  
* @return  
*/
@RestController
@RequestMapping("/shopLogin")
public class ShopLoginController {
	@RequestMapping("name")
	public Map name(){
		String name=SecurityContextHolder.getContext().getAuthentication().getName();
		Map map=new HashMap();
		map.put("loginName", name);
		return map ;
	}	
}

