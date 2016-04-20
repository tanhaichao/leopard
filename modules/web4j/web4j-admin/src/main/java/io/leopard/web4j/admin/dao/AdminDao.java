package io.leopard.web4j.admin.dao;

import io.leopard.web4j.admin.Admin;

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
	String getUsername(HttpServletRequest request, HttpServletResponse response);

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
	void login(String username, HttpServletRequest request);

	Admin get(String username);

}
