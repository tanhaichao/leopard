package io.leopard.im.huanxin;

import java.util.List;

import io.leopard.im.huanxin.model.MessageTargetType;
import io.leopard.im.huanxin.model.UserResponseObject;

/**
 * 环信
 * 
 * @author 谭海潮
 *
 */
public interface HuanxinClient {

	String getToken();

	UserResponseObject addUser(String username, String password, String nickname);

	UserResponseObject getUser(String username);

	/**
	 * 发送消息
	 * 
	 * @param targetList 账号列表
	 * @param type 目标类型
	 * @param from 默认为admin
	 * @param msg 消息内容
	 * @return
	 */
	boolean sendMessage(List<String> targetList, MessageTargetType type, String from, Object msg);

	/**
	 * 强制用户下线
	 * 
	 * @param username
	 * @return
	 */
	boolean disconnect(String username);
}
