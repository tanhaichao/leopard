package io.leopard.security.admin.menu;

public class Role {

	/**
	 * 超级管理员
	 */
	public static final Role SUPER = new Role("super");
	/**
	 * 普通管理员
	 */
	public static final Role GENERAL = new Role("general");

	public Role() {
	}

	public Role(String role) {
		this.role = role;
	}

	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
