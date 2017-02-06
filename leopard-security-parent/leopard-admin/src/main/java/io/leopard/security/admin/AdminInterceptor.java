package io.leopard.security.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.Order;

import io.leopard.web.servlet.RegisterHandlerInterceptor;
import io.leopard.web.servlet.RequestUtil;

@Order(99)
public class AdminInterceptor extends RegisterHandlerInterceptor {
	protected Log logger = LogFactory.getLog(this.getClass());

	private static boolean checkAllowIp = true;

	@Resource
	private AdminDao adminDao;

	// public AdminInterceptor() {
	// PassportCheckerImpl.addPassportChecker(new PassportCheckerAdminImpl());
	// }

	/**
	 * 判断所在IP能否登录后台系统.
	 * 
	 * @param request
	 */
	protected void checkIp(HttpServletRequest request) {
		// FIXME ahai 代码被注释掉了
		// String ip = RequestUtil.getProxyIp(request);
		// // System.out.println("ip:" + ip);
		// if (!loginHandler.isAllowAdminIp(ip)) {
		// throw new AdminIpForbiddenRuntimeException(ip);
		// }
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// logger.info("preHandle:" + request.getRequestURI());
		// admin过滤器
		if (!PassportCheckerAdminImpl.isAdminFolder(request)) {
			return true;
		}

		// 检查用户所在IP
		if (checkAllowIp) {
			checkIp(request);
		}

		Long sessUid = adminDao.getUid(request, response);
		if (sessUid == null || sessUid <= 0) {
			String ip = RequestUtil.getProxyIp(request);
			String message = "您[" + ip + "]未登录,uri:" + request.getRequestURI() + " sessUid:" + sessUid;
			logger.warn(message);
			adminDao.forwardLoginUrl(request, response);
			return false;
		}
		this.adminDao.login(sessUid, request);
		return true;
	}

}
