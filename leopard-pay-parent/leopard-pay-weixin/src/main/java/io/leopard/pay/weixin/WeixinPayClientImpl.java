package io.leopard.pay.weixin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

import com.github.binarywang.wxpay.bean.request.WxPayMicropayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayMicropayResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;

import io.leopard.json.Json;
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

	@Override
	public WxPayUnifiedOrderResult unifiedOrder(String orderNo, TradeType tradeType, int totalFee, String body, String detail, String notifyUrl, String spbillCreateIp) throws WxErrorException {
		WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
		request.setOutTradeNo(orderNo);// 商户订单号
		request.setTotalFee(totalFee);// 订单总金额
		// request.setProductId(productId);// 商品ID
		request.setBody(body);// 商品描述
		request.setDetail(detail);// 商品详细介绍
		request.setNotifyURL(notifyUrl);
		request.setTradeType(tradeType.getKey());// JSAPI，NATIVE，APP
		request.setSpbillCreateIp(spbillCreateIp);
		WxPayUnifiedOrderResult result = wxPayService.unifiedOrder(request);
		Json.print(result, "result");
		// wxPayService.downloadBill(billDate, billType, tarType, deviceInfo);
		return result;
	}

	@Override
	public WxPayService getWxPayService() {
		return wxPayService;
	}

	@Override
	public WxPayMicropayResult micropay(String orderNo, int totalFee, String body, String authCode, String spbillCreateIp) throws WxErrorException {
		WxPayMicropayRequest.Builder builder = WxPayMicropayRequest.newBuilder();// .appid(appId).mchId(mchId);
		// builder .nonceStr(nonceStr);
		// builder .sign(sign)
		builder.outTradeNo(orderNo);
		builder.totalFee(totalFee);
		builder.body(body);
		builder.spbillCreateIp(spbillCreateIp);
		builder.authCode(authCode);
		WxPayMicropayRequest request = builder.build();
		return wxPayService.micropay(request);
	}

}
