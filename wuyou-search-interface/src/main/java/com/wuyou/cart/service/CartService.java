package com.wuyou.cart.service;

import java.util.List;

import entity.Cart;

/** 
* @author  jie
* @date 创建时间：2019年5月4日 
* @version 1.0  
* @return  
*/
public interface CartService {
	
	/**
	 * 添加商品到购物车
	 * @param cartList
	 * @param itemId
	 * @param num
	 * @return
	 */
	public List<Cart> addGoodsToCartList(List<Cart> cartList,Long itemId,Integer num );
	
	/**
	 * 从redis中查询购物车
	 * @param username
	 * @return
	 */
	public List<Cart> findCartListFromRedis(String username);
	
	/**
	 * 将购物车保存到redis
	 * @param username
	 * @param cartList
	 */
	public void saveCartListToRedis(String username,List<Cart> cartList);
	
	/**
	 * 合并购物车
	 * @param cartList1
	 * @param cartList2
	 * @return
	 */
	public List<Cart> mergeCartList(List<Cart> cartList1,List<Cart> cartList2);

}

