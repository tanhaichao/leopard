package io.leopard.web.passport;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import io.leopard.web.servlet.RegisterHandlerInterceptor;

/**
 * 检查是否已登录.
 * 
 * @author 阿海
 * 
 */
@Component
// TODO ahai 在site项目必须实现BeanPostProcessor接口才能成功配置拦截器.
@Order(9) // 数字小优先
public class PassportInterceptor extends RegisterHandlerInterceptor {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private PassportValidatorFinder passportValidatorFinder;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// logger.info("preHandle:" + request.getRequestURI());
		List<PassportValidator> list = passportValidatorFinder.find(request, handler);
		for (PassportValidator validator : list) {
			Object account = PassportUtil.validateAndStore(validator, request, response);
			// logger.info("validator:" + validator + " handler:" + handler + " account:" + account);
			if (account == null) {
				boolean isNeedCheckLogin = PassportUtil.isNeedCheckLogin(validator, request, handler);
				if (isNeedCheckLogin) {
					PassportUtil.showLoginBox(validator, request, response);
					return false;
				}
			}
		}
		return true;
	}

}
