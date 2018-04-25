package io.leopard.web.passport;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.web.method.HandlerMethod;

/**
 * Controller方法的参数名称解析器
 * 
 * @author 谭海潮
 *
 */
public class ParameterNameResolver {
	/**
	 * 参数名称解析器
	 */
	private static final ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

	/**
	 * 方法参数名称缓存数据
	 */
	private static final Map<Integer, Set<String>> handlerMethodParameterNameCache = new ConcurrentHashMap<>();

	/**
	 * 判断是否有某个参数名称
	 * 
	 * @param handlerMethod
	 * @param parameterName
	 * @return
	 */
	public static boolean hasParameterName(Object handler, String parameterName) {
		if (!(handler instanceof HandlerMethod)) {
			return false;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Set<String> parameterNameSet = getParameterNameList(handlerMethod);
		return parameterNameSet.contains(parameterName);
	}

	/**
	 * 获取参数名称列表
	 * 
	 * @param handlerMethod
	 * @return
	 */
	public static Set<String> getParameterNameList(HandlerMethod handlerMethod) {
		int key = handlerMethod.hashCode();
		Set<String> parameterNameSet = handlerMethodParameterNameCache.get(key);
		if (parameterNameSet == null) {
			parameterNameSet = loadParameterNameList(key, handlerMethod);
		}
		return parameterNameSet;
	}

	private static synchronized Set<String> loadParameterNameList(int key, HandlerMethod handlerMethod) {
		Set<String> parameterNameSet = handlerMethodParameterNameCache.get(key);
		if (parameterNameSet != null) {
			return parameterNameSet;
		}
		parameterNameSet = resolveParameterNameList(handlerMethod);
		handlerMethodParameterNameCache.put(key, parameterNameSet);
		return parameterNameSet;
	}

	/**
	 * 解析参数名称列表
	 * 
	 * @param handlerMethod
	 * @return
	 */
	public static Set<String> resolveParameterNameList(HandlerMethod handlerMethod) {
		MethodParameter[] parameters = handlerMethod.getMethodParameters();
		Set<String> parameterNameSet = new LinkedHashSet<>();
		if (parameters != null) {
			for (MethodParameter parameter : parameters) {
				parameter.initParameterNameDiscovery(parameterNameDiscoverer);
				String parameterName = parameter.getParameterName();
				parameterNameSet.add(parameterName);
			}
		}
		return parameterNameSet;
	}
}
