package io.leopard.httpnb;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

public class HttpHeaderPostImpl extends AbstractHttpHeader {

	public HttpHeaderPostImpl(long timeout) {
		this.setMethod("POST");
		this.setTimeout(timeout);
	}

	@Override
	public HttpURLConnection openConnection(String url) throws IOException {
		HttpURLConnection conn = super.openConnection(url);

		conn.setUseCaches(false); // do not use cache
		conn.setDoOutput(true); // use for output
		conn.setDoInput(true); // use for Input

		conn.setRequestMethod("POST");

		if (!this.paramList.isEmpty()) {
			String queryString = this.getQueryString();
			PrintWriter out = new PrintWriter(conn.getOutputStream());
			out.print(queryString); // send to server
			out.close(); // close outputstream
		}
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
}
