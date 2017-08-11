package io.leopard.im.qcloud;

/**
 * 腾讯云通讯
 * 
 * @author 谭海潮
 *
 */
public interface QcloudImClient {
	String addUser(String username, String password, String nickname);

	String getGroupList();

}
