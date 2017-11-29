package io.leopard.pay.weixin;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.github.binarywang.wxpay.bean.request.WxPayMicropayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayOrderReverseRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayMicropayResult;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayOrderReverseResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;

import io.leopard.burrow.util.StringUtil;
import io.leopard.jdbc.Jdbc;
import io.leopard.json.Json;
import io.leopard.lang.DecimalUtil;
import io.leopard.lang.inum.EnumConstantInvalidException;
import io.leopard.lang.inum.EnumUtil;

public class WeixinPayClientImpl implements WeixinPayClient {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Value("${weixin.appId}")
	private String appId;

	@Value("${weixin.mchId}")
	private String mchId;

	@Value("${weixin.mchKey}")
	private String mchKey;

	@Autowired
	private Jdbc jdbc;

	private WxPayService wxPayService;

	private WeixinPayDao weixinPayDao;

	@PostConstruct
	public void init() {
		WxPayConfig payConfig = new WxPayConfig();
		payConfig.setAppId(appId);
		payConfig.setMchId(mchId);
		payConfig.setMchKey(mchKey);
		wxPayService = new WxPayServiceImpl();
		wxPayService.setConfig(payConfig);

		this.weixinPayDao = new WeixinPayDaoMysqlImpl(jdbc);
	}

	@Override
	public String shortUrl(String longUrl) throws WxPayException {
		String shortUrl = wxPayService.shorturl(longUrl);
		return shortUrl;
	}

	@Override
	public WxPayUnifiedOrderResult unifiedOrder(String orderNo, TradeType tradeType, double amount, String body, String detail, String notifyUrl, String spbillCreateIp) throws WxPayException {
		String paymentId = StringUtil.uuid();
		weixinPayDao.add(paymentId, orderNo);

		int totalFee = (int) DecimalUtil.multiply(amount, 100);// TODO

		WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
		request.setOutTradeNo(paymentId);// 商户订单号
		request.setTotalFee(totalFee);// 订单总金额
		// request.setProductId(productId);// 商品ID
		request.setBody(body);// 商品描述
		request.setDetail(detail);// 商品详细介绍
		request.setNotifyURL(notifyUrl);
		request.setTradeType(tradeType.getKey());// JSAPI，NATIVE，APP
		request.setSpbillCreateIp(spbillCreateIp);
		WxPayUnifiedOrderResult result = wxPayService.unifiedOrder(request);
		Json.print(result, "result");
		// wxPayService.downloadBill(billDate, billType, tarType, deviceInfo);
		return result;
	}

	@Override
	public WxPayService getWxPayService() {
		return wxPayService;
	}

	@Override
	public WeixinMicropayStatus micropayForStatus(String orderNo, int totalFee, String body, String authCode, String spbillCreateIp) throws WeixinPayException {
		try {
			WxPayMicropayResult result = this.micropay(orderNo, totalFee, body, authCode, spbillCreateIp);
			Json.print(result, "result");
			return WeixinMicropayStatus.SUCCESS;
		}
		catch (WeixinPayException e) {
			logger.error(e.getMessage(), e);
			String errCode = e.getErrCode().toLowerCase();

			try {
				WeixinMicropayStatus status = EnumUtil.toEnum(errCode, WeixinMicropayStatus.class);
				return status;
			}
			catch (EnumConstantInvalidException e2) {
				logger.error("resultCode:" + e.getResultCode() + " errCode:" + e.getErrCode() + " errMsg:" + e.getErrCodeDes());
				throw e;
			}
		}
	}

	@Override
	public WxPayMicropayResult micropay(String orderNo, int totalFee, String body, String authCode, String spbillCreateIp) throws WeixinPayException {
		String paymentId = StringUtil.uuid();
		weixinPayDao.add(paymentId, orderNo);

		// 接口文档地址: https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_10&index=1
		WxPayMicropayRequest.Builder builder = WxPayMicropayRequest.newBuilder();// .appid(appId).mchId(mchId);
		// builder .nonceStr(nonceStr);
		// builder .sign(sign)
		builder.outTradeNo(paymentId);
		builder.totalFee(totalFee);

		builder.body(body);
		builder.spbillCreateIp(spbillCreateIp);
		builder.authCode(authCode);
		WxPayMicropayRequest request = builder.build();
		try {
			return wxPayService.micropay(request);
		}
		catch (WxPayException e) {
			throw new WeixinPayException("访问刷卡支付接口出现异常.", e);
		}
	}

	@Override
	public WxPayOrderQueryResult queryOrder(String orderNo) throws WxPayException {
		String paymentId = weixinPayDao.getPaymentId(orderNo);
		if (StringUtils.isEmpty(paymentId)) {
			throw new RuntimeException("订单[" + orderNo + "]支付记录不存在.");
		}
		// paymentId = paymentId.substring(0, 31) + "x";
		return wxPayService.queryOrder(null, paymentId);
	}

	@Override
	public WeixinOrderTradeStatus queryOrderForStatus(String orderNo) throws WxPayException {
		WxPayOrderQueryResult result = this.queryOrder(orderNo);
		// SUCCESS—支付成功
		// REFUND—转入退款
		// NOTPAY—未支付
		// CLOSED—已关闭
		// REVOKED—已撤销（刷卡支付）
		// USERPAYING--用户支付中
		// PAYERROR--支付失败(其他原因，如银行返回失败)
		// 支付状态机请见下单API页面
		String tradeState = result.getTradeState();
		return EnumUtil.toEnum(tradeState, WeixinOrderTradeStatus.class);
	}

	@Override
	public WxPayOrderReverseResult reverseOrder(String orderNo) throws WxPayException {
		String paymentId = weixinPayDao.getPaymentId(orderNo);
		if (StringUtils.isEmpty(paymentId)) {
			throw new RuntimeException("订单[" + orderNo + "]支付记录不存在.");
		}
		WxPayOrderReverseRequest.Builder builder = WxPayOrderReverseRequest.newBuilder();
		builder.outTradeNo(paymentId);
		WxPayOrderReverseRequest request = builder.build();

		return this.wxPayService.reverseOrder(request);
	}
}
