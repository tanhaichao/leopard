package io.leopard.web4j.passport;

import io.leopard.burrow.LeopardLei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通行证(用户)登录验证接口
 * 
 * @author 阿海
 *
 */
public interface PassportValidateLei extends LeopardLei {

	/**
	 * 获取当前登录的用户信息(Leopard会自动将返回值存入session作缓存).
	 * 
	 * @return 通行证或uid
	 */
	PassportUser validate(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 显示登陆框.
	 * 
	 * @param request
	 * @param response
	 */
	void showLoginBox(HttpServletRequest request, HttpServletResponse response);

}
