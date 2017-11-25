package io.leopard.pay.alipay;

/**
 * 支付宝
 * 
 * @author 谭海潮
 *
 */
public interface AlipayClient {

	// #服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	// #页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址

	PreparePayResult preparePay(String outTradeNo, double amount, String notifyUrl, String returnUrl, String subject, String description);

	String webPay(String orderNo, String orderName, String payNotifyUrl, double amount, String webReturnUrl);

}
