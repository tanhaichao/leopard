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
	public String getGroupList() {
		String userSig = this.getUserSign();
		String url = "https://console.tim.qq.com/v4/group_open_http_svc/get_appid_group_list";
		Map<String, Object> params = new HashMap<>();
		params.put("contenttype", "json");
		params.put("usersig", userSig);
		params.put("random", "99999999");
		params.put("sdkappid", "88888888");
		params.put("identifier", "admin");
		String json = Httpnb.doPost(url, params);
		System.err.println(json);

		return null;
	}

}
