package io.leopard.data.kit.token;

import java.util.Date;

public class Token {

	/**
	 * token ID
	 */
	private String tokenId;
	/**
	 * 账号
	 */
	private String account;
	/**
	 * 类别.
	 */
	private String category;

	/**
	 * 在哪里使用.
	 */
	private String target;

	/**
	 * Token值
	 */
	private String token;
	/**
	 * 是否已使用
	 */
	private boolean used;
	/**
	 * 记录创建时间
	 */
	private Date posttime;
	/**
	 * 失效时间
	 */
	private Date expiryTime;
	/**
	 * 最后修改时间
	 */
	private Date lmodify;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public Date getPosttime() {
		return posttime;
	}

	public void setPosttime(Date posttime) {
		this.posttime = posttime;
	}

	public Date getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}

	public Date getLmodify() {
		return lmodify;
	}

	public void setLmodify(Date lmodify) {
		this.lmodify = lmodify;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
