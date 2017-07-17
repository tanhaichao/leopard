package io.leopard.im.huanxin;

import io.leopard.httpnb.Httpnb;
import io.leopard.im.huanxin.model.TokenRequestObject;
import io.leopard.json.Json;

public class HuanxinClientImpl implements HuanxinClient {
	
	private String orgName;

	private String appName;

	private String clientId;

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

	@Override
	public String getToken() {
		String url = "https://a1.easemob.com/" + orgName + "/" + appName + "/token";
		TokenRequestObject tokenRO = new TokenRequestObject();
		tokenRO.setClientId(clientId);
		tokenRO.setClientSecret(clientSecret);
		String requestBody = Json.toJson(tokenRO);
		String json = Httpnb.execute(url, new HttpHeaderRequestBodyImpl(requestBody));
		return json;
	}

	protected String loadToken() {
		if (token != null) {
			return this.token;
		}
		this.token = this.getToken();
		return this.token;
	}

	protected String requestByToken(String url, Object requestObject) {
		if (token == null) {
			this.loadToken();
		}
		String requestBody = Json.toJson(requestObject);
		String authorization = "Bearer " + this.token;
		String json = Httpnb.execute(url, new HttpHeaderRequestBodyImpl(requestBody, authorization));
		return json;
	}

}
