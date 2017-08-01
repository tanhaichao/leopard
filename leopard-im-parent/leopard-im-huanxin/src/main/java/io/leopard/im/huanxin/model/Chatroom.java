package io.leopard.im.huanxin.model;

/**
 * 聊天室
 * 
 * @author 谭海潮
 *
 */
public class Chatroom {
	/**
	 * 聊天室名称
	 */
	private String name;

	/**
	 * 聊天室描述
	 */
	private String description;

	/**
	 * 聊天室成员最大数（包括聊天室创建者），值为数值类型，默认值200，最大值5000
	 */
	private int maxusers;

	/**
	 * 聊天室的管理员
	 */
	private String owner;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMaxusers() {
		return this.maxusers;
	}

	public void setMaxusers(int maxusers) {
		this.maxusers = maxusers;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
}
