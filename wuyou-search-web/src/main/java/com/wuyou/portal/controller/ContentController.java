package com.wuyou.portal.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wuyou.content.service.ContentService;
import com.wuyou.pojo.TbContent;

/** 
* @author  jie
* @date 创建时间：2019年4月23日 
* @version 1.0  
* @return  
*/
@RestController
@RequestMapping("/content")
public class ContentController {

	@Reference
	private ContentService contentService;
	
	/**
	 * 根据广告分类ID查询广告列表
	 * @param categoryId
	 * @return
	 */
	@RequestMapping("/findByCategoryId")
	public List<TbContent> findByCategoryId(Long categoryId) {
		return contentService.findByCategoryId(categoryId);
	}		
}


