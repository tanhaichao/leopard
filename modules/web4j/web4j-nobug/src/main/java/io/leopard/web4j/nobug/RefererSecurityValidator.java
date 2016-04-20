package io.leopard.web4j.nobug;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 来源安全验证器.
 * 
 * @author 阿海
 * 
 */
public class RefererSecurityValidator {

	private static Map<String, String> DOMAIN_WHITE_MAP = new ConcurrentHashMap<String, String>();

	// public static void addDoaminWhiteList(Set<String> set) {
	// if (set != null) {
	// for (String domain : set) {
	// DOMAIN_WHITE_MAP.put(domain, "");
	// }
	// }
	// }

	public static void setDoaminWhiteList(Set<String> set) {
		Map<String, String> map = new ConcurrentHashMap<String, String>();
		if (set != null) {
			for (String domain : set) {
				map.put(domain, "");
			}
		}
		DOMAIN_WHITE_MAP = map;
	}

	public static void checkReferer(HttpServletRequest request) {
		checkReferer(request, DOMAIN_WHITE_MAP);
	}

	public static void checkReferer(HttpServletRequest request, Map<String, String> domainWhiteListMap) {
		String referer = request.getHeader("referer");
		if (StringUtils.isEmpty(referer)) {
			throw new RefererInvalidException("来源信息不能为空.");// FIXME ahai 注释了代码
			// return;
		}
		String currentDoamin = request.getServerName();
		String refererDoamin = parseDomain(referer);
		if (currentDoamin.equals(refererDoamin)) {
			// 本域名放行
			return;
		}
		if (domainWhiteListMap.containsKey(refererDoamin)) {
			// 白名单域名放行.
			return;
		}
		throw new RefererInvalidException("非法请求[" + currentDoamin + " " + referer + "].");
		// System.err.println("currentDoamin:" + currentDoamin + " referer:" + referer);
	}

	public static String parseDomain(String referer) {
		referer = referer.toLowerCase();
		String regex = "^(http|https)://([a-z0-9\\.\\-_]+)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(referer);
		if (m.find()) {
			return m.group(2);
		}
		System.err.println("根据referer[" + referer + "]解析域名出错.");
		return null;
	}
}
