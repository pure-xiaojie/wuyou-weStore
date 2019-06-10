app.service('orderService',function($http){
	//订单列表
	this.findOrderList=function(){
		return $http.get('/order/findAll.do');		
	}
	
	//详细订单
	this.findOrderDetail=function(orderId){
		return $http.get('/order/orderDetail.do?orderId=' + orderId);		
	}
	
	//更改状态
	this.updateStatus=function(ids,status){
		return $http.get('/order/updateStatus.do?ids='+ids+"&status="+status);
	} 

});