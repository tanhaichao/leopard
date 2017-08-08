package io.leopard.account.weixin;

import java.util.List;

import me.chanjar.weixin.common.exception.WxErrorException;

/**
 * 微信账号
 * 
 * @author 谭海潮
 *
 */
public interface WeixinAccountClient {

	/**
	 * 获取回调IP列表
	 * 
	 * @return
	 * @throws WxErrorException
	 */
	List<String> getCallbackIP() throws WxErrorException;

	String shortUrl(String longUrl) throws WxErrorException;
}
