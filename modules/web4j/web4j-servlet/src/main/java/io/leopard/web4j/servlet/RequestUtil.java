package io.leopard.web4j.servlet;

import io.leopard.burrow.util.StringUtil;

import java.util.Enumeration;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 
 * 
 * @author 阿海
 */
public class RequestUtil {
	private static final Log logger = LogFactory.getLog(RequestUtil.class);

	/**
	 * 获取代理服务器IP. .
	 * 
	 * @param request
	 * @return
	 */
	public static String getProxyIp(HttpServletRequest request) {
		String proxyIp = request.getHeader("X-Real-IP");
		if (proxyIp == null) {
			proxyIp = request.getHeader("RealIP");
		}
		if (proxyIp == null) {
			proxyIp = request.getRemoteAddr();
		}
		return proxyIp;
	}

	public static String getRequestContextUri(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String requestURI;
		if ("/".equals(contextPath)) {
			requestURI = request.getRequestURI();
		}
		else {
			String uri = request.getRequestURI();
			requestURI = uri.substring(contextPath.length());
		}
		if (requestURI.indexOf("//") != -1) {
			requestURI = requestURI.replaceAll("/+", "/");
		}
		return requestURI;
	}

	public static HttpServletRequest getCurrentRequest() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attr == null) {
			return null;
		}
		return attr.getRequest();
	}

	/**
	 * 获取上传的文件.
	 * 
	 * @param request
	 *            请求
	 * @param name
	 *            文件名
	 * @return 文件
	 */
	public static MultipartFile getFile(HttpServletRequest request, String name) {
		// try {
		MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;
		MultipartFile file = mrequest.getFile(name); // 发送对象
		if (file == null || file.isEmpty()) {
			return null;
		}
		return file;
		// }
		// catch (ClassCastException e) {
		// logger.warn("ClassCastException " + e.getMessage());
		// return null;
		// }
	}

	/**
	 * 获取域名.
	 * 
	 * @param request
	 * @return
	 */
	public static String getDomain(HttpServletRequest request) {
		String domain = "http://" + request.getServerName();
		return domain;
	}

	/**
	 * 获取user-agent.
	 */
	public static String getUserAgent(HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent");
		return userAgent;
	}

	/**
	 * 把null或无效的页码转成1.
	 * 
	 * @param pageid
	 * @return
	 */
	public static int getPageid(Integer pageid) {
		if (pageid == null || pageid <= 0) {
			return 1;
		}
		return pageid;
	}

	/**
	 * 从请求中获取页码.
	 * 
	 * @param request
	 * @return
	 */
	public static int getPageid(HttpServletRequest request) {
		int pageid = NumberUtils.toInt(request.getParameter("page"));
		if (pageid <= 0) {
			return 1;
		}
		return pageid;
	}

	/**
	 * 打印header信息.
	 * 
	 * @param request
	 */
	public static void printHeaders(HttpServletRequest request) {
		Enumeration<String> e = request.getHeaderNames();
		while (e.hasMoreElements()) {
			String name = e.nextElement();
			String value = request.getHeader(name);
			logger.info(name + ":" + value);
		}
	}

	private static final java.util.regex.Pattern IS_LICIT_IP_PATTERN = java.util.regex.Pattern.compile("^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$");

	/**
	 * 判断IP是否合法.
	 * 
	 * @param ip
	 * @return
	 */
	public static boolean isLicitIp(final String ip) {
		if (StringUtils.isEmpty(ip)) {
			return false;
		}
		Matcher m = IS_LICIT_IP_PATTERN.matcher(ip);
		return m.find();
		// return false;
		// }
		// return true;
	}

	/**
	 * 打印请求中的对象.
	 * 
	 * @param request
	 */
	public static void printAttributes(HttpServletRequest request) {
		Enumeration<String> e = request.getAttributeNames();
		while (e.hasMoreElements()) {
			String name = e.nextElement();
			Object value = request.getAttribute(name);
			logger.info(name + ":" + value);
		}
	}

	/**
	 * 获取上次访问的地址.
	 * 
	 * @param request
	 * @return
	 */
	public static String getReferer(HttpServletRequest request) {
		return request.getHeader("referer");
	}

	/**
	 * 获取请求参数的值，若不存在则返回默认值.
	 * 
	 * @param request
	 * @param parameterName
	 * @param defaultValue
	 * @return
	 */
	public static String getString(HttpServletRequest request, String parameterName, String defaultValue) {
		String value = request.getParameter(parameterName);
		return StringUtils.isEmpty(value) ? defaultValue : value;
	}

	public static String getRequestURL(HttpServletRequest request) {
		boolean isHttps = "true".equals(request.getHeader("isHttps"));
		StringBuilder sb = new StringBuilder(48);
		int port = request.getServerPort();

		String scheme;
		if (isHttps) {
			scheme = "https";
			if (port == 80) {
				port = 443;
			}
		}
		else {
			scheme = "http";
		}

		sb.append(scheme);
		sb.append("://");
		sb.append(request.getServerName());
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
		sb.append(request.getRequestURI());
		return sb.toString();
	}

	/**
	 * 从cookie获取用户通行证.
	 * 
	 * @param request
	 * @return
	 */
	public static String getCookieUsername(HttpServletRequest request) {
		String username = CookieUtil.getCookie("username", request);
		username = StringUtil.urlDecode(username);
		return username;
	}

	// public static void test() {
	// logger.info("test");
	// }

	// public static void main(String[] args) {
	// String str = "192.168.0.1,";
	// int index = str.lastIndexOf(',');
	// String sss = str.substring(index + 1).trim();
	// System.out.println(index + "::" + sss);
	// }

}
