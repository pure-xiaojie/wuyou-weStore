package com.wuyou.backmanage.service;

import java.util.List;
import java.util.Map;

import com.wuyou.pojo.TbBrand;

import entity.PageResult;

/**
 * 品牌接口
 * @author ASUS
 *
 */
public interface BrandService {
	//获取品牌列表数据
	public List<TbBrand> findAll();
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);
	
	/**
	 * 增加
	 * @param brand
	 */
	public void add(TbBrand brand);
	
	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public TbBrand findOne(long id);
	
	/**
	 * 修改
	 * @param brand
	 */
	public void update(TbBrand brand);
	
	/**
	 * 删除
	 * @param ids
	 */
	public void delete(long[] ids);
	
	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage2(TbBrand brand, int pageNum,int pageSize);

	/**
	 * 品牌下拉框数据
	 */
	public List<Map> selectOptionList();

}
