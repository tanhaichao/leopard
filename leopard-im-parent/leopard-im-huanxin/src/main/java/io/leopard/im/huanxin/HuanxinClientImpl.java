package io.leopard.im.huanxin;

import org.springframework.beans.factory.annotation.Value;

import io.leopard.httpnb.Httpnb;
import io.leopard.im.huanxin.model.AccessTokenResponseObject;
import io.leopard.im.huanxin.model.TokenRequestObject;
import io.leopard.im.huanxin.model.UserResponseObject;

public class HuanxinClientImpl implements HuanxinClient {

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

	@Override
	public String getToken() {
		String url = this.getUrl("/token");
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

	@Override
	public UserResponseObject getUser(String username) {
		String url = this.getUrl("/users/" + username);
		String json = this.requestByToken("GET", url, null);
		System.err.println("json:" + json);
		return null;
	}

}
