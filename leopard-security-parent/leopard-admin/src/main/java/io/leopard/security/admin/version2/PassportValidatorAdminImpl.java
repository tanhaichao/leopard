package io.leopard.security.admin.version2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.leopard.burrow.lang.LeopardValidUtil;
import io.leopard.data.kit.password.PassportTokenUtil;
import io.leopard.web.passport.ParameterNameResolver;
import io.leopard.web.passport.PassportGroup;
import io.leopard.web.passport.PassportValidator;
import io.leopard.web.servlet.CookieUtil;

/**
 * 运营后台管理员登录验证.
 * 
 * @author 谭海潮
 *
 */
@Component
@PassportGroup("sessAdminId")
public class PassportValidatorAdminImpl implements PassportValidator {

	protected Log logger = LogFactory.getLog(PassportValidatorAdminImpl.class);

	@Autowired
	private AdminBiz adminBiz;

	@Autowired
	private LoginController loginController;

	@Override
	public Object validate(HttpServletRequest request, HttpServletResponse response) {
		long uid = NumberUtils.toLong(CookieUtil.getCookie("adminId", request));
		String token = CookieUtil.getCookie("a_token", request);

		if (!LeopardValidUtil.isValidUid(uid)) {
			return null;
		}
		String encryptedPassword = PassportTokenUtil.getEncryptedPassword(token);
		if (StringUtils.isEmpty(encryptedPassword)) {
			logger.info("validate encryptedPassword:" + encryptedPassword);
			return null;
		}
		try {
			adminBiz.login(uid, encryptedPassword);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		return uid;
	}

	@Override
	public boolean login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String mobile = request.getParameter("username");
		String password = request.getParameter("password");
		String url = request.getParameter("url");
		boolean remember = "true".equals(request.getParameter("remember"));

		loginController.login(mobile, password, remember, request, response);
		response.sendRedirect(url);
		return true;
	}

	@Override
	public boolean showLoginBox(HttpServletRequest request, HttpServletResponse response) {

		return false;
	}

	@Override
	public Boolean isNeedCheckLogin(HttpServletRequest request, Object handler) {
		String uri = request.getRequestURI();
		if (uri.startsWith("/admin/")) {
			// return true;
		}
		return ParameterNameResolver.hasParameterName(handler, "sessAdminId");
	}

}
