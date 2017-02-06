package io.leopard.web.passport;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.web.method.HandlerMethod;

public class PassportCheckerHandlerMethodImpl implements PassportChecker {
	protected Log logger = LogFactory.getLog(this.getClass());

	private final Map<String, String> parameterNameMap = new ConcurrentHashMap<String, String>();
	private final Map<String, Boolean> handlerCacheMap = new ConcurrentHashMap<String, Boolean>();
	private final ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

	public PassportCheckerHandlerMethodImpl(String... names) {
		for (String name : names) {
			parameterNameMap.put(name, "");
		}
	}

	public boolean hasPassportParameter(HandlerMethod handlerMethod) {
		MethodParameter[] parameters = handlerMethod.getMethodParameters();
		if (parameters != null) {
			for (MethodParameter parameter : parameters) {
				parameter.initParameterNameDiscovery(parameterNameDiscoverer);
				String parameterName = parameter.getParameterName();
				if (parameterNameMap.containsKey(parameterName)) {
					// logger.info("hasPassportParameter parameterName:" + parameterName);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Boolean isNeedCheckLogin(HttpServletRequest request, Object handler) {
		if (!(handler instanceof HandlerMethod)) {
			return null;
		}
		String key = handler.toString();
		Boolean contain = handlerCacheMap.get(key);
		if (contain != null) {
			if (contain) {
				return true;
			}
			return null;
		}
		boolean hasPassportParameter = this.hasPassportParameter((HandlerMethod) handler);
		// logger.info("isNeedCheckLogin hasPassportParameter:" + hasPassportParameter);
		handlerCacheMap.put(key, hasPassportParameter);
		if (hasPassportParameter) {
			return true;
		}
		return null;
	}

}
