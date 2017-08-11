package io.leopard.im.qcloud;

import java.util.HashMap;
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

		String userSig = this.getUserSign(identifier);
		String url = "https://console.tim.qq.com/v4/im_open_login_svc/kick";
		Map<String, Object> params = new HashMap<>();
		params.put("contenttype", "json");
		params.put("apn", "1");
		params.put("usersig", userSig);
		// params.put("random", "99999999");
		params.put("sdkappid", appId);
		params.put("identifier", identifier);

		url = super.getUrl(url, params);

		String json = Httpnb.doGet(url);
		System.err.println("appId:" + appId + " identifier:" + identifier);
		System.err.println("userSig:" + userSig);
		System.err.println(json);

		return false;
	}

	@Override
	public String getGroupList() {
		String userSig = this.getUserSign(identifier);
		String url = "https://console.tim.qq.com/v4/group_open_http_svc/get_appid_group_list";
		Map<String, Object> params = new HashMap<>();
		params.put("contenttype", "json");
		params.put("apn", "1");
		params.put("usersig", userSig);
		// params.put("random", "99999999");
		params.put("sdkappid", appId);
		params.put("identifier", identifier);

		url = super.getUrl(url, params);

		String json = Httpnb.doGet(url);
		System.err.println("appId:" + appId + " identifier:" + identifier);
		System.err.println("userSig:" + userSig);
		System.err.println(json);

		return null;
	}

}
