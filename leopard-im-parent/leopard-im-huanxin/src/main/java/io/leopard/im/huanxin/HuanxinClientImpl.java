package io.leopard.im.huanxin;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import io.leopard.httpnb.Httpnb;
import io.leopard.im.huanxin.model.AccessTokenResponseObject;
import io.leopard.im.huanxin.model.AddUserRequestObject;
import io.leopard.im.huanxin.model.MessageTargetType;
import io.leopard.im.huanxin.model.TokenRequestObject;
import io.leopard.im.huanxin.model.UserResponseObject;
import io.leopard.json.Json;

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
	public UserResponseObject addUser(String username, String password, String nickname) {
		String url = this.getUrl("/users");
		AddUserRequestObject userinfo = new AddUserRequestObject();
		userinfo.setUsername(username);
		userinfo.setPassword(password);
		userinfo.setNickname(nickname);
		String json = this.requestByToken("POST", url, userinfo);
		return this.toUser(json);
	}

	@Override
	public UserResponseObject getUser(String username) {
		String url = this.getUrl("/users/" + username);
		String json = this.requestByToken("GET", url, null);
		return this.toUser(json);
	}

	protected Map<String, Object> toResponseData(String json) {
		// System.err.println("json:" + json);
		Map<String, Object> map = Json.toMap(json);
		String error = (String) map.get("error");
		if (error != null) {
			String message = (String) map.get("error_description");
			throw new RuntimeException(message);
		}
		return map;
	}

	protected UserResponseObject toUser(String json) {
		// System.err.println("json:" + json);
		Map<String, Object> data = this.toResponseData(json);

		@SuppressWarnings("rawtypes")
		List entities = (List) data.get("entities");
		if (entities.size() <= 0) {
			return null;
		}
		return HuanxinJson.toObject(HuanxinJson.toJson(entities.get(0)), UserResponseObject.class);
	}

	@Override
	public boolean sendMessage(List<String> targetList, MessageTargetType type, String from, Object msg) {
		String url = this.getUrl("/messages");
		Map<String, Object> requestObject = new LinkedHashMap<String, Object>();
		requestObject.put("target_type", type.getKey());
		requestObject.put("target", targetList);
		requestObject.put("msg", msg);
		requestObject.put("from", from);
		Json.print(msg, "msg");

		String json = this.requestByToken("POST", url, requestObject);
		Map<String, Object> data = this.toResponseData(json);
		Object result = data.get("data");
		// Json.print(result, "result");
		return true;
	}

}
