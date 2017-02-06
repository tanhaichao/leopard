package io.leopard.security.admin.version2;

import io.leopard.core.exception.forbidden.PasswordWrongException;
import io.leopard.core.exception.other.AdminDisabledException;
import io.leopard.data.kit.password.LoginInfo;

/**
 * 管理员
 * 
 * @author 谭海潮
 *
 */
public interface AdminBiz {

	boolean login(long adminId, String password) throws AdminNotFoundException, PasswordWrongException, AdminDisabledException;

	LoginInfo login(String username, String password) throws AdminNotFoundException, PasswordWrongException;

	boolean add(String username, String password, String name);

	String getName(long adminId) throws AdminNotFoundException;

	////
	AdminVO getByUsername(String username);

	AdminVO get(long adminId);

	/**
	 * 是否向顶级域名写cookie.
	 * 
	 * @return
	 */
	boolean isTopdomainCookie();

	/**
	 * 添加角色
	 * 
	 * @param username
	 * @param role
	 * @return
	 * @throws AdminNotFoundException 
	 */
	boolean addRole(String username, String role) throws AdminNotFoundException;

	/**
	 * 删除角色
	 * 
	 * @param username
	 * @param role
	 * @return
	 * @throws AdminNotFoundException 
	 */
	boolean deleteRole(String username, String role) throws AdminNotFoundException;

	boolean updatePassword(long adminId, String oldPassword, String newPassword, String confirmPassword) throws PasswordWrongException, AdminDisabledException, AdminNotFoundException;

	boolean disable(long adminId);

	boolean enable(long adminId);

}
