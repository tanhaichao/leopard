package io.leopard.im.huanxin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import io.leopard.httpnb.Httpnb;
import io.leopard.im.huanxin.model.AccessTokenResponseObject;
import io.leopard.im.huanxin.model.TokenRequestObject;
import io.leopard.json.Json;

public class AbstractHuanxin {

	@Value("${huanxin.appKey}")
	private String appKey;

	@Value("${huanxin.clientId}")
	private String clientId;

	@Value("${huanxin.clientSecret}")
	private String clientSecret;

	private String token;

	protected String getUrl(String path) {
		String url = "https://a1.easemob.com/" + appKey.replace("#", "/") + path;
		return url;
	}

	public String getToken() {
		String url = this.getUrl("/token");
		System.err.println("url:" + url);
		TokenRequestObject tokenRO = new TokenRequestObject();
		tokenRO.setClientId(clientId);
		tokenRO.setClientSecret(clientSecret);
		String requestBody = HuanxinJson.toJson(tokenRO);
		String json = Httpnb.execute(url, new HttpHeaderRequestBodyImpl("POST", requestBody));
		AccessTokenResponseObject object = HuanxinJson.toObject(json, AccessTokenResponseObject.class);
		return object.getAccessToken();
	}

	protected String loadToken() {
		if (token != null) {
			return this.token;
		}
		this.token = this.getToken();
		return this.token;
	}

	protected String requestByToken(String method, String url, Object requestObject) {
		if (token == null) {
			this.loadToken();
		}
		String requestBody = null;
		if (requestObject != null) {
			requestBody = HuanxinJson.toJson(requestObject);
		}

		String authorization = "Bearer " + this.token;
		String json = Httpnb.execute(url, new HttpHeaderRequestBodyImpl(method, requestBody, authorization));
		return json;
	}

	protected Map<String, Object> toResponseData(String json) {
		System.err.println("json:" + json);
		Map<String, Object> map = Json.toMap(json);
		String error = (String) map.get("error");
		if (error != null) {
			String message = (String) map.get("error_description");
			if (message == null) {
				message = error;// TODO
			}
			throw new RuntimeException(message);
		}
		return map;
	}

}
