package com.wuyou.pay.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.dubbo.config.annotation.Service;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;

import com.wuyou.pay.service.AlipayService;

import util.AlipayConfig;

/** 
* @author  jie
* @date 创建时间：2019年5月23日 
* @version 1.0  
* @return  
*/
@Service
public class AlipayServiceImpl implements AlipayService {

	@Override
	public String createNative(String out_trade_no, String total_fee) {
		System.out.println("调用ali支付接口");
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
 
		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
 
		//商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trad = out_trade_no;
		//付款金额，必填
		String total_amount = total_fee;
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
		String result=null;
		try {
			result = alipayClient.pageExecute(alipayRequest).getBody();
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("阿里请求返回："+result);
		Map<String, String> map=new HashMap<>();
		map.put("total_fee", total_fee);//总金额
		map.put("out_trade_no",out_trade_no);//订单号
		return result;
	}

}
