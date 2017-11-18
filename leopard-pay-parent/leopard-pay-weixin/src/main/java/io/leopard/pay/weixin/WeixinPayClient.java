package io.leopard.pay.weixin;

import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.service.WxPayService;

import me.chanjar.weixin.common.exception.WxErrorException;

/**
 * 微信支付
 * 
 * @author 谭海潮
 *
 */
public interface WeixinPayClient {

	/**
	 * 获取短连接
	 * 
	 * @param longUrl
	 * @return
	 * @throws WxErrorException
	 */
	String shortUrl(String longUrl) throws WxErrorException;

	WxPayService getWxPayService();

	/**
	 * 统一下单
	 * 
	 * @param orderNo
	 * @param tradeType
	 * @param totalFee
	 * @param body
	 * @param detail
	 * @param notifyUrl
	 * @param spbillCreateIp
	 * @return
	 * @throws WxErrorException
	 */
	WxPayUnifiedOrderResult unifiedOrder(String orderNo, TradeType tradeType, int totalFee, String body, String detail, String notifyUrl, String spbillCreateIp) throws WxErrorException;

}
