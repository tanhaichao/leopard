package io.leopard.mvc.cors;

import javax.servlet.http.HttpServletRequest;

public class CorsConfig {

	public static boolean isEnable() {
		return true;
	}

	public static String getAccessControlAllowOrigin(HttpServletRequest request) {
		return "*";
	}

}
