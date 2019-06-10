app.controller('brandController',function($scope,$controller,brandServer){
    	
	$controller('baseController',{$scope:$scope});
	    
    //查询品牌列表
	$scope.findAll=function(){
		brandServer.findAll().success(
				function(response) {
					$scope.list=response;
				}
		);
	}
	
	
	//分页
	$scope.findPage=function(page,size){
		brandServer.findPage().success(
				function(response) {
					$scope.list=response.rows;    //显示当前页数据
					$scope.conf.totalItems=response.total; //更新总记录数
				}
		);
	}
	
	//新增
	$scope.save=function(){
		var object=null;
		if($scope.entity.id != null){
			object=brandServer.update($scope.entity);
		}
		else{
			object=brandServer.add($scope.entity);
		}
		object.success(
			function(response){
				if(response.success){
					$scope.reloadList(); //刷新
				}
				else{
					alert(response.message);
				}
			}		
		)
	}
	
	//查询实体
	$scope.findOne=function(id){
		brandServer.findOne(id).success(
				function(response){
					$scope.entity=response;
				}
		)
	}
	
	
	//删除
	$scope.dele=function(){
		brandServer.dele($scope.selectIds).success(
			function(response){
				if(response.success){
					$scope.reloadList();  //刷新
				}
				else{
					alert(response.message);
				}
			}		
		)
	}
	
	$scope.searchEntity={};//定义搜索对象 			
	//条件查询 
	$scope.search=function(page,rows){
		brandServer.search(page,rows,$scope.searchEntity).success(
			function(response){
					$scope.conf.totalItems=response.total;//总记录数 
					$scope.list=response.rows;//给列表变量赋值 
			}		
		);				
	}

});