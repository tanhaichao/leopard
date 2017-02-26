package io.leopard.web.xparam.resolver;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.leopard.json.Json;

/**
 * RequestBody参数解析器
 * 
 * @author 谭海潮
 *
 */
public class RequestBodyParser {

	protected static Log logger = LogFactory.getLog(RequestBodyParser.class);

	// TODO 未支持下划线
	public static String[] getParameterValues(HttpServletRequest request, String name) {
		String[] values = request.getParameterValues(name);
		if (values == null) {
			String value = getParameterForRequestBody(request, name);
			if (value == null) {
				return null;
			}
			return new String[] { value };
		}
		return values;
	}

	// private static String[] getParameterValuesForRequestBody(HttpServletRequest request, String name) {
	// Map<String, Object> requestBody = getRequestBody(request);
	// if (requestBody == null) {
	// return null;
	// }
	// Object value = requestBody.get(name);
	// if (value == null) {
	// return null;
	// }
	// if (value instanceof String) {
	// return (String) value;
	// }
	// else if (value instanceof Integer) {
	// return Integer.toString((Integer) value);
	// }
	// else if (value instanceof Long) {
	// return Long.toString((Long) value);
	// }
	// else if (value instanceof Float) {
	// return Float.toString((Float) value);
	// }
	// else if (value instanceof Double) {
	// return Double.toString((Double) value);
	// }
	// else if (value instanceof Date) {
	// return ((Date) value).getTime() + "";
	// }
	// else if (value instanceof Boolean) {
	// return value + "";
	// }
	// return Json.toJson(value);
	// }

	public static String getParameter(HttpServletRequest request, String name) {
		String value = request.getParameter(name);
		if (value == null) {
			value = getParameterForRequestBody(request, name);
		}
		return value;
	}

	private static Map<String, Object> getRequestBody(HttpServletRequest request) {
		@SuppressWarnings("unchecked")

		Map<String, Object> requestBody = (Map<String, Object>) request.getAttribute("requestBody");
		if (requestBody == null) {
			String requestBodyJson = request.getParameter("requestBody");
			logger.info("uri:" + request.getRequestURI() + " requestBodyJson:" + requestBodyJson);
			if (StringUtils.isEmpty(requestBodyJson)) {
				return null;
			}
			requestBody = Json.toMap(requestBodyJson);
			request.setAttribute("requestBody", requestBody);
		}
		return requestBody;
	}

	private static String getParameterForRequestBody(HttpServletRequest request, String name) {
		Map<String, Object> requestBody = getRequestBody(request);
		if (requestBody == null) {
			return null;
		}
		Object value = requestBody.get(name);

		if (value == null) {
			return null;
		}
		if (value instanceof String) {
			return (String) value;
		}
		else if (value instanceof Integer) {
			return Integer.toString((Integer) value);
		}
		else if (value instanceof Long) {
			return Long.toString((Long) value);
		}
		else if (value instanceof Float) {
			return Float.toString((Float) value);
		}
		else if (value instanceof Double) {
			return Double.toString((Double) value);
		}
		else if (value instanceof Date) {
			return ((Date) value).getTime() + "";
		}
		else if (value instanceof Boolean) {
			return value + "";
		}

		String json = Json.toJson(value);
		System.err.println("getParameterForRequestBody name:" + name + " json:" + json);
		return json;
	}
}
