package io.leopard.web4j.allow.dao;

public class Allow {

	private String uri;
	private String ip;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "Allow [uri=" + uri + ", ip=" + ip + "]";
	}

}
