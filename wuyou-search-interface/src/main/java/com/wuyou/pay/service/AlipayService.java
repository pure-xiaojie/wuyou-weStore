package com.wuyou.pay.service;

/** 
* @author  jie
* @date 创建时间：2019年5月23日 
* @version 1.0  
* @return  
*/
public interface AlipayService {
	/**
	 * 生成支付宝支付二维码
	 * @param out_trade_no 订单号
	 * @param total_fee 金额(分)
	 * @return
	 */
	public String createNative(String out_trade_no,String total_fee);

}

