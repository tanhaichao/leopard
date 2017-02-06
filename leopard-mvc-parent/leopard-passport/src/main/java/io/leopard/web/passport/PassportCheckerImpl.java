package io.leopard.web.passport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

public class PassportCheckerImpl implements PassportChecker {

	protected Log logger = LogFactory.getLog(this.getClass());

	// protected UriListChecker uriListChecker = new UriListChecker();// 需要做登录验证的URL列表

	// private PassportChecker passportCheckerHandlerMethodImpl = new PassportCheckerHandlerMethodImpl();

	private static List<PassportChecker> passportCheckerList = Collections.synchronizedList(new ArrayList<PassportChecker>());

	static {
		passportCheckerList.add(new PassportCheckerHandlerMethodImpl("sessUid", "sessUsername"));
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		DefaultListableBeanFactory factory = (DefaultListableBeanFactory) beanFactory;
		Map<String, PassportChecker> map = factory.getBeansOfType(PassportChecker.class);
		for (Entry<String, PassportChecker> entry : map.entrySet()) {
			// System.err.println("PassportCheckerImpl entry:" + entry.getValue());
			passportCheckerList.add(entry.getValue());
		}
	}

	@Override
	public Boolean isNeedCheckLogin(HttpServletRequest request, Object handler) {
		for (PassportChecker checker : passportCheckerList) {
			Boolean isNeedCheckLogin = checker.isNeedCheckLogin(request, handler);
			// logger.info("PassportCheckerImpl checker:" + checker + " isNeedCheckLogin:" + isNeedCheckLogin);
			if (isNeedCheckLogin != null) {
				return isNeedCheckLogin;
			}
		}
		return null;
	}
}
