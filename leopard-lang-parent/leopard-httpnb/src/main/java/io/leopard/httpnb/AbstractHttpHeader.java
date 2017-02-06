package io.leopard.httpnb;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class AbstractHttpHeader implements HttpHeader {

	private long timeout = -1;
	private String cookie;
	protected String method = "GET";
	private String userAgent = null;

	protected List<Param> paramList = new ArrayList<Param>();

	// public AbstractHttpHeader(String method, long timeout) {
	// this.setMethod(method);
	// this.setTimeout(timeout);
	// }

	@Override
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	@Override
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	@Override
	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public HttpURLConnection openConnection(String url) throws IOException {
		boolean isHttps = url.startsWith("https");
		if (isHttps) {
			Https.trustAllHosts();
		}
		URL oUrl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) oUrl.openConnection();

		if (isHttps) {
			((HttpsURLConnection) conn).setHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
		}

		if (timeout > 0) {
			conn.setConnectTimeout((int) timeout);
			conn.setReadTimeout((int) timeout);
		}
		if (cookie != null && cookie.length() > 0) {
			conn.setRequestProperty("Cookie", cookie);
		}
		if (userAgent != null && userAgent.length() > 0) {
			conn.setRequestProperty("user-agent", userAgent);
		}

		conn.setRequestProperty("X-Real-IP", "127.0.0.1");// FIXME ahai 测试代码

		return conn;
	}

	@Override
	public long getTimeout() {
		return timeout;
	}

	@Override
	public void addParam(Param param) {
		this.paramList.add(param);
	}

	@Override
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

}
