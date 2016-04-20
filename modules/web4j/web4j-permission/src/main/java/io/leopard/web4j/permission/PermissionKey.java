package io.leopard.web4j.permission;

public class PermissionKey {
	private String uri;
	private String ip;

	public PermissionKey() {

	}

	public PermissionKey(String uri, String ip) {
		this.uri = uri;
		this.ip = ip;
	}

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

}
