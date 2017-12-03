package io.leopard.pay.alipay;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.jpay.alipay.AliPayApiConfig;
import com.jpay.alipay.AliPayApiConfigKit;

import me.hao0.alipay.core.Alipay;
import me.hao0.alipay.core.AlipayBuilder;
import me.hao0.alipay.model.pay.WebPayDetail;

public class AlipayClientImpl implements AlipayClient {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Value("${alipay.partner}")
	private String partner;

	@Value("${alipay.appId}")
	private String appId;

	@Value("${alipay.privateKey}")
	private String privateKey;

	@Value("${alipay.publicKey}")
	private String publicKey;

	@Value("${alipay.gatewayUrl}")
	private String gatewayUrl;

	private Alipay alipay;

	DefaultAlipayClient alipayClient;

	@PostConstruct
	public void init() {
		alipay = AlipayBuilder.newBuilder(partner, privateKey).build();
		AliPayApiConfig aliPayApiConfig = AliPayApiConfig.New();
		aliPayApiConfig.setAppId(partner);
		aliPayApiConfig.setPrivateKey(privateKey);
		aliPayApiConfig.setAlipayPublicKey(publicKey);
		aliPayApiConfig.setServiceUrl(gatewayUrl);
		aliPayApiConfig.setCharset("UTF-8");
		aliPayApiConfig.setSignType("RSA2");
		aliPayApiConfig.build();
		System.err.println("appId:" + appId);
		System.err.println("gatewayUrl:" + gatewayUrl);
		AliPayApiConfigKit.putApiConfig(aliPayApiConfig);

		alipayClient = new DefaultAlipayClient(gatewayUrl, appId, privateKey, "json", "UTF-8", publicKey, "RSA2");
	}

	@Override
	public String webPay(String orderNo, String orderName, String payNotifyUrl, double amount, String webReturnUrl) {
		String totalFee = Double.toString(amount);
		WebPayDetail detail = new WebPayDetail(orderNo, orderName, totalFee);
		detail.setNotifyUrl(payNotifyUrl);
		detail.setReturnUrl(webReturnUrl);
		String form = alipay.pay().webPay(detail);
		// logger.info("web pay form: {}", form);
		return form;
	}

	@Override
	public AlipayMicropayStatus micropayForStatus(String outTradeNo, String scene, String authCode, String subject, double totalAmount, String spbillCreateIp) throws AlipayApiException {
		AlipayTradePayResponse response = this.micropay(outTradeNo, scene, authCode, subject, totalAmount, spbillCreateIp);

		String code = response.getCode();
		// https://docs.open.alipay.com/194/105170/

		if ("10000".equals(code)) {
			return AlipayMicropayStatus.SUCCESS;
		}
		else if ("40004".equals(code)) {
			return AlipayMicropayStatus.TRADE_ERROR;
		}
		else if ("10003".equals(code)) {// 等待用户付款
			return AlipayMicropayStatus.SUCCESS;
		}
		else if ("20000".equals(code)) {
			return AlipayMicropayStatus.TRADE_ERROR;
		}
		else {
			return AlipayMicropayStatus.TRADE_ERROR;
		}
		// if (response.isSuccess()) {
		// return AlipayMicropayStatus.SUCCESS;
		// }
		// else {
		// return AlipayMicropayStatus.TRADE_ERROR;
		// }
	}

	@Override
	public AlipayTradePayResponse micropay(String outTradeNo, String scene, String authCode, String subject, double amount, String spbillCreateIp) throws AlipayApiException {
		AlipayTradePayModel model = new AlipayTradePayModel();
		model.setOutTradeNo(outTradeNo);
		model.setScene(scene);
		model.setAuthCode(authCode);
		model.setSubject(subject);
		model.setTotalAmount(Double.toString(amount));
		System.err.println("authCode:" + authCode);
		System.err.println("totalAmount:" + amount);

		AlipayTradePayRequest request = new AlipayTradePayRequest();
		request.setBizModel(model);
		return alipayClient.execute(request);
		// if (response.isSuccess()) {
		// System.out.println("调用成功");
		// }
		// else {
		// System.out.println("调用失败");
		// }
		// return response.getCode() + ":" + response.getSubCode() + ":" + response.getSubMsg();
		// return AliPayApi.tradePay(model, notifyUrl);
	}

