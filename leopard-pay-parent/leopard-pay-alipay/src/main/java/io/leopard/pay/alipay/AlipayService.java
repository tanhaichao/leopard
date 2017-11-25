package io.leopard.pay.alipay;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

import me.hao0.alipay.core.Alipay;
import me.hao0.alipay.core.AlipayBuilder;
import me.hao0.alipay.model.pay.WapPayDetail;
import me.hao0.alipay.model.pay.WebPayDetail;
import me.hao0.alipay.model.refund.RefundDetail;

public class AlipayService {

	@Value("${merchantId}")
	private String merchantId;

	@Value("${secret}")
	private String secret;

	@Value("${payNotifyUrl}")
	private String payNotifyUrl;

	@Value("${refundNotifyUrl}")
	private String refundNotifyUrl;

	@Value("${webReturnUrl}")
	private String webReturnUrl;

	@Value("${wapReturnUrl}")
	private String wapReturnUrl;

	private Alipay alipay;

	@PostConstruct
	public void initAlipay() {
		alipay = AlipayBuilder.newBuilder(merchantId, secret).build();

		System.err.println(alipay);
	}

	/**
	 * web支付
	 */
	public String webPay(WebPayDetail detail) {
		detail.setNotifyUrl(payNotifyUrl);
		detail.setReturnUrl(webReturnUrl);
		return alipay.pay().webPay(detail);
	}

	/**
	 * wap支付
	 */
	public String wapPay(WapPayDetail detail) {
		detail.setNotifyUrl(payNotifyUrl);
		detail.setReturnUrl(wapReturnUrl);
		return alipay.pay().wapPay(detail);
	}

	/**
	 * MD5验证
	 */
	public Boolean notifyVerifyMd5(Map<String, String> params) {
		return alipay.verify().md5(params);
	}

	/**
	 * 退款申请
	 */
	public Boolean refund(RefundDetail detail) {
		detail.setNotifyUrl(refundNotifyUrl);
		return alipay.refund().refund(detail);
	}
}
