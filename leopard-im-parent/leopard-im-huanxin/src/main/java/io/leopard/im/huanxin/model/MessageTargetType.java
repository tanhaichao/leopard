package io.leopard.im.huanxin.model;

import io.leopard.lang.inum.Snum;

/**
 * 消息目标类型
 * 
 * @author 谭海潮
 *
 */
public enum MessageTargetType implements Snum {
	USERS("users", "给用户发消息")//
	, CHATGROUPS("chatgroups", "给群发消息")//
	, CHATROOMS("chatrooms", "给聊天室发消息");
	// users 给用户发消息。chatgroups: 给群发消息，chatrooms: 给聊天室发消息

	private String key;

	private String desc;

	private MessageTargetType(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getDesc() {
		return desc;
	}
}
