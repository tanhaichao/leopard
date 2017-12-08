package io.leopard.account.qq;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

/**
 * QQ账号
 * 
 * @author 谭海潮
 *
 */
public interface QqAccountClient {
	WxMpOAuth2AccessToken oauth2getAccessToken(String code) throws WxErrorException;

}
