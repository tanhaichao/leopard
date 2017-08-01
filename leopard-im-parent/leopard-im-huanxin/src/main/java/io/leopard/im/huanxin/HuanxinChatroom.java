package io.leopard.im.huanxin;

/**
 * 环信聊天室
 * 
 * @author 谭海潮
 *
 */
public interface HuanxinChatroom {

	/**
	 * 创建聊天室
	 * 
	 * @param name 名称
	 * @param description 描述
	 * @param maxusers 聊天室成员最大数
	 * @param owner 聊天室的管理员
	 * @return 聊天室ID
	 */
	String create(String name, String description, int maxusers, String owner);

}
