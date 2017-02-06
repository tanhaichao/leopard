package io.leopard.security.admin.version2;

import java.util.Date;
import java.util.List;

/**
 * 管理员信息
 * 
 * @author 谭海潮
 *
 */
public class Admin {

	/**
	 * 管理员ID
	 */
	private long adminId;

	/**
	 * 账号
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 角色
	 */
	private List<String> roleList;
	/**
	 * 随机码
	 */
	private String salt;
	/**
	 * 管理员姓名
	 */
	private String name;

	/**
	 * 是否已禁用
	 */
	private boolean disabled;
	/**
	 * 创建时间
	 */
	private Date posttime;

	public long getAdminId() {
		return adminId;
	}

	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public Date getPosttime() {
		return posttime;
	}

	public void setPosttime(Date posttime) {
		this.posttime = posttime;
	}

}
