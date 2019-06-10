package util;

public class AlipayConfig {

	    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
		public static String app_id = "2016092900623970";
		
		// 商户私钥，您的PKCS8格式RSA2私钥
	    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCu+Uu8EIPRrrdvFhvDVrUVtQNA8bNHGaDfLZ7ZtgUgBMivS0KRhbmorVc2HC8ATc2imbHD3FyEb3SkUyhHVjzXR9a80NseK6ZZGmdY3bkd5QPt1biV6lHhIbMYUE2+IoP9nyMJo/qKsvgjueMQ8fhQYFnPg6Qx8EdYkPL8+y9DBUKxOVEchw1to1nf6MzR5wsJ4NNfSX4FBoZgsCUW+8FkGc/zbTZx27aPOb/V9w0VJVm2jNoyi5FTUEhFHbuVcDPZgdiEPQrNhQM2ZAQVQtbgssIPtKACYwT7iAXfKkNaapfa34yuEs4OitsQwo+FhNnw3p6QGwM8roP2MBrHsiwpAgMBAAECggEAFgVZ+rk5XdJXU6u8osDu2WSGnE3O+kvHa08ahtvZmUYA287k+duSmh/2seCqKaehEa22Kag6HJDKZUPx9chsr8tiwzCl4w4rSVuBVjXRbmm8LNqQ+I0c8PCE0bKJljxR0EGO70+r4HjVselbIm7RiYwWwX8X0xEiaYsUibIzKIPzE5cUxnsBTT7CEi9ftlZlb38gX2lG12rWA+PAfc/U4l3Ov+oWMi9G0qpH2NSHL1CHdv/oYAYU/VFjDje5VNHMSnMVACMwgAwsQeWCJy/Xdc/8aZlesVtfZhdof/+H6Jfs1p8rPIiYYjbuf4Nmn/e5yCGJ/NeJUfuMw94kUVbo4QKBgQD2kXpSA9wTXriDwkzS3kl1W/61pGfLyEEe0+JvZ7ZTPeyqx6T0vhkpbquBLAmrwXJfFGHijeMhg+rLERwBe7uQv8LqtJ4/ui8BXZ0lMtuVGyIN+bK/fcfLIqs+04qS8nNZz7w1+sfUSNmyeiFE33K6RlJN0IFi5hER5yCFlMvOCwKBgQC1qrqEA5GoHFLleyyti6Kukyxc2CFQ4ZeMVp5lbzL44c9AjhWJk86v1ErgdeVvHNVIXedyzaz/uFrM66oXEqfwcGxC4P7d2Jds7NWkosalqYY0qFOPPQf1hLmP8M5fdKJnDvyLuOKJNDU7wYcHgKDX6UP+XLp1lUjQLwBuOz3zGwKBgAobAEZoPhbVJP7RcdCap6wVryTGrjJd7L1Utc0XFRPoT9DYCdy2BdX64TCUG3lcnlFjRnfAlPSEsZTNCLbO2WsfUN/Js0qtWMvXiF9D7Ff6GrUz7K2IfC3JJ/eVN/K52RxvNSmJM7NxoOp9sutdtRgJVco2p3bkMwLm1CBlbsztAoGAEMPQsp4hswHqJINZfCqGuNWcInVPnccz1R+pPnMkoRM1rR9vnWKmkFNnFY/LvJVMmYEQ+udAL9kzh/W3nKl8FhRJ6WIaIx752nOAw9DBxEgAuptOGsnnEiFNfpsacHSzCA+5/qrxPPg0cW9KpsaYEVTVkYO9MVy1e4Da6VFORkECgYAD7yvt67f8rQ+ufPSRJW2kQkptUu3FRYh/wvNYcYeQ7WoWQM5RzBYdtFJ72vSNGaDlZTkX5L65UZIZd2p4OiPajS9Y0MOcCQigL4xemr4afT7OUuPtM1j0mdI2KS2OQnw/0reeF+LKS/6vDhydXruCpWJtekgEsbhYZus62eLJJQ==";
		
		// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
	    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8Oi1Scq8NzC8eGREkHIvlYrcKSswpzNZ+sUGVXp5r0W15iRC81hYLCunz2DEbhR89vUfHGFynZMlS2cEEEznmWapFdj/7DQt/xem9gnoXaHzBY8T+CvzeOpZPysOP2ugsXi4ecEvjhI6ukva+UjZQui7kSp9ivP1mmS+fO46d2KFLzDq4KYxxl6wLSrRGx6HRnNeZFTArbJufzXgS+yb8r83XVAhnQYvquY55/WSHedwPUATq6iQWBpvV29ST1+bkWWkhGKSrTsWn8zTj5kbAXZx+SfU5ZGf3pZCZAKzwJJlWH0UZDHwnS5jEXrjvlNvJ+maQBqCv8pV9iLDQzou3QIDAQAB";

		// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
		public static String notify_url = "http://localhost:9102/paysuccess.html";

		// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
		public static String return_url = "http://localhost:9102/paysuccess.html";

		// 签名方式
		public static String sign_type = "RSA2";
		
		// 字符编码格式
		public static String charset = "utf-8";
		
		// 支付宝网关
		public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
		
		// 支付宝网关
		public static String log_path = "C:\\";
}
