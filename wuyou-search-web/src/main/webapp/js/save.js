 $(function () {
	 	
	 $.ajax({
            type: 'get',      
            url: '/alipay/alipayNotify.do',
            data: { out_trade_no: 1},
            success: function (data) {
                alert("订单状态更新成功");
            },
            error: function (data) {
            	alert("订单状态更新失败");
            }
            
        })
 });