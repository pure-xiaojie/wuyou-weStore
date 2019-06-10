package com.wuyou.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 
* @author  jie
* @date 创建时间：2019年5月2日 
* @version 1.0  
* @return  
*/
@RestController
@RequestMapping("/login")
public class LoginController {	
	@RequestMapping("/name")
	public Map showName(){
		String name = SecurityContextHolder.getContext().getAuthentication().getName();//得到登陆人账号
		Map map=new HashMap<>();
		map.put("loginName", name);
		return map;		
	}	
}


