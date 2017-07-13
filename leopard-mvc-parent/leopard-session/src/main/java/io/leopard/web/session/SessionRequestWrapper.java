package io.leopard.web.session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionRequestWrapper extends HttpServletRequestWrapper {

	public static final String SESSIONID_COOKIE_NAME = "SESSIONID";

	private final String sid;
	private HttpSession session;
	private static int expiry = 86400;// 1天

	public static void setExpiry(int expiry) {
		// System.out.println("session expiry:" + expiry);
		if (expiry < 100) {
			throw new IllegalArgumentException("session超时时间不能小于100[" + expiry + "].");
		}
		SessionRequestWrapper.expiry = expiry;
	}

	// protected HttpServletRequest request;
	protected HttpServletResponse response;

	public SessionRequestWrapper(HttpServletRequest request, HttpServletResponse response) {
		super(request);
		this.response = response;
		this.sid = this.createJsessionIdCookie();
	}

	private String createJsessionIdCookie() {
		String sid = getCookie(SESSIONID_COOKIE_NAME);
		if (sid == null || sid.length() == 0) {
			// 写cookie
			sid = java.util.UUID.randomUUID().toString();
			this.setCookie(SESSIONID_COOKIE_NAME, sid);
			this.setAttribute(SESSIONID_COOKIE_NAME, sid);
		}
		return sid;
	}

	/**
	 * 获取cookie的值</br>
	 * 
	 * @param name cookie名称
	 * @param request http请求
	 * @return cookie值
	 */
	private String getCookie(String name) {
		if (name == null || name.length() == 0) {
			throw new IllegalArgumentException("cookie名称不能为空.");
		}
		Cookie[] cookies = ((HttpServletRequest) this.getRequest()).getCookies();
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

	private void setCookie(String name, String value) {
		if (name == null || name.length() == 0) {
			throw new IllegalArgumentException("cookie名称不能为空.");
		}
		if (value == null || value.length() == 0) {
			throw new IllegalArgumentException("cookie值不能为空.");
		}

		Cookie cookie = new Cookie(name, value);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		// response.addHeader("P3P", "CP=\"CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR\"");
		response.addCookie(cookie);
	}

	@Override
	public HttpSession getSession(boolean create) {
		// System.out.println("getSession:" + create);
		if (!Store.getInstance().isEnable()) {
			return super.getSession(true);
		}
		if (session != null) {
			return session;
		}
		if (create) {
			this.session = new SessionWrapper(this.sid, expiry);
			return session;
		}
		else {
			return null;
		}
	}

	@Override
	public HttpSession getSession() {
		return this.getSession(true);
	}
}
