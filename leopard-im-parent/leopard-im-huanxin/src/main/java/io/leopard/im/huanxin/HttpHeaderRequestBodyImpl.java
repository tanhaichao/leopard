package io.leopard.im.huanxin;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;

import io.leopard.httpnb.AbstractHttpHeader;

public class HttpHeaderRequestBodyImpl extends AbstractHttpHeader {

	private String requestBody;

	public HttpHeaderRequestBodyImpl(String requestBody) {
		this(requestBody, null);
	}

	public HttpHeaderRequestBodyImpl(String requestBody, String authorization) {
		this.requestBody = requestBody;
		this.authorization = authorization;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	@Override
	public HttpURLConnection openConnection(String url) throws IOException {
		HttpURLConnection conn = super.openConnection(url);
		if (authorization != null) {
			conn.setRequestProperty("Authorization", authorization);
		}
		conn.setUseCaches(false); // do not use cache
		conn.setDoOutput(true); // use for output
		conn.setDoInput(true); // use for Input
		conn.setRequestMethod(this.method);

		if (requestBody != null && requestBody.length() > 0) {
			PrintWriter out = new PrintWriter(conn.getOutputStream());
			out.print(requestBody); // send to server
			out.close(); // close outputstream
		}
		return conn;
	}
}
