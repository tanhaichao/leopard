package io.leopard.pay.alipay;

import io.leopard.lang.inum.Snum;

/**
 * 微信支付状态
 * 
 * @author 谭海潮
 *
 */
public enum AlipayMicropayStatus implements Snum {
	SUCCESS("success", "支付成功")//
	, ORDERPAID("orderpaid", "订单已支付")//
	, NOTENOUGH("notenough", "余额不足")//
	, USERPAYING("userpaying", "用户支付中，需要输入密码")//
	, AUTH_CODE_ERROR("auth_code_error", "每个二维码仅限使用一次，请刷新再试")//
	, TRADE_ERROR("trade_error", "交易错误，请确认帐号是否存在异常。")//
	;

	private String key;

	private String desc;

	private AlipayMicropayStatus(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	@Override
	public String getKey() {
		return this.key;
	}

	@Override
	public String getDesc() {
		return this.desc;
	}

}
