package io.leopard.httpnb;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class HttpHeaderImpl implements HttpHeader {

	private long timeout = -1;
	private String cookie;
	private String method = "GET";
	private String userAgent = null;

	private List<Param> paramList = new ArrayList<Param>();

	public HttpHeaderImpl(String method, long timeout) {
		this.setMethod(method);
		this.setTimeout(timeout);
	}

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

		if ("POST".equalsIgnoreCase(this.method)) {
			conn.setUseCaches(false); // do not use cache
			conn.setDoOutput(true); // use for output
			conn.setDoInput(true); // use for Input
		}

		conn.setRequestMethod(this.method);

		if (!this.paramList.isEmpty()) {
			String queryString = this.getQueryString();
			PrintWriter out = new PrintWriter(conn.getOutputStream());
			out.print(queryString); // send to server
			out.close(); // close outputstream
		}

		// return execute(conn);
		return conn;
	}

	protected String getQueryString() throws UnsupportedEncodingException {
		if (this.paramList.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Param param : paramList) {
			if (param.getValue() == null) {
				continue;
			}
			if (sb.length() > 0) {
				sb.append('&');
			}
			sb.append(param.getName()).append('=').append(URLEncoder.encode(param.getValue().toString(), "UTF-8"));
		}
		return sb.toString();
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
