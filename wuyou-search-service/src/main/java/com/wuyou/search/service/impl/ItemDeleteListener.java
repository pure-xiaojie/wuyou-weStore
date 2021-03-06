package com.wuyou.search.service.impl;

import java.util.Arrays;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wuyou.search.service.ItemSearchService;

/** 
* @author  jie
* @date 创建时间：2019年4月29日 
* @version 1.0  
* @return  
*/
@Component
public class ItemDeleteListener implements MessageListener {

	@Autowired
	private ItemSearchService itemSearchService;

	@Override
	public void onMessage(Message message) {
		ObjectMessage objectMessage = (ObjectMessage)message;
		try {			
			Long[]  goodsIds = (Long[]) objectMessage.getObject();		
			System.out.println("ItemDeleteListener监听接收到消息..."+goodsIds);
			itemSearchService.deleteByGoodsIds(Arrays.asList(goodsIds));
			System.out.println("成功删除索引库中的记录");			
		} catch (Exception e) {
			e.printStackTrace();
		}			

	}

}

