package io.leopard.im.huanxin.model;

import java.util.Date;

/**
 * 用户信息
 * 
 * @author 谭海潮
 *
 */
public class UserResponseObject {

	private String uuid;

	private Date created;

	private Date modified;

	private String username;

	private boolean activated;

	private String nickname;

	private int notificationDisplayStyle;

	private boolean notificationNoDisturbing;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getNotificationDisplayStyle() {
		return notificationDisplayStyle;
	}

	public void setNotificationDisplayStyle(int notificationDisplayStyle) {
		this.notificationDisplayStyle = notificationDisplayStyle;
	}

	public boolean isNotificationNoDisturbing() {
		return notificationNoDisturbing;
	}

	public void setNotificationNoDisturbing(boolean notificationNoDisturbing) {
		this.notificationNoDisturbing = notificationNoDisturbing;
	}

}
