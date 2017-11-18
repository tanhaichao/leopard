package io.leopard.pay.alipay;

/**
 * 支付宝
 * 
 * @author 谭海潮
 *
 */
public interface AlipayClient {

	PreparePayResult preparePay(String outTradeNo, double amount, String returnUrl, String subject, String description);

}
