package com.wuyou.page.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import com.alibaba.dubbo.config.annotation.Service;
import com.wuyou.mapper.TbGoodsDescMapper;
import com.wuyou.mapper.TbGoodsMapper;
import com.wuyou.mapper.TbItemCatMapper;
import com.wuyou.mapper.TbItemMapper;
import com.wuyou.page.service.ItemPageService;
import com.wuyou.pojo.TbGoods;
import com.wuyou.pojo.TbGoodsDesc;
import com.wuyou.pojo.TbItem;
import com.wuyou.pojo.TbItemExample;
import com.wuyou.pojo.TbItemExample.Criteria;

import freemarker.template.Configuration;
import freemarker.template.Template;

/** 
* @author  jie
* @date 创建时间：2019年4月28日 
* @version 1.0  
* @return  
*/
@Service
public class ItemPageServiceImpl implements ItemPageService {

	//@Value("${pagedir}")//配置文件中指定生成静态网页的位置
	private String pagedir="D:\\我的程序人生\\eclipse\\wuyou-parent\\wuyou-search-web\\src\\main\\webapp\\detailsHtml\\";
	
	@Autowired
	private FreeMarkerConfig freeMarkerConfig;
	
	@Autowired
	private TbGoodsMapper goodsMapper;
	
	@Autowired
	private TbGoodsDescMapper goodsDescMapper;
	
	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Autowired
	private TbItemMapper itemMapper;

	@Override
	public boolean genItemHtml(Long goodsId){				
		try {
			Configuration configuration = freeMarkerConfig.getConfiguration();
			Template template = configuration.getTemplate("item.ftl");
			Map dataModel=new HashMap<>();			
			//1.加载商品表数据
			TbGoods goods = goodsMapper.selectByPrimaryKey(goodsId);
			dataModel.put("goods", goods);			
			
			//2.加载商品扩展表数据			
			TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
			dataModel.put("goodsDesc", goodsDesc);						
			
			//3.商品分类
			String itemCat1 = itemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();
			String itemCat2 = itemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
			String itemCat3 = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();
			dataModel.put("itemCat1", itemCat1);
			dataModel.put("itemCat2", itemCat2);
			dataModel.put("itemCat3", itemCat3);

			//4.SKU列表			
			TbItemExample example=new TbItemExample();
			Criteria criteria = example.createCriteria();
			criteria.andStatusEqualTo("1");//状态为有效
			criteria.andGoodsIdEqualTo(goodsId);//指定SPU ID
			example.setOrderByClause("is_default desc");//按照状态降序，保证第一个为默认			
			List<TbItem> itemList = itemMapper.selectByExample(example);		
			dataModel.put("itemList", itemList);

			Writer out=new FileWriter(pagedir+goodsId+".html");
			template.process(dataModel, out);
			out.close();
			return true;			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteItemHtml(Long[] goodsIds) {
		
		try {
			for(Long goodsId:goodsIds){
				new File(pagedir+goodsId+".html").delete();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}	

	}


}

