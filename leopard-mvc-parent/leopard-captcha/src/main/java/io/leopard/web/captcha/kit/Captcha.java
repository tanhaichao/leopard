package io.leopard.web.captcha.kit;

import java.util.Date;

public class Captcha {

	private String captchaId;

	private String account;

	/**
	 * 类别:图片、文字
	 */
	private String category;

	/**
	 * 类型:手机、邮件
	 */
	private String type;

	/**
	 * 在哪里使用
	 */
	private String target;

	private String captcha;
	private Date posttime;
	private Date expiryTime;
	private Date lmodify;

	private boolean used;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCaptchaId() {
		return captchaId;
	}

	public void setCaptchaId(String captchaId) {
		this.captchaId = captchaId;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public Date getPosttime() {
		return posttime;
	}

	public void setPosttime(Date posttime) {
		this.posttime = posttime;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
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

}
