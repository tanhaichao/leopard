package io.leopard.data.signature;

import java.util.Date;

/**
 * 签名信息.
 * 
 * @author 阿海
 * 
 */
public class SignatureInfo {

	private String user;
	private Date posttime;
	private String sha1;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getPosttime() {
		return posttime;
	}

	public void setPosttime(Date posttime) {
		this.posttime = posttime;
	}

	public String getSha1() {
		return sha1;
	}

	public void setSha1(String sha1) {
		this.sha1 = sha1;
	}

}
