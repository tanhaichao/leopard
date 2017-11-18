package io.leopard.pay.weixin;

import io.leopard.lang.inum.Snum;

/**
 * 交易类型
 * 
 * @author 谭海潮
 *
 */
public enum TradeType implements Snum {

	/** 交易类型: JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付、MWEB--手机网站支付 */

	JSAPI("JSAPI", "公众号支付")//
	, NATIVE("NATIVE", "原生扫码支付")//
	, APP("APP", "app支付")//
	, MWEB("MWEB", "手机网站支付")//
	;

	private String key;

	private String desc;

	private TradeType(String key, String desc) {
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
