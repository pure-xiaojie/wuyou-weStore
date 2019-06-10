//控制层 
app.controller('userController' ,function($scope,$controller  ,userService,loginService,cartService){	
	//注册
	$scope.reg=function(){		
	    if($scope.entity.password!=$scope.password)  {
	      	alert("两次输入的密码不一致，请重新输入");		    	
	      	return ;
	    }
	    //新增
		userService.add( $scope.entity , $scope.smscode).success(
			function(response){
				alert(response.message);
			}		
		);				
	} 
	
	//发送验证码
	$scope.sendCode=function(){
		if($scope.entity.phone==null){
			alert("请输入手机号！");
			return ;
		}		
		userService.sendCode($scope.entity.phone).success(
			function(response){
				alert(response.message);								
			}				
		);
	}
	
	//更改个人信息
	$scope.save=function(){	
		userService.update($scope.entitys).success(
			function(response){
				alert(response.message);								
			}				
		);
	}
	
	//显示用户名
	$scope.showName=function(){
		loginService.showName().success(
			function(response){				
				$scope.loginName=response.loginName;
			}
		);		
	}
	
	//获取当前用户地址列表
	$scope.findAddressList=function(){
		cartService.findAddressList().success(
			function(response){
				$scope.addressList=response;
				//设置默认地址
				for(var i=0;i< $scope.addressList.length;i++){
					if($scope.addressList[i].isDefault=='1'){
						$scope.address=$scope.addressList[i];
						break;
					}					
				}			

			}		
		);		
	}
	
	//更改密码
	$scope.update=function(){	
		if($scope.entity.password!=$scope.password)  {
	      	alert("两次输入的密码不一致，请重新输入");		    	
	      	return ;
	    }
		userService.update($scope.entity).success(
			function(response){
				alert(response.message);								
			}				
		);
	}

});	
