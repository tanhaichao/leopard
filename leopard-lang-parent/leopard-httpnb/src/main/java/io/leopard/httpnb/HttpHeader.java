package io.leopard.httpnb;

import java.io.IOException;
import java.net.HttpURLConnection;

public interface HttpHeader {

	void setTimeout(long timeout);

	long getTimeout();

	void setCookie(String cookie);

	void setMethod(String method);

	HttpURLConnection openConnection(String url) throws IOException;

	void addParam(Param param);

	void setUserAgent(String userAgent);

}
