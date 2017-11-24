package io.leopard.pay.weixin;

import com.github.binarywang.wxpay.exception.WxPayException;

public class WeixinPayException extends Exception {

	private static final long serialVersionUID = 1L;

	private String resultCode;

	private String errCode;

	private String errCodeDes;

	public WeixinPayException(String message, WxPayException e) {
		super(message, e);
		this.resultCode = e.getResultCode();
		this.errCode = e.getErrCode();
		this.errCodeDes = e.getErrCodeDes();
	}

	public String getResultCode() {
		return resultCode;
	}

	public String getErrCode() {
		return errCode;
	}

	public String getErrCodeDes() {
		return errCodeDes;
	}

}
