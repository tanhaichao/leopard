package io.leopard.rpc;

public class ClientBase implements Client {
	protected String userAgent;
	protected int appid;
	protected int timeout = 10;

	@Override
	public String getUserAgent() {
		return userAgent;
	}

	@Override
	public int getAppid() {
		return appid;
	}

	@Override
	public int getTimeout() {
		return timeout;
	}

	@Override
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	@Override
	public void setAppid(int appid) {
		this.appid = appid;
	}

	@Override
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setClientInfo(String userAgent, int timeout) {
		this.setClientInfo(userAgent, 0, timeout);
	}

	@Override
	public void setClientInfo(String userAgent, int appid, int timeout) {
		this.setUserAgent(userAgent);
		this.setAppid(appid);
		this.setTimeout(timeout);
	}

}
