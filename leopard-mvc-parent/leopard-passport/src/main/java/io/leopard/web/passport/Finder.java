package io.leopard.web.passport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.StringUtils;

import io.leopard.burrow.lang.AssertUtil;

public class Finder {
	protected Log logger = LogFactory.getLog(this.getClass());

	private static PassportCheckerImpl passportChecker = new PassportCheckerImpl();

	private static Finder instance = new Finder();

	public static Finder getInstance() {
		return instance;
	}

	private Finder() {
	}

	// private List<PassportValidator> list = Collections.synchronizedList(new ArrayList<PassportValidator>());
	private List<PassportValidator> list = new ArrayList<PassportValidator>();

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		DefaultListableBeanFactory factory = (DefaultListableBeanFactory) beanFactory;
		Map<String, PassportValidator> map = factory.getBeansOfType(PassportValidator.class);
		for (Entry<String, PassportValidator> entry : map.entrySet()) {
			list.add(new DefaultPassportValidatorWrapper(entry.getValue()));
		}
		// System.err.println("list:" + list);
		passportChecker.setBeanFactory(beanFactory);
	}

	public static class DefaultPassportValidatorWrapper extends PassportValidatorWrapper {
		public DefaultPassportValidatorWrapper(PassportValidator validator) {
			super(validator);
		}

		@Override
		public Boolean isNeedCheckLogin(HttpServletRequest request, Object handler) {
			Boolean isNeedCheckLogin = super.isNeedCheckLogin(request, handler);
			// logger.info("custom:" + validator + " isNeedCheckLogin:" + isNeedCheckLogin + " request:" + request.getRequestURI());
			if (isNeedCheckLogin == null && "sessUid".equals(this.sessionKey)) {
				isNeedCheckLogin = passportChecker.isNeedCheckLogin(request, handler);
				// logger.info("default:" + validator + " isNeedCheckLogin:" + isNeedCheckLogin + " request:" + request.getRequestURI());
			}
			return isNeedCheckLogin;
		}
	}

	public PassportValidator find(String type) {
		AssertUtil.assertNotEmpty(type, "登录类型不能为空.");
		for (PassportValidator validator : list) {
			if (type.equals(getSessionKey(validator))) {
				// System.err.println("find:" + validator + " sessionKey:" + getSessionKey(validator));
				return validator;
			}
		}
		throw new IllegalArgumentException("找不到通行证验证器[" + type + "]的实现.");
	}

	public static String getSessionKey(PassportValidator validator) {
		if (validator instanceof PassportValidatorWrapper) {
			validator = ((PassportValidatorWrapper) validator).getValidator();
		}

		Class<?> clazz = validator.getClass();

		while (true) {
			String name = clazz.getName();
			if (name.indexOf("$$") == -1) {
				break;
			}
			clazz = clazz.getSuperclass();
			if (clazz == null || clazz.equals(Object.class)) {
				break;
			}
		}

		PassportGroup anno = clazz.getAnnotation(PassportGroup.class);
		if (anno == null || StringUtils.isEmpty(anno.value())) {
			return "sessUid";
		}
		return anno.value();
	}

	public List<PassportValidator> find(HttpServletRequest request, Object handler) {
		List<PassportValidator> list = new ArrayList<PassportValidator>();
		for (PassportValidator validator : this.list) {
			Boolean isNeedCheckLogin = validator.isNeedCheckLogin(request, handler);
			// logger.info("validator:" + validator + " isNeedCheckLogin:" + isNeedCheckLogin + " request:" + request.getRequestURI());

			if (isNeedCheckLogin != null && isNeedCheckLogin) {
				// System.err.println("validator:" + Finder.getSessionKey(validator) + " isNeedCheckLogin:" + isNeedCheckLogin + " request:" + request.getRequestURI());
				list.add(validator);
			}
		}

		return list;
	}
}
