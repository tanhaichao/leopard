package io.leopard.security.admin;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AdminDao {

	/**
	 * 获取帐号.
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	Long getUid(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 转发到登录页面.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	void forwardLoginUrl(HttpServletRequest request, HttpServletResponse response) throws IOException;

	/**
	 * 登录.
	 * 
	 * @param username
	 * @param request
	 */
	void login(long uid, HttpServletRequest request);

	Admin get(long uid);

}