	@Override
	public PreparePayResult preparePay(String outTradeNo, double amount, String notifyUrl, String returnUrl, String subject, String description) {
		final Map<String, String> paramsMap = getParamsMap(outTradeNo, amount, notifyUrl, returnUrl, subject, description);
		final StringBuilder formBuilder = new StringBuilder();
		final String action = gatewayUrl + "?_input_charset=UTF-8";
		formBuilder.append("<form id=\"alipay_submit\" name=\"alipaysubmit\" method=\"GET\" action=\"").append(action).append("\">");

		for (final Map.Entry<String, String> entry : paramsMap.entrySet()) {
			final String name = entry.getKey();
			final String value = entry.getValue();
			formBuilder.append("<input type=\"hidden\" name=\"").append(name).append("\" value=\"").append(value).append("\"/>");
		}
		formBuilder.append("</form>");

		final String form = formBuilder.toString();

		final PreparePayResult preparePayResult = new PreparePayResult();
		preparePayResult.setType(PreparePayResultType.FORM);
		preparePayResult.setResult(form);
		return preparePayResult;
	}

	// #页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址

	private Map<String, String> getParamsMap(String outTradeNo, double amount, String notifyUrl, String returnUrl, String subject, String description) {
		// 该笔订单的资金总额, 单位为RMB-Yuan. 取值范围为[0.01, 100000000.00], 精确到小数点后两位
		int totalFee = (int) (amount * 100);
		// FIXME 还没有判断小数点精度
		// if (totalFee != amount) {
		// throw new IllegalArgumentException("金额[" + amount + "]的小数点没有精确到2位.");
		// }

		final SortedMap<String, String> sortedParams = new TreeMap<>();
		sortedParams.put("service", "create_direct_pay_by_user");
		sortedParams.put("partner", partner);
		sortedParams.put("seller_id", partner);
		sortedParams.put("_input_charset", "UTF-8");
		sortedParams.put("payment_type", "1"); // 支付类型,必填,不能修改
		sortedParams.put("notify_url", notifyUrl);
		sortedParams.put("return_url", returnUrl);
		sortedParams.put("out_trade_no", outTradeNo);
		sortedParams.put("total_fee", Integer.toString(totalFee));
		sortedParams.put("subject", subject);
		sortedParams.put("body", description);
		// TODO
		// if (Tools.string.isNotEmpty(paymentParam.getExtraParam())) {
		// sortedParams.put("extra_common_param", paymentParam.getExtraParam());
		// }

		final String signature = AlipayUtil.signature(sortedParams, privateKey);

		sortedParams.put("sign_type", "MD5"); // 签名类型,默认：MD5
		sortedParams.put("sign", signature);
		return sortedParams;
	}

	@Override
	public AlipayTradeQueryResponse queryOrder(String outTradeNo) throws AlipayApiException {
		AlipayTradeQueryModel model = new AlipayTradeQueryModel();
		model.setOutTradeNo(outTradeNo);

		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		request.setBizModel(model);
		return alipayClient.execute(request);
	}

	@Override
	public AlipayOrderTradeStatus queryOrderForStatus(String outTradeNo) throws AlipayApiException {
		AlipayTradeQueryResponse response = this.queryOrder(outTradeNo);
		String tradeStatus = response.getTradeStatus();
		if ("WAIT_BUYER_PAY".equals(tradeStatus)) {
			return AlipayOrderTradeStatus.USERPAYING;
		}
		else if ("TRADE_SUCCESS".equals(tradeStatus)) {
			return AlipayOrderTradeStatus.SUCCESS;
		}
		logger.error("tradeStatus:" + tradeStatus);
		return AlipayOrderTradeStatus.PAYERROR;
	}

}
