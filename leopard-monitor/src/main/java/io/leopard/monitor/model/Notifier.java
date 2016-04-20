package io.leopard.monitor.model;

/**
 * 报警消息接收人.
 * 
 * @author 阿海
 * 
 */
public class Notifier {
	private String name;
	private String mobile;
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		// System.err.println("setMobile:" + mobile);
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
