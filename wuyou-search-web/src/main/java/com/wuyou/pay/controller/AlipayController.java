package com.wuyou.pay.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.wuyou.order.service.OrderService;
import com.wuyou.pojo.TbOrder;
import com.wuyou.pojo.TbPayLog;

import util.AlipayConfig;

/**
 * @Description: controller
 */
@RestController
@RequestMapping("/alipay")
public class AlipayController {
	//注入接口 
	@Reference
	private OrderService orderService;
	
	/**
	 * 生成二维码
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/createNative")
	public String createNative() {
		System.out.println("支付宝支付接口");
		//获取当前用户		
		String userId=SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println("userID:" + userId);
		//到redis查询支付日志
		TbPayLog payLog = orderService.searchPayLogFromRedis(userId);
		
		//商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no;
		//付款金额，必填
		String total_amount;
		
		if(payLog != null) {
			System.out.println("userid:"+userId+"   paylog:"+payLog);
			System.out.println(payLog.getOutTradeNo()+payLog.getTotalFee());
			
			//普通商品订单号
			out_trade_no = payLog.getOutTradeNo();
			//金额
			total_amount = payLog.getTotalFee()+"";
		}
		else {
			//秒杀商品订单号
			out_trade_no = "1134359267755600";
			//付款金额
			total_amount = 19.9+"";
		}
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
 
		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
 
		//订单名称，必填
		String subject = "无忧商品";
		//商品描述，可空
		String body = "用户订购商品个数：1";
 
		// 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
    	String timeout_express = "1c";
 
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
				+ "\"total_amount\":\""+ total_amount +"\","
				+ "\"subject\":\""+ subject +"\","
				+ "\"body\":\""+ body +"\","
				+ "\"timeout_express\":\""+ timeout_express +"\","
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
 
		//请求
		String result="";
		try {
			result = alipayClient.pageExecute(alipayRequest).getBody();
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("阿里请求返回："+result);
	
		return result;
	}
	
	/**
     * 检查支付状态
     * @param out_trade_no  //订单号
     * @param totay_money   //金额
     * @param trade_no		//交易流水号
     */
    @RequestMapping("/alipayNotify")
    public String alipayNotify(String out_trade_no, String totay_money,String trade_no){
        
    	String userId=SecurityContextHolder.getContext().getAuthentication().getName();
    	TbPayLog payLog = orderService.searchPayLogFromRedis(userId);
    	String out_trade = payLog.getOutTradeNo();
    	boolean signVerified;
    	if(out_trade.equals(out_trade_no) && trade_no != null)
           signVerified=true;
    	else
    	   signVerified=false;
    		
        if (signVerified) {
        	System.out.println("更新订单状态成功");
            //更新订单状态等
			orderService.updateOrderStatus(out_trade_no, trade_no);
            return ("success");
        } else {
            return ("fail");
        }
    }
	
}

