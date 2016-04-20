package io.leopard.web4j.servlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * cookie操作
 * 
 * @author 阿海
 */
public class CookieUtil {

	protected static final Log logger = LogFactory.getLog(CookieUtil.class);

	/**
	 * 设置cookie</br>
	 * 
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 * @param request
	 *            http请求
	 * @param response
	 *            http响应
	 */
	public static void setCookie(String name, String value, HttpServletRequest request, HttpServletResponse response) {
		int maxAge = -1;
		CookieUtil.setCookie(name, value, maxAge, request, response);
	}

	/**
	 * 设置cookie</br>
	 * 
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            最大生存时间
	 * @param request
	 *            http请求
	 * @param response
	 *            http响应
	 */
	public static void setCookie(String name, String value, int maxAge, HttpServletRequest request, HttpServletResponse response) {
		boolean httpOnly = false;
		CookieUtil.setCookie(name, value, maxAge, httpOnly, request, response);
	}

	/**
	 * 设置cookie</br>
	 * 
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            最大生存时间
	 * @param httpOnly
	 *            cookie的路径
	 * @param request
	 *            http请求
	 * @param response
	 *            http响应
	 */
	public static void setCookie(String name, String value, int maxAge, boolean httpOnly, HttpServletRequest request, HttpServletResponse response) {
		String domain = request.getServerName();
		// String domain = serverName;
		setCookie(name, value, maxAge, httpOnly, domain, response);
	}

	public static void setCookie(String name, String value, int maxAge, boolean httpOnly, String domain, HttpServletResponse response) {
		if (StringUtils.isEmpty(name)) {
			throw new IllegalArgumentException("cookie名称不能为空.");
		}
		if (StringUtils.isEmpty(value)) {
			throw new IllegalArgumentException("cookie值不能为空.");
		}

		Cookie cookie = new Cookie(name, value);
		cookie.setDomain(domain);
		cookie.setMaxAge(maxAge);
		if (httpOnly) {
			// TODO ahai httponly改动未测试.
			cookie.setPath("/");
			cookie.setHttpOnly(true);
		}
		else {
			cookie.setPath("/");
		}
		// TODO ahai 所有cookie都要写这些值吗?
		response.addHeader("P3P", "CP=\"CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR\"");
		response.addCookie(cookie);
	}

	/**
	 * 获取cookie的值</br>
	 * 
	 * @param name
	 *            cookie名称
	 * @param request
	 *            http请求
	 * @return cookie值
	 */
	public static String getCookie(String name, HttpServletRequest request) {
		if (StringUtils.isEmpty(name)) {
			throw new IllegalArgumentException("cookie名称不能为空.");
		}
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		for (int i = 0; i < cookies.length; i++) {
			if (name.equalsIgnoreCase(cookies[i].getName())) {
				return cookies[i].getValue();
			}
		}
		return null;
	}

	/**
	 * 删除cookie</br>
	 * 
	 * @param name
	 *            cookie名称
	 * @param request
	 *            http请求
	 * @param response
	 *            http响应
	 */
	public static void deleteCookie(String name, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isEmpty(name)) {
			throw new IllegalArgumentException("cookie名称不能为空.");
		}
		CookieUtil.setCookie(name, "", -1, false, request, response);
	}

	/**
	 * 删除cookie</br>
	 * 
	 * @param name
	 *            cookie名称
	 * @param request
	 *            http请求
	 * @param response
	 *            http响应
	 */
	public static void deleteCookie(String name, String domain, HttpServletResponse response) {
		if (StringUtils.isEmpty(name)) {
			throw new IllegalArgumentException("cookie名称不能为空.");
		}
		CookieUtil.setCookie(name, "", -1, false, domain, response);
	}

}
