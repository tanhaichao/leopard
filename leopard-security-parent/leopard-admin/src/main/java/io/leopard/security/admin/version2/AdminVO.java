package io.leopard.security.admin.version2;

import java.util.List;

public class AdminVO {
	/**
	 * 管理员ID
	 */
	private long adminId;
	/**
	 * 角色
	 */
	private List<String> roleList;
	/**
	 * 管理员姓名
	 */
	private String name;

	/**
	 * 密码
	 */
	private String password;
	/**
	 * 随机码
	 */
	private String salt;

	/**
	 * 是否已禁用
	 */
	private boolean disabled;

	/**
	 * 账号
	 */
	private String username;

	public long getAdminId() {
		return adminId;
	}

	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}

	public List<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
