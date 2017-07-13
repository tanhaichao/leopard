package io.leopard.mvc.cors;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

public class CorsConfig {

	public static boolean isEnable() {
		return true;
	}

	public static String getAccessControlAllowOrigin(HttpServletRequest request) {
		String referer = request.getHeader("referer");
		if (StringUtils.isEmpty(referer)) {
			return "*";
		}

		try {
			return getHostAndPort(referer);
		}
		catch (MalformedURLException e) {
			return "*";
		}
	}

	private static String getHostAndPort(String referer) throws MalformedURLException {
		URL url = new URL(referer);
		String host = url.getHost();
		int port = url.getPort();
		String scheme = url.getProtocol();
		StringBuilder sb = new StringBuilder(48);

		sb.append(scheme);
		sb.append("://");

		sb.append(host);
		if (port == 80 && "http".equals(scheme)) {
			//
		}
		else if (port == 443 && "https".equals(scheme)) {
			//
		}
		else {
			sb.append(':');
			sb.append(port);
		}
		return sb.toString();
	}

}
