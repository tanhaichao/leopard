package io.leopard.web4j.permission;

import java.util.Date;

/**
 * 权限配置.
 * 
 * @author 阿海
 * 
 */
public class Permission extends PermissionKey {

	private String username;// 操作人账号
	private String content;
	private Date posttime;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPosttime() {
		return posttime;
	}

	public void setPosttime(Date posttime) {
		this.posttime = posttime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
