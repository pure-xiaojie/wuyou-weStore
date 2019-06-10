 //控制层 
app.controller('ordersController' ,function($scope,$window,orderService){	
    
	$scope.sendFalse={};//记录未发货订单
	var d = 0;
	
    $scope.status=[' ', ' ', '未发货 ','未发货','已发货','未评价','已评价'];//订单状态
    
    //查询订单列表
	$scope.findOrderList=function(){
		orderService.findOrderList().success(
			function(response){
				$scope.orderList=response;			
				//设置默认地址
				for(var i=0;i< $scope.orderList.length;i++){
					findOrderDetail(i,$scope.orderList[i].orderId);	
					
					//未发货订单
					if(($scope.orderList[i].status)*1 == 3 || ($scope.orderList[i].status)*1 == 2){	
						$scope.sendFalse[d]=$scope.orderList[i];
						d++;
						
					}
				}
				
			}
			
		);	
	}
	
	$scope.orderListDetail=[];//记录订单详细信息
	$scope.detail=[];
	
	//查询订单详细信息
	findOrderDetail=function(i,orderId){
		orderService.findOrderDetail(orderId).success(
			function(response){
				$scope.orderListDetail.push(response);
				$scope.detail[orderId]=response;
			}
		);		
	}
	
	$scope.selectIds=[];  //用户勾选的ID集合
	
	//用户都行复选框
	$scope.updateSelection=function($event,id){
		if($event.target.checked){
			$scope.selectIds.push(id);   //向集合添加元素
		}
		else{
			var index = $scope.selectIds.indexOf(id);  //查找值的位置
			$scope.selectIds.splice(index,1);  //参数1：移除的位置
		}
	}
	
	//更改多个状态
	$scope.updateStatus=function(status){		
		orderService.updateStatus($scope.selectIds,status).success(
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
	
	//导出exit表格
	$scope.exports=function(){
		orderService.exports().success(
				function(response){
					if(response.success){//成功
						alert("导出成功");
					}else{
						alert(response.message);
					}
				}
		);		
	}
	
});	
