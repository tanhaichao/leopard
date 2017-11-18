package io.leopard.pay.alipay;

import io.leopard.lang.inum.Snum;

/**
 * 预支付结果类型
 * 
 * @author 谭海潮
 *
 */
public enum PreparePayResultType implements Snum {

	ERROR("ERROR", "错误")//
	, FORM("FORM", "表单")//
	, REDIRECT("REDIRECT", "跳转 (跳转是不带上下文路径的)")//
	, QR_CODE("QR_CODE", "二维码")//
	, APP_PAY_STRING("APP_PAY_STRING", "APP 支付的订单字符串");

	private String key;

	private String desc;

	private PreparePayResultType(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getDesc() {
		return desc;
	}
}
