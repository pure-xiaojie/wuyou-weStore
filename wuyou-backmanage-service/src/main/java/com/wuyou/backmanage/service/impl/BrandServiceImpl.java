package com.wuyou.backmanage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.wuyou.mapper.TbBrandMapper;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wuyou.pojo.TbBrandExample;
import com.wuyou.pojo.TbBrandExample.Criteria;
import com.wuyou.backmanage.service.BrandService;
import com.wuyou.pojo.TbBrand;

import entity.PageResult;

/** 
* @author  jie
* @date 创建时间：2019年4月21日 
* @version 1.0  
* @return  
*/
@Service
@Transactional
public class BrandServiceImpl implements BrandService {

	@Autowired
	private TbBrandMapper brandMapper;
	
	@Override
	public List<TbBrand> findAll() {

		return brandMapper.selectByExample(null);
	}
	
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);  //分页  
		Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public void add(TbBrand brand) {
		brandMapper.insert(brand);
	}

	@Override
	public TbBrand findOne(long id) {
		
		return brandMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(TbBrand brand) {
		brandMapper.updateByPrimaryKey(brand);
	}

	@Override
	public void delete(long[] ids) {
		for(long id:ids) {
			brandMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public PageResult findPage2(TbBrand brand, int pageNum, int pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);		
		TbBrandExample example=new TbBrandExample();
		Criteria criteria = example.createCriteria();		
		if(brand!=null){
			if(brand.getName()!=null && brand.getName().length()>0){
				criteria.andNameLike("%"+brand.getName()+"%");
			}
			if(brand.getFirstChar()!=null && brand.getFirstChar().length()>0){
				criteria.andFirstCharEqualTo(brand.getFirstChar());
			}		
		}		
		Page<TbBrand> page= (Page<TbBrand>)brandMapper.selectByExample(example);	
		return new PageResult(page.getTotal(), page.getResult());

	}
	
	/**
	 * 列表数据
	 */
	@Override
	public List<Map> selectOptionList() {
		return brandMapper.selectOptionList();
	}

}

