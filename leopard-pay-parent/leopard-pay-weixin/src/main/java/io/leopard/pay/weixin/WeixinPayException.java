package io.leopard.pay.weixin;

import com.github.binarywang.wxpay.exception.WxPayException;

public class WeixinPayException extends Exception {

	private static final long serialVersionUID = 1L;

	public WeixinPayException(String message, WxPayException e) {
		super(message, e);
	}

}
