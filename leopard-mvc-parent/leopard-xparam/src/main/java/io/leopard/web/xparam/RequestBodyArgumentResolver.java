package io.leopard.web.xparam;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.leopard.json.Json;

/**
 * RequestBody参数解析
 * 
 * @author 谭海潮
 *
 */
public class RequestBodyArgumentResolver {

	protected static Log logger = LogFactory.getLog(RequestBodyArgumentResolver.class);

	public static String getParameter(HttpServletRequest request, String name) {
		String value = request.getParameter(name);
		if (value == null) {
			value = getParameterForRequestBody(request, name);
		}
		return value;
	}

	public static String getParameterForRequestBody(HttpServletRequest request, String name) {
		@SuppressWarnings("unchecked")
		Map<String, Object> requestBody = (Map<String, Object>) request.getAttribute("requestBody");
		if (requestBody == null) {
			String requestBodyJson = request.getParameter("requestBody");
			logger.info("requestBodyJson:" + requestBodyJson);
			if (StringUtils.isEmpty(requestBodyJson)) {
				return null;
			}
			requestBody = Json.toMap(requestBodyJson);
			request.setAttribute("requestBody", requestBody);
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
		return Json.toJson(value);
	}
}
