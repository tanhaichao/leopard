package io.leopard.mvc.cors;

import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

public class CorsConfig {
	protected Log logger = LogFactory.getLog(this.getClass());

	@Value("${mvc.cors}")
	private String enable;

	private static boolean ENABLE;

	@PostConstruct
	public void init() {
		// logger.info("init:" + enable);
		ENABLE = "true".equals(enable);
	}

	public static boolean isEnable() {
		// System.err.println("cors:" + ENABLE);
		return ENABLE;
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
		if ((port == -1 || port == 80) && "http".equalsIgnoreCase(scheme)) {
			//
		}
		else if ((port == -1 || port == 443) && "https".equalsIgnoreCase(scheme)) {
			//
		}
		else {
			sb.append(':');
			sb.append(port);
		}
		return sb.toString();
	}

}
