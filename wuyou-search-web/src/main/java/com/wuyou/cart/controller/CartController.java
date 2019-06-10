package com.wuyou.cart.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.wuyou.cart.service.CartService;

import entity.Cart;
import entity.Result;

/** 
* @author  jie
* @date 创建时间：2019年5月5日 
* @version 1.0  
* @return  
*/
@RestController
@RequestMapping("/cart")
public class CartController {
	//引入服务
	@Reference(timeout=6000)
	private CartService cartService;
	
	//注入
	@Autowired
	private  HttpServletRequest request;
	
	@Autowired
	private  HttpServletResponse response;
	
	
	/**
	 * 购物车列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/findCartList")
	public List<Cart> findCartList(){	
		//得到登陆人账号,判断当前是否有人登陆
		String username = SecurityContextHolder.getContext().getAuthentication().getName(); 
		System.out.println("当前登录人："+username);
		
		//获取cookie
		String cartListString = util.CookieUtil.getCookieValue(request, "cartList","UTF-8");
		if(cartListString==null || cartListString.equals("")){
			cartListString="[]";
		}
		List<Cart> cartList_cookie = JSON.parseArray(cartListString, Cart.class);
		
		if(username.equals("anonymousUser")){//如果未登录
			//从cookie中提取购物车
			System.out.println("从cookie中获取购物车");
			return cartList_cookie;	
		}else{
			//如果已登录,从redis中提取					
			List<Cart> cartList_redis =cartService.findCartListFromRedis(username);				
			
			if(cartList_cookie.size()>0){//如果本地存在购物车
				//合并购物车
				cartList_redis=cartService.mergeCartList(cartList_redis, cartList_cookie);	
				//清除本地cookie的数据
				util.CookieUtil.deleteCookie(request, response, "cartList");
				//将合并后的数据存入redis 
				cartService.saveCartListToRedis(username, cartList_redis); 
				System.out.println("执行合并购物车");
			}			

			return cartList_redis;
		}					
	}
	
	/**
	 * 添加商品到购物车
	 * @param request
	 * @param response
	 * @param itemId
	 * @param num
	 * @return
	 */
	@RequestMapping("/addGoodsToCartList")
	public Result addGoodsToCartList(Long itemId,Integer num){
		
		//得到登陆人账号,判断当前是否有人登陆
		String username = SecurityContextHolder.getContext().getAuthentication().getName(); 
		System.out.println("当前登录用户："+username);

		try {
			//获取购物车列表
			List<Cart> cartList =findCartList();
			
			//调用服务操作购物车
			cartList = cartService.addGoodsToCartList(cartList, itemId, num);	
			
			if(username.equals("anonymousUser")){ 
				//如果是未登录，保存到cookie
				util.CookieUtil.setCookie(request, response, "cartList", JSON.toJSONString(cartList),3600*24 ,"UTF-8");
				System.out.println("向cookie存入数据");
			}else{//如果是已登录，保存到redis
				cartService.saveCartListToRedis(username, cartList);			
			}
			return new Result(true, "存入购物车成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "存入购物车失败");
		}
	}	
}


