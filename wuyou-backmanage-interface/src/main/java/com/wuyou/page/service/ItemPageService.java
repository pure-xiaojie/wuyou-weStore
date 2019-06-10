package com.wuyou.page.service;
/** 
* @author  jie
* @date 创建时间：2019年4月28日 
* @version 1.0  
* @return 商品详细页接口 
*/
public interface ItemPageService {
	/**
	 * 生成商品详细页
	 * @param goodsId
	 */
	public boolean genItemHtml(Long goodsId);
	
	/**
	 * 删除商品详细页
	 * @param goodsId
	 * @return
	 */
	public boolean deleteItemHtml(Long[] goodsIds);


}

