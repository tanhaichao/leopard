package io.leopard.im.huanxin.model;

import java.util.Date;

public class AccessTokenResponseObject {

	private String accessToken;

	private Date expiresIn;

	private String application;

	public String getApplication() {
		return this.application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Date getExpiresIn() {
		return this.expiresIn;
	}

	public void setExpiresIn(Date expiresIn) {
		this.expiresIn = expiresIn;
	}
}
