package io.leopard.pay.weixin;

public interface WeixinPayDao {

	boolean add(String paymentId, String outTradeNo);

	String getPaymentId(String outTradeNo);
}
