package io.leopard.security.admin.version2;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.leopard.burrow.lang.LeopardCheckUtil;
import io.leopard.core.exception.forbidden.CaptchaWrongException;
import io.leopard.core.exception.forbidden.PasswordWrongException;
import io.leopard.core.exception.invalid.PasswordInvalidException;
import io.leopard.core.exception.notfound.UserNotFoundException;
import io.leopard.data.kit.password.PasswordVerifier;
import io.leopard.data.kit.password.PasswordVerifierImpl;
import io.leopard.jdbc.Jdbc;
import io.leopard.web.servlet.CookieBuilder;

/**
 * 登录.
 * 
 * @order 1
 * @author 谭海潮
 *
 */
@Controller
@RequestMapping("/admin/")
public class LoginController {

	@Resource
	private AdminBiz adminBiz;

	private PasswordVerifier passwordVerifier = new PasswordVerifierImpl();

	@Resource
	private Jdbc jdbc;

	public static void isPassword(String password) {
		if (StringUtils.isEmpty(password)) {
			throw new PasswordInvalidException("密码不能为空.");
		}
		int length = password.getBytes().length;
		if (length < 6) {
			throw new PasswordInvalidException("密码不能少于6位[" + length + "].");
		}
	}

	/**
	 * 登录.
	 * 
	 * @param mobile 手机号
	 * @param password
	 * @param remember 记住登录状态
	 * @return
	 * @throws UserNotFoundException
	 * @throws CaptchaWrongException
	 * @throws AdminNotFoundException
	 * @axure 1_2
	 */
	public boolean login(String username, String password, boolean remember, HttpServletRequest request, HttpServletResponse response) throws PasswordWrongException, AdminNotFoundException {
		LeopardCheckUtil.isUsername(username);
		isPassword(password);

		AdminVO admin = this.adminBiz.getByUsername(username);
		if (admin == null) {
			AdminNotFoundException e = new AdminNotFoundException(0);
			e.setMessage("管理员[" + username + "]不存在.");
			throw e;
		}
		String salt = admin.getSalt();
		String dbEncryptedPassword = admin.getPassword();
		passwordVerifier.check(username, password, salt, dbEncryptedPassword);
		String token = passwordVerifier.makeToken(dbEncryptedPassword);

		int maxAge = -1;// 60 * 60 * 24 * 7;

		boolean isTopdomainCookie = adminBiz.isTopdomainCookie();

		if (isTopdomainCookie) {
			new CookieBuilder("adminId", admin.getAdminId(), response).setMaxAge(maxAge, remember).setTopLevelDomain(request).build();
			new CookieBuilder("a_token", token, response).setMaxAge(maxAge, remember).setTopLevelDomain(request).build();
		}
		else {
			new CookieBuilder("adminId", admin.getAdminId(), response).setMaxAge(maxAge, remember).build();
			new CookieBuilder("a_token", token, response).setMaxAge(maxAge, remember).build();
		}

		return true;
	}

	/**
	 * 退出登录
	 * 
	 * @return
	 */
	@RequestMapping
	@ResponseBody
	public boolean logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("sessAdminId");
		new CookieBuilder("a_token", "", response).setTopLevelDomain(request).build();
		return true;
	}
}
