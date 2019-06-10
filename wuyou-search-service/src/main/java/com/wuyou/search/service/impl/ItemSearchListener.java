package com.wuyou.search.service.impl;

import java.util.List;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.wuyou.pojo.TbItem;
import com.wuyou.search.service.ItemSearchService;

/** 
* @author  jie
* @date 创建时间：2019年4月29日 
* @version 1.0  
* @return  
*/
@Component
public class ItemSearchListener implements MessageListener {

	@Autowired
	private ItemSearchService itemSearchService;

	@Override
	public void onMessage(Message message) { 
		try {
			TextMessage textMessage=(TextMessage)message;			
			String text = textMessage.getText();
			System.out.println("ItemSearchListener监听接收到消息..."+text);
			
			List<TbItem> list = JSON.parseArray(text,TbItem.class);
			for(TbItem item:list){
				System.out.println(item.getId()+" "+item.getTitle());
				Map specMap= JSON.parseObject(item.getSpec());//将spec字段中的json字符串转换为map
				item.setSpecMap(specMap);//给带注解的字段赋值
			}			
			itemSearchService.importList(list);//导入	
			System.out.println("成功导入到索引库");
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}


}

