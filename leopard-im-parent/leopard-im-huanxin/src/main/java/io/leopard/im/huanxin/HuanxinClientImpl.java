package io.leopard.im.huanxin;

import org.springframework.beans.factory.annotation.Value;

import io.leopard.httpnb.Httpnb;
import io.leopard.im.huanxin.model.TokenRequestObject;
import io.leopard.im.huanxin.model.UserResponseObject;
import io.leopard.json.Json;

public class HuanxinClientImpl implements HuanxinClient {

	@Value("${huanxin.orgName}")
	private String orgName;

	@Value("${huanxin.appName}")
	private String appName;

	@Value("${huanxin.clientId}")
	private String clientId;

	@Value("${huanxin.clientSecret}")
	private String clientSecret;

	private String token;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	protected String getUrl(String path) {
		String url = "https://a1.easemob.com/" + orgName + "/" + appName + path;
		return url;
	}

	@Override
	public String getToken() {
		String url = this.getUrl("/token");
		TokenRequestObject tokenRO = new TokenRequestObject();
		tokenRO.setClientId(clientId);
		tokenRO.setClientSecret(clientSecret);
		String requestBody = Json.toJson(tokenRO);
		String json = Httpnb.execute(url, new HttpHeaderRequestBodyImpl("POST", requestBody));
		return json;
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
			requestBody = Json.toJson(requestObject);
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
