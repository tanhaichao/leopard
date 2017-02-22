package io.leopard.web.xparam.resolver;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
//import org.apache.commons.lang.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * List<?>参数解析.
 * 
 * @author 阿海
 *
 */
@Component
public class ParamListHandlerMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver {

	protected Log logger = LogFactory.getLog(this.getClass());

	// private Set<String> simpleClassSet = new HashSet<String>();
	// public ParamListHandlerMethodArgumentResolver() {
	// simpleClassSet.add(String.class.getName());
	// simpleClassSet.add(Long.class.getName());
	// simpleClassSet.add(Float.class.getName());
	// simpleClassSet.add(Integer.class.getName());
	// simpleClassSet.add(Double.class.getName());
	// simpleClassSet.add(Date.class.getName());
	// simpleClassSet.add(MultipartFile.class.getName());
	// }

	// private Map<Integer, Class<?>> clazzMap = new ConcurrentHashMap<Integer, Class<?>>();

	// private Map<Integer, Class<?>> modelMap = new ConcurrentHashMap<Integer, Class<?>>();

	@Value("${xparam.underline}")
	private String underline;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> type = parameter.getParameterType();
		if (!type.equals(List.class)) {
			return false;
		}
		String name = parameter.getParameterName();
		return name.endsWith("List");
		// if (!support) {
		// return false;
		// }

		// {
		// Type[] args = ((ParameterizedType) parameter.getGenericParameterType()).getActualTypeArguments();
		// Class<?> clazz = (Class<?>) args[0];
		// boolean isModel = isModelClass(clazz);
		// clazzMap.put(parameter.hashCode(), clazz);
		// if (isModel) {
		// modelMap.put(parameter.hashCode(), clazz);
		// System.err.println("name:" + name + " typeName:" + args[0] + " isModel:" + isModel);
		// }
		// }

		// return support;
	}

	// private boolean isModelClass(Class<?> clazz) {
	// return !simpleClassSet.contains(clazz.getName());
	// }

	@Override
	protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request.getNativeRequest();
		String[] values = getParameterValues(req, name);
		// logger.info("name:" + name + " values:" + values);
		if (values == null) {
			return null;
		}
		if (values.length == 1) {
			if (values[0].startsWith("[") && values[0].endsWith("]")) {
				Type[] args = ((ParameterizedType) parameter.getGenericParameterType()).getActualTypeArguments();
				Class<?> clazz = (Class<?>) args[0];
				return UnderlineJson.toListObject(values[0], clazz);
			}
		}
		return values;

		// int hashCode = parameter.hashCode();
		// Class<?> clazz = modelMap.get(hashCode);
		// if (clazz != null) {
		// return toList(clazz, values);
		// }
		//
		// if (values != null && values.length == 1) {
		// // logger.info("values:" + values[0]);
		// if (StringUtils.isEmpty(values[0])) {
		// return null;
		// }
		// // TODO 暂时只支持List<String>
		// if (values[0].startsWith("[") && values[0].endsWith("]")) {
		// return Json.toListObject(values[0], String.class);
		// }
		// }
		// return values;
	}

	public static String[] getParameterValues(HttpServletRequest req, String name) {
		String[] values = RequestBodyParser.getParameterValues(req, name);
		// logger.info("name:" + name + " values:" + values);
		if (values == null) {
			values = RequestBodyParser.getParameterValues(req, name.replaceFirst("List$", ""));
			if (values == null) {
				return null;
			}
		}
		return values;
	}

}
