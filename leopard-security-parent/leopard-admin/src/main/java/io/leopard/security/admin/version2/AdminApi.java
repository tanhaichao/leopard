package io.leopard.security.admin.version2;

/**
 * 管理员扩展接口.
 * 
 * @author 谭海潮
 *
 */
public interface AdminApi {

	AdminVO getByUsername(String username);

	AdminVO get(long adminId);

	/**
	 * 是否向顶级域名写cookie.
	 * 
	 * @return
	 */
	boolean isTopdomainCookie();
}
