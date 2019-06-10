//订单控制层 
app.controller('orderController',function($scope,$window,orderService,loginService){
	$scope.paySuccess={};//记录成功付款订单 
	var a = 0;
	
	$scope.payFalse={};//记录未付款订单
	var b = 0;
	
	$scope.sendSuccess={};//记录已发货订单 
	var c = 0;
	
	$scope.sendFalse={};//记录未发货订单
	var d = 0;
	
	$scope.appraiseFalse={};//记录未评价订单
	var e = 0;
	
	//查询订单列表
	$scope.findOrderList=function(){
		orderService.findOrderList().success(
			function(response){
				$scope.orderList=response;			
				//设置默认地址
				for(var i=0;i< $scope.orderList.length;i++){
					findOrderDetail(i,$scope.orderList[i].orderId);
					
					//已付款订单
					if(($scope.orderList[i].status)*1 == 2){	
						$scope.paySuccess[a]=$scope.orderList[i];
						a++;
					}
					
					//未付款订单
					if(($scope.orderList[i].status)*1 == 1){	
						$scope.payFalse[b]=$scope.orderList[i];
						b++;
					}
					
					//已发货订单
					if(($scope.orderList[i].status)*1 == 4){	
						$scope.sendSuccess[c]=$scope.orderList[i];
						c++;
					}
					
					//未发货订单
					if(($scope.orderList[i].status)*1 == 3){	
						$scope.sendFalse[d]=$scope.orderList[i];
						d++;
					}
					
					//未评论订单
					if(($scope.orderList[i].status)*1 == 5){	
						$scope.appraiseFalse[e]=$scope.orderList[i];
						e++;
					}
					
				}
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
	
	$scope.orderListDetail=[];//记录订单详细信息
	
	//查询订单详细信息
	findOrderDetail=function(i,orderId){
		orderService.findOrderDetail(orderId).success(
			function(response){
				$scope.orderListDetail.push(response);
			}
		);		
	}
	
	$scope.findOneDetail=function(orderId) {
		for(var j = 0; j < $scope.orderListDetail.length; j++)
			if($scope.orderListDetail[j].orderId*1 == orderId*1) {
				$scope.detail = $scope.orderListDetail[j];
				break;
			}

	}
	
	//搜索跳转
	$scope.search=function(){
		location.href="search.html#?keywords="+$scope.keywords;
	}
	
	//提醒发货
	$scope.alertSend=function() {
		alert("亲，已帮您提醒商家立刻发货，请稍等！");
	}
	
	
	//确认收货
	$scope.sureGet=function(orderId,status) {

		orderService.updateStatus(orderId,status).success(
				function(response){
					if(response.success){//成功
						$scope.selectIds=[];//清空ID集合
						$window.location.reload();
					}else{
						alert(response.message);
					}
				}
			);		
	}
});
