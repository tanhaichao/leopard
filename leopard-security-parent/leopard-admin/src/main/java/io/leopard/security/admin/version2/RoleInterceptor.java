package io.leopard.security.admin.version2;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import io.leopard.burrow.util.ListUtil;
import io.leopard.core.exception.other.NotLoginException;
import io.leopard.security.admin.annotion.Role;
import io.leopard.security.admin.menu.RoleForbiddenException;
import io.leopard.web.servlet.RegisterHandlerInterceptor;

/**
 * Role注解检查.
 * 
 * @author 阿海
 * 
 */
@Component
@Order(9)
public class RoleInterceptor extends RegisterHandlerInterceptor {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private AdminBiz adminBiz;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// logger.info("preHandle uri:" + request.getRequestURI() + " handler:" + handler.getClass());
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		// TODO ahai 这里要加上缓存?
		HandlerMethod method = (HandlerMethod) handler;
		Role role = method.getMethodAnnotation(Role.class);
		if (role == null) {
			return true;
		}
		this.check(role, request);
		return true;
	}

	protected void check(Role role, HttpServletRequest request) throws AdminNotFoundException, RoleForbiddenException {
		String[] roles = role.value();
		if (roles.length == 0) {
			return;
		}
		Number number = (Number) request.getSession().getAttribute("sessAdminId");
		if (number == null) {
			throw new NotLoginException("您未登录后台.");
		}
		long sessAdminId = number.longValue();
		AdminVO admin = adminBiz.get(sessAdminId);
		if (admin == null) {
			throw new AdminNotFoundException(sessAdminId);
		}
		List<String> roleList = admin.getRoleList();
		if (ListUtil.isEmpty(roleList)) {
			throw new RoleForbiddenException("您[" + sessAdminId + "]什么角色也没有，不能访问该接口.");
		}
		for (String element : roles) {
			if (roleList.contains(element)) {
				return;
			}
		}
		throw new RoleForbiddenException("您[" + sessAdminId + "]没有权限访问该接口.");
	}

}
