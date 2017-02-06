package io.leopard.web.passport;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import io.leopard.web.servlet.RegisterHandlerInterceptor;
import io.leopard.web.xparam.Nologin;

/**
 * 检查是否已登录.
 * 
 * @author 阿海
 * 
 */
@Component
// TODO ahai 在site项目必须实现BeanPostProcessor接口才能成功配置拦截器.
@Order(9)
public class PassportInterceptor extends RegisterHandlerInterceptor {

	protected Log logger = LogFactory.getLog(this.getClass());

	private Finder finder = Finder.getInstance();

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		super.setBeanFactory(beanFactory);
		// System.err.println("setBeanFactory setBeanFactory:" + beanFactory);
		finder.setBeanFactory(beanFactory);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		List<PassportValidator> list = finder.find(request, handler);
		for (PassportValidator validator : list) {
			Object account = validator.validate(request, response);
			// logger.info("validator:" + validator + " handler:" + handler + " account:" + account);
			if (account == null) {
				HandlerMethod method = (HandlerMethod) handler;
				Nologin nologin = method.getMethodAnnotation(Nologin.class);
				if (nologin == null) {
					validator.showLoginBox(request, response);
					return false;
				}
			}
		}
		return true;
	}

}
