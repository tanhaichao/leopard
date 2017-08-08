package io.leopard.pay.weixin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;

import me.chanjar.weixin.common.exception.WxErrorException;

public class WeixinPayClientImpl implements WeixinPayClient {

	@Value("${weixin.appId}")
	private String appId;

	@Value("${weixin.mchId}")
	private String mchId;

	@Value("${weixin.mchKey}")
	private String mchKey;

	private WxPayService wxPayService;

	@PostConstruct
	public void init() {
		WxPayConfig payConfig = new WxPayConfig();
		payConfig.setAppId(appId);
		payConfig.setMchId(mchId);
		payConfig.setMchKey(mchKey);
		wxPayService = new WxPayServiceImpl();
		wxPayService.setConfig(payConfig);

	}

	@Override
	public String shortUrl(String longUrl) throws WxErrorException {
		String shortUrl = wxPayService.shorturl(longUrl);
		return shortUrl;
	}

}
