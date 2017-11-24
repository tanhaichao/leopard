package io.leopard.pay.weixin;

import java.util.Date;

public class WeixinPay {

	private String paymentId;

	/**
	 * 业务ID
	 */
	private String outTradeNo;

	private Date posttime;

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public Date getPosttime() {
		return posttime;
	}

	public void setPosttime(Date posttime) {
		this.posttime = posttime;
	}

}
