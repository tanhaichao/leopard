package io.leopard.account.qq;

import org.springframework.beans.factory.annotation.Value;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

public class QqAccountClientImpl implements QqAccountClient {

	@Value("${qq.appId}")
	private String appId;

	@Value("${qq.appKey}")
	private String appKey;

	@Override
	public WxMpOAuth2AccessToken oauth2getAccessToken(String code) throws WxErrorException {
		// TODO Auto-generated method stub
		return null;
	}

}
