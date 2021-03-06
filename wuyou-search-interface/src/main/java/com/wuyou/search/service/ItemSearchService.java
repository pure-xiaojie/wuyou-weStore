package com.wuyou.search.service;

import java.util.List;
import java.util.Map;

/** 
* @author  jie
* @date 创建时间：2019年4月25日 
* @version 1.0  
* @return  
*/
public interface ItemSearchService {
	/**
	 * 搜索
	 * @param keywords
	 * @return
	 */
	public Map search(Map searchMap);
	
	/**
	 * 导入数据
	 * @param list
	 */
	public void importList(List list);

	/**
	 * 删除数据
	 * @param ids
	 */
	public void deleteByGoodsIds(List goodsIdList);

}

