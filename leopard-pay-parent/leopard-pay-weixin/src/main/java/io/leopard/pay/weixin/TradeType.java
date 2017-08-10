package io.leopard.pay.weixin;

import io.leopard.lang.inum.Snum;

/**
 * 交易类型
 * 
 * @author 谭海潮
 *
 */
public enum TradeType implements Snum {
	JSAPI("JSAPI", "JSAPI")//
	, NATIVE("NATIVE", "NATIVE")//
	, APP("APP", "APP")//
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
