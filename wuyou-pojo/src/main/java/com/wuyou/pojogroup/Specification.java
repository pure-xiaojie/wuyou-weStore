package com.wuyou.pojogroup;

import java.io.Serializable;
import java.util.List;

import com.wuyou.pojo.TbSpecification;
import com.wuyou.pojo.TbSpecificationOption;

/** 
 * 规格组合实体类
* @author  jie
* @date 创建时间：2019年4月11日 下午10:19:52 
* @version 1.0  
* @return  
*/
public class Specification implements Serializable{
	private TbSpecification specification;
	
	private List<TbSpecificationOption> specificationOptionList;

	public TbSpecification getSpecification() {
		return specification;
	}

	public void setSpecification(TbSpecification specification) {
		this.specification = specification;
	}

	public List<TbSpecificationOption> getSpecificationOptionList() {
		return specificationOptionList;
	}

	public void setSpecificationOptionList(List<TbSpecificationOption> specificationOptionList) {
		this.specificationOptionList = specificationOptionList;
	}
	
	
}


