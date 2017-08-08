package io.leopard.account.weixin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

public class WeixinAccountClientImpl implements WeixinAccountClient {

	@Value("${weixin.appId}")
	private String appId;

	@Value("${weixin.secret}")
	private String secret;

	@Value("${weixin.token}")
	private String token;

	@Value("${weixin.aesKey}")
	private String aesKey;

	private WxMpServiceImpl mpService;

	@PostConstruct
	public void init() {
		WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
		config.setAppId(appId);// 设置微信公众号的appid
		config.setSecret(secret);// 设置微信公众号的app corpSecret

		config.setToken(token);// 设置微信公众号的token
		config.setAesKey(aesKey);// 设置消息加解密密钥

		this.mpService = new WxMpServiceImpl();
		mpService.setWxMpConfigStorage(config);
	}

	@Override
	public List<String> getCallbackIP() throws WxErrorException {
		String[] ips = mpService.getCallbackIP();
		if (ips == null) {
			return null;
		}
		List<String> ipList = new ArrayList<String>();
		for (String ip : ips) {
			ipList.add(ip);
		}
		return ipList;
	}

	@Override
	public String shortUrl(String longUrl) throws WxErrorException {
		return this.mpService.shortUrl(longUrl);
	}

}
