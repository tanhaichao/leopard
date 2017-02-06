package io.leopard.rpc;

public interface Client {
	String getUserAgent();

	int getAppid();

	int getTimeout();

	void setUserAgent(String userAgent);

	void setAppid(int appid);

	void setTimeout(int timeout);

	void setClientInfo(String userAgent, int appid, int timeout);

}
