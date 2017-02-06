package io.leopard.httpnb;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.List;

public class HttpHeaderDeleteImpl extends AbstractHttpHeader {

	public HttpHeaderDeleteImpl(long timeout) {
		this.setMethod("DELETE");
		this.setTimeout(timeout);
	}

	@Override
	public HttpURLConnection openConnection(String url) throws IOException {
		if (!this.paramList.isEmpty()) {
			url = this.parseUrl(url, paramList);
		}
		// System.err.println("url:" + url);
		HttpURLConnection conn = super.openConnection(url);
		conn.setRequestMethod("DELETE");
		return conn;
	}

	protected String parseUrl(String url, List<Param> paramList) throws UnsupportedEncodingException {
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
		String queryString = sb.toString();
		// System.err.println("queryString:" + queryString);
		if (url.indexOf("?") == -1) {
			return url + "?" + queryString;
		}
		else {
			return url + "&" + queryString;
		}
	}
}
