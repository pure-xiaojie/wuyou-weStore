package com.wuyou.pojogroup;

import java.io.Serializable;
import java.util.List;

import com.wuyou.pojo.TbGoods;
import com.wuyou.pojo.TbGoodsDesc;
import com.wuyou.pojo.TbItem;

/** 
* @author  jie
* @date 创建时间：2019年4月18日 下午3:14:40 
* @version 1.0  
* @return  
* 组合实体类
*/
public class Goods implements Serializable{
	private TbGoods goods;//商品SPU
	private TbGoodsDesc goodsDesc;//商品扩展
	private List<TbItem> itemList;//商品SKU列表	
	
	public TbGoods getGoods() {
		return goods;
	}
	public void setGoods(TbGoods goods) {
		this.goods = goods;
	}
	public TbGoodsDesc getGoodsDesc() {
		return goodsDesc;
	}
	public void setGoodsDesc(TbGoodsDesc goodsDesc) {
		this.goodsDesc = goodsDesc;
	}
	public List<TbItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<TbItem> itemList) {
		this.itemList = itemList;
	}

	
}

