package io.leopard.account.weixin;

import java.util.List;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

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

	/**
	 * 获取用户的access_token
	 * 
	 * @param code
	 * @return
	 * @throws WxErrorException
	 */
	WxMpOAuth2AccessToken oauth2getAccessToken(String code) throws WxErrorException;
}
