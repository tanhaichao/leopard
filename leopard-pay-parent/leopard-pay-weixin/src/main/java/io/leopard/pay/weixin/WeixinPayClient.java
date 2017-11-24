package io.leopard.pay.weixin;

import com.github.binarywang.wxpay.bean.result.WxPayMicropayResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.exception.WxPayException;
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
	String shortUrl(String longUrl) throws WxPayException;

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
	 */
	WxPayUnifiedOrderResult unifiedOrder(String orderNo, TradeType tradeType, int totalFee, String body, String detail, String notifyUrl, String spbillCreateIp) throws WxPayException;

	/**
	 * 提交刷卡支付
	 * 
	 * @param orderNo
	 * @param totalFee
	 * @param body
	 * @param authCode
	 * @param spbillCreateIp
	 * @return
	 */
	WxPayMicropayResult micropay(String orderNo, int totalFee, String body, String authCode, String spbillCreateIp) throws WeixinPayException;

}
