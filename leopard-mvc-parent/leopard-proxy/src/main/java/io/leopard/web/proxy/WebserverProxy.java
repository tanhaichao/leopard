package io.leopard.web.proxy;

import io.leopard.web.servlet.CookieUtil;
import io.leopard.web.servlet.RequestUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WebserverProxy {
	private static final Log logger = LogFactory.getLog(WebserverProxy.class);

	private static final Set<String> IGNORE_HEADER = new HashSet<String>();
	static {
		IGNORE_HEADER.add(null);
		IGNORE_HEADER.add("Content-Length");
	}

	protected static String getResinIp(HttpServletRequest request) {
		String resinIp = request.getParameter("resinIp");
		if (resinIp == null || resinIp.length() == 0) {
			resinIp = CookieUtil.getCookie("resinIp", request);
		}
		return resinIp;
	}

	/**
	 * 
	 * @param uri
	 * @param request
	 * @return null:表示未使用代码.
	 * @throws IOException
	 */
	public static boolean proxy(String uri, HttpServletRequest request, ServletResponse res) throws IOException {
		if (!uri.startsWith("/monitor/")) {
			return false;
		}

		String resinIp = getResinIp(request);
		if (resinIp == null || resinIp.length() == 0) {
			return false;
		}

		// FIXME ahai 要判断是否本系统的IP

		try {
			doProxy(resinIp, uri, request, res);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			// String body = e.getMessage();
			outputError(res, e.toString());
		}
		return true;
	}

	protected static void outputError(ServletResponse res, String message) throws IOException {
		// message = ErrorUtil.replaceMessage(message);

		StringBuilder sb = new StringBuilder();
		sb.append(message);
		sb.append("<script type=\"text/javascript\">\n");
		sb.append("var Cookie = {\n");
		sb.append("\t\"addCookie\" : function(name, value) {\n");
		sb.append("\tvar str = name + \"=\" + escape(value);\n");
		sb.append("\tstr += \";path=/\";\n");
		sb.append("\tdocument.cookie = str;\n");
		sb.append("}\n");
		sb.append("}\n");
		sb.append("function selectResinServer(resinIp) {\n");
		sb.append("\tCookie.addCookie(\"resinIp\", resinIp);\n");
		sb.append("\tdocument.location.href = document.location.href;\n");
		sb.append("}\n");
		sb.append("</script>\n");

		sb.append("<a href=\"javascript:selectResinServer('');\">访问默认服务器</a>\n");

		res.setContentType("text/html; charset=UTF-8");

		String body = sb.toString();
		res.setContentLength(body.getBytes().length);
		Writer out = res.getWriter();
		out.write(body);
		out.flush();
	}

	protected static String doProxy(String resinIp, String uri, HttpServletRequest request, ServletResponse res) throws IOException {
		// if (!LeopardValidUtil.isValidIp(resinIp)) {
		// throw new IpInvalidException(resinIp, "非法resinIp[" + resinIp + "]");
		// }
		if (!"get".equalsIgnoreCase(request.getMethod())) {
			throw new IllegalArgumentException("只支持get方式请求.");
		}

		String proxyIp = RequestUtil.getProxyIp(request);
		String host = request.getServerName();
		String url = getUrl(resinIp, uri, request);

		// proxyIp = "58.215.138.22";

		int time = 60 * 1000;// 60秒超时
		URL oUrl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) oUrl.openConnection();
		conn.setConnectTimeout(time);
		conn.setReadTimeout(time);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Host", host);
		conn.setRequestProperty("X-Real-IP", proxyIp);
		conn.setRequestProperty("User-Agent", "LeopardResinProxy");

		// GET http://notice.lobby.duowan.com/monitor/performance.do?resinIp=58.215.138.22&name1= HTTP/1.1
		// Accept: text/html, application/xhtml+xml, */*
		// Accept-Language: zh-CN
		// User-Agent: Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)
		// Accept-Encoding: gzip, deflate
		// Connection: Keep-Alive
		// Host: notice.lobby.duowan.com
		// Pragma: no-cache
		// Cookie: lobby_cck=cck; UDBSESSIONID=a8507468-f813-4719-8e81-6b59dc1aad36; b_Admin_visibility=hidden
		//

		conn.setDoOutput(true);

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		String str;
		StringBuilder sb = new StringBuilder();
		while ((str = in.readLine()) != null) {
			if (sb.length() > 0) {
				sb.append('\n');
			}
			sb.append(str);
		}
		in.close();
		conn.disconnect();

		String body = sb.toString();

		int contentLength = body.getBytes().length;
		HttpServletResponse response = (HttpServletResponse) res;

		int status = conn.getResponseCode();
		response.setStatus(status);
		res.setContentLength(contentLength);

		// Map<String, List<String>> headerMap = conn.getHeaderFields();
		// for (Map.Entry<String, List<String>> entry : headerMap.entrySet()) {
		// String name = entry.getKey();
		// if (IGNORE_HEADER.contains(name)) {
		// continue;
		// }
		// else {
		// response.setHeader(name, entry.getValue().get(0));
		// }
		//
		// logger.info("header name: " + entry.getKey() + " ,value : " + entry.getValue());
		// }

		Writer out = response.getWriter();
		out.write(body);
		out.flush();

		logger.info("done proxy, proxyIp:" + proxyIp + " url:" + url);
		return body;
	}

	// private static final Pattern STATUS_CODE_PATTERN = Pattern.compile(" ([0-9]+) ");
	//
	// protected static int parseStatusCode(List<String> list) {
	// if (ListUtil.isEmpty(list)) {
	// logger.error("解析状态码出错,list:" + list);
	// throw new IllegalArgumentException("状态码信息怎么会为空?");
	// }
	// // [HTTP/1.1 200 OK]
	// String value = list.get(0);
	// Matcher m = STATUS_CODE_PATTERN.matcher(value);
	// if (m.find()) {
	// return Integer.parseInt(m.group(1));
	// }
	// logger.error("解析状态码出错,value:" + value);
	// throw new IllegalArgumentException("解析状态码出错.");
	//
	// }

	protected static String getUrl(String resinIp, String uri, HttpServletRequest request) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append("http://").append(resinIp + ":8081").append(uri);
		Map<String, String[]> map = request.getParameterMap();
		if (map != null && !map.isEmpty()) {
			StringBuilder queryString = new StringBuilder();
			for (Entry<String, String[]> entry : map.entrySet()) {
				String name = entry.getKey();
				if ("resinIp".equals(name)) {
					continue;
				}
				String[] values = entry.getValue();
				// System.err.println("name:" + name + " values:" + StringUtils.join(values, ","));
				for (String value : values) {
					if (queryString.length() > 0) {
						queryString.append("&");
					}
					queryString.append(name).append("=").append(URLEncoder.encode(value, "UTF-8"));
				}
			}
			if (queryString.length() > 0) {
				sb.append("?").append(queryString.toString());
			}
		}

		return sb.toString();

	}
}
