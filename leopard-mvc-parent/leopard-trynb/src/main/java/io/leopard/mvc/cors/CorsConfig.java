package io.leopard.mvc.cors;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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

	private List<String> allowOriginList;

	private boolean enable;

	@PostConstruct
	public void init() {
		List<String> allowOriginList = allowOriginResolver.resolve(cors);
		if (allowOriginList == null) {
			allowOriginList = new ArrayList<>();
		}
		this.allowOriginList = toRegexList(allowOriginList);
		// logger.info("init:" + enable);
		enable = !allowOriginList.isEmpty();
	}

	public boolean isEnable() {
		// System.err.println("cors:" + ENABLE);
		return enable;
	}

	public String getAccessControlAllowOrigin(HttpServletRequest request) {
		String referer = request.getHeader("referer");
		if (StringUtils.isEmpty(referer)) {
			return null;
		}
		return getHostAndPort(referer, allowOriginList);
	}

	/**
	 * 转换成正则表达式
	 * 
	 * @param allowOriginList
	 * @return
	 */
	protected static List<String> toRegexList(List<String> allowOriginList) {
		List<String> regexList = new ArrayList<>();
		for (String origin : allowOriginList) {
			if ("*".equals(origin)) {
				regexList.add("^[a-zA-Z0-9_\\-\\.]+$");
			}
			else if (origin.startsWith("*.")) {
				regexList.add("^[a-zA-Z0-9_\\-\\.]+" + origin.substring(2) + "$");
			}
			else {
				regexList.add("^" + origin + "$");
			}
		}
		return regexList;
	}

	protected static boolean match(String host, List<String> originRegexList) {
		for (String regex : originRegexList) {
			// System.err.println("host:" + host + " regex:" + regex + " match:" + host.matches(regex));
			if (host.matches(regex)) {
				return true;
			}
		}
		return false;

	}

	protected static String getHostAndPort(String referer, List<String> originRegexList) {
		URL url;
		try {
			url = new URL(referer);
		}
		catch (MalformedURLException e) {
			return null;
		}
		String host = url.getHost();

		boolean matched = match(host, originRegexList);
		System.err.println("host:" + host + " matched:" + matched + " originRegexList:" + originRegexList);

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
