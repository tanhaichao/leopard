package io.leopard.web4j.parameter;

import io.leopard.web4j.servlet.CookieUtil;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;

/**
 * 获取cookie中的uid(不做登录验证).
 * 
 * @author 阿海
 * 
 */

@Service
public class CookieUidPageParameter implements PageParameter {

	@Override
	public String getValue(HttpServletRequest request) {
		String cookie = CookieUtil.getCookie("uid", request);
		long uid = NumberUtils.toLong(cookie);
		return Long.toString(uid);
	}

	@Override
	public String getKey() {
		return "cookieUid";
	}

}
