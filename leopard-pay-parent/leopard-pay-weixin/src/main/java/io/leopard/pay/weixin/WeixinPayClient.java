package io.leopard.pay.weixin;

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

}
