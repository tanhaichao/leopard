package io.leopard.mvc.cors;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

/**
 * 跨域配置
 * 
 * @author 谭海潮
 *
 */
public class CorsConfig {
	protected Log logger = LogFactory.getLog(this.getClass());

	@Value("${mvc.cors}")
	private String cors;

	@Autowired
	private AllowOriginResolver allowOriginResolver;

	@PostConstruct
	public void init() {
		// logger.info("cors:" + cors);
		if ("${mvc.cors}".equals(cors)) {// TODO
			cors = null;
		}
		allowOriginResolver.setCors(cors);
	}

	public boolean isEnable() {
		return allowOriginResolver.isEnable();
	}

	public String getAccessControlAllowOrigin(HttpServletRequest request) {
		String referer = request.getHeader("referer");
		if (StringUtils.isEmpty(referer)) {
			return null;
		}
		return getHostAndPort(referer);
	}

	protected String getHostAndPort(String referer) {
		URL url;
		try {
			url = new URL(referer);
		}
		catch (MalformedURLException e) {
			return null;
		}
		String host = url.getHost();

		boolean matched = allowOriginResolver.match(host);
		// System.err.println("host:" + host + " matched:" + matched + " originRegexList:" + originRegexList);
		if (!matched) {
			return null;
		}

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
