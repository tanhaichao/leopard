package io.leopard.pay.alipay;

import io.leopard.lang.inum.Snum;

/**
 * 支付宝订单交易状态
 * 
 * @author 谭海潮
 *
 */
public enum AlipayOrderTradeStatus implements Snum {

	SUCCESS("SUCCESS", "支付成功")//
	, REFUND("REFUND", "转入退款")//
	, NOTPAY("NOTPAY", "未支付")//
	, CLOSED("CLOSED", "已关闭")//
	, REVOKED("REVOKED", "已撤销（刷卡支付）")//
	, USERPAYING("USERPAYING", "用户支付中")//
	, PAYERROR("PAYERROR", "支付失败(其他原因，如银行返回失败)")//
	;

	private String key;

	private String desc;

	private AlipayOrderTradeStatus(String key, String desc) {
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
