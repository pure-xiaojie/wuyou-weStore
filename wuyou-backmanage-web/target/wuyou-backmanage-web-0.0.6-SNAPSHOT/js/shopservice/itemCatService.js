//服务层
app.service('itemCatService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../shopItemCat/findAll.do');		
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../shopItemCat/findPage.do?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../shopItemCat/findOne.do?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../shopItemCat/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../shopItemCat/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../shopItemCat/delete.do?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../shopItemCat/search.do?page='+page+"&rows="+rows, searchEntity);
	} 
	
	//根据上级ID查询下级列表
	this.findByParentId=function(parentId){
		return $http.get('../shopItemCat/findByParentId.do?parentId='+parentId);	
	}

});
