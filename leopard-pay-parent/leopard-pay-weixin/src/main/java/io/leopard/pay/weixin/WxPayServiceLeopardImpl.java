package io.leopard.pay.weixin;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.binarywang.wxpay.bean.request.WxPayMicropayRequest;
import com.github.binarywang.wxpay.bean.result.WxPayBaseResult;
import com.github.binarywang.wxpay.bean.result.WxPayMicropayResult;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.github.binarywang.wxpay.util.SignUtils;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;

public class WxPayServiceLeopardImpl extends WxPayServiceImpl {
	private static final String PAY_BASE_URL = "https://api.mch.weixin.qq.com";

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public WxPayMicropayResult micropay(WxPayMicropayRequest request) throws WxErrorException {
		request.checkAndSign(this.getConfig());

		String url = this.getPayBaseUrl() + "/pay/micropay";
		String responseContent = this.post(url, request.toXML());

		WxPayMicropayResult result = WxPayBaseResult.fromXML(responseContent, WxPayMicropayResult.class);

		String resultCode = result.getResultCode();
		String errCode = result.getErrCode();
		System.out.println("resultCode:" + resultCode + " errCode:" + errCode);

		this.checkResult(this, result);
		return result;
	}

	/**
	 * 校验返回结果签名
	 */
	public void checkResult(WxPayServiceImpl wxPayService, WxPayMicropayResult result) throws WxErrorException {
		// 校验返回结果签名
		Map<String, String> map = result.toMap();
		if (result.getSign() != null && !SignUtils.checkSign(map, wxPayService.getConfig().getMchKey())) {
			throw new WxErrorException(WxError.newBuilder().setErrorCode(-1).setErrorMsg("参数格式校验错误！").build());
		}

		// 校验结果是否成功
		if (!"SUCCESS".equalsIgnoreCase(result.getReturnCode()) || !"SUCCESS".equalsIgnoreCase(result.getResultCode())) {
			StringBuilder errorMsg = new StringBuilder();
			if (result.getReturnCode() != null) {
				errorMsg.append("返回代码：").append(result.getReturnCode());
			}
			if (result.getReturnMsg() != null) {
				errorMsg.append("，返回信息：").append(result.getReturnMsg());
			}
			if (result.getResultCode() != null) {
				errorMsg.append("，结果代码：").append(result.getResultCode());
			}
			if (result.getErrCode() != null) {
				errorMsg.append("，错误代码：").append(result.getErrCode());
			}
			if (result.getErrCodeDes() != null) {
				errorMsg.append("，错误详情：").append(result.getErrCodeDes());
			}
			WxError error = WxError.newBuilder().setErrorCode(-1).setErrorMsg(errorMsg.toString()).build();
			throw new WxErrorException(error);
		}
	}

	private String post(String url, String xmlParam) {
		String requestString = xmlParam;
		try {
			requestString = new String(xmlParam.getBytes(CharEncoding.UTF_8), CharEncoding.ISO_8859_1);
		}
		catch (UnsupportedEncodingException e) {
			// 实际上不会发生该异常
			e.printStackTrace();
		}

		HttpRequest request = HttpRequest.post(url).body(requestString);
		HttpResponse response = request.send();
		String responseString = null;
		try {
			responseString = new String(response.bodyText().getBytes(CharEncoding.ISO_8859_1), CharEncoding.UTF_8);
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		this.log.debug("\n[URL]: {}\n[PARAMS]: {}\n[RESPONSE]: {}", url, xmlParam, responseString);
		return responseString;
	}

	private String getPayBaseUrl() {
		if (this.getConfig().useSandboxForWxPay()) {
			return PAY_BASE_URL + "/sandboxnew";
		}

		return PAY_BASE_URL;
	}
}
