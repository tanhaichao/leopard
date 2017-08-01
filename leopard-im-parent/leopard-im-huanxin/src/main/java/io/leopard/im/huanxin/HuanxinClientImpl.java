package io.leopard.im.huanxin;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.leopard.im.huanxin.model.AddUserRequestObject;
import io.leopard.im.huanxin.model.MessageTargetType;
import io.leopard.im.huanxin.model.UserResponseObject;
import io.leopard.json.Json;

public class HuanxinClientImpl extends AbstractHuanxin implements HuanxinClient {

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

	@Override
	public boolean disconnect(String username) {
		String url = this.getUrl("/users/" + username + "/disconnect");
		String json = this.requestByToken("GET", url, null);
		Map<String, Object> data = this.toResponseData(json);
		return (boolean) data.get("result");
	}

}
