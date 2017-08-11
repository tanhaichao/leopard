package io.leopard.im.qcloud;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import io.leopard.httpnb.Httpnb;

public class QcloudImClientImpl extends AbstractQcloudIm implements QcloudImClient {

	@Override
	public String addUser(String username, String password, String nickname) {
		return null;
	}

	@Override
	public boolean kick(String identifier) {
		// usersig=xxx&identifier=admin&sdkappid=88888888&random=99999999&contenttype=json
		// {"ActionStatus":"FAIL","ErrorCode":60008,"ErrorInfo":"service timeout or request format error,please check and try again"}
		String url = "https://console.tim.qq.com/v4/im_open_login_svc/kick";
		Map<String, Object> request = new LinkedHashMap<>();
		request.put("Identifier", identifier);
		String json = super.doRequest(url, request);
		System.err.println(json);
		return false;
	}

	@Override
	public String getGroupList() {
		String url = "https://console.tim.qq.com/v4/group_open_http_svc/get_appid_group_list";
		String json = super.doRequest(url, "GET", null);
		System.err.println(json);
		return null;
	}

}
