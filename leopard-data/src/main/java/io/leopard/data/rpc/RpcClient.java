package io.leopard.data.rpc;

import io.leopard.burrow.util.DateTime;
import io.leopard.commons.utility.ClassUtil;
import io.leopard.core.exception.StatusCodeException;
import io.leopard.httpnb.Httpnb;
import io.leopard.json.Json;
import io.leopard.json.JsonException;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import io.leopard.commons.utility.HttpUtils;
//import io.leopard.commons.utility.HttpsUtils;

public class RpcClient {
	private static Log logger = LogFactory.getLog(RpcClient.class);

	public static Object doGet(String url, long timeout) {
		return doPost(url, timeout);
	}

	public static Object doPost(String url, long timeout) {
		return RpcClient.doPost(url, (Map<String, Object>) null, timeout);
	}

	public static Object doPost(String url, String json, long timeout) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("json", json);
		return doPost(url, params, timeout);
	}

	public static <T> List<T> doPostForList(String url, Map<String, Object> params, Class<T> clazz, long timeout) {
		Map<String, Object> map = doPostForMap(url, params, timeout);
		String data = (String) map.get("data");
		return Json.toListObject(data, clazz);
	}

	public static Object doPost(String url, Map<String, Object> params, long timeout) {
		Map<String, Object> map = doPostForMap(url, params, timeout);

		String className = (String) map.get("clazz");

		// AssertUtil.assertNotEmpty(className, "怎么clazz为空?");
		if (className == null || className.length() == 0) {
			logger.error("url:" + url);
			// logger.error("json:" + json);
			throw new IllegalArgumentException("怎么clazz为空?");
		}
		Class<?> clazz = ClassUtil.forName(className);
		String data = (String) map.get("data");
		Object result = Json.toObject(data, clazz);
		return result;
	}

	@SuppressWarnings("unchecked")
	protected static Map<String, Object> doPostForMap(String url, Map<String, Object> params, long timeout) {
		String json = Httpnb.doPost(url, timeout, params);
		if (StringUtils.isEmpty(json)) {
			throw new RuntimeException("调用远程接口出错，没有返回json.");
		}
		// System.out.println("json:" + json);
		Map<String, Object> map;
		try {
			map = Json.toObject(json, Map.class);
			// map = Json.toMap(json);
		}
		catch (JsonException e) {
			System.err.println("接口返回错误的json数据:" + e.getMessage());
			logger.error("接口返回错误的json数据:" + e.getMessage() + " json:" + json);
			throw e;
		}
		String status = (String) map.get("status");
		if (status == null) {
			throw new RuntimeException("调用远程接口出错，没有返回状态码.");
		}
		if (!status.equals("200")) {
			String message = (String) map.get("message");
			// logger.error("json:"+json);
			throw new StatusCodeException(status, "调用远程接口出错.[" + status + "." + message + "]", message);
			// throw new RuntimeException("调用远程接口出错.[" + message + "]");
		}

		return map;
	}

	public static Map<String, String> toParams(Object bean) {
		Field[] fields = bean.getClass().getDeclaredFields();
		Map<String, String> params = new LinkedHashMap<String, String>();
		for (Field field : fields) {
			String fieldName = field.getName();
			if (fieldName.indexOf("$") > -1) {
				continue;
			}
			String value;
			try {
				value = getStringValue(bean, field);
			}
			catch (Exception e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			if (value == null) {
				continue;
			}
			params.put(fieldName, value);
			// System.out.println("field:" + fieldName + " value:" + value);
		}
		return params;
	}

	protected static String getStringValue(Object bean, Field field) throws IllegalArgumentException, IllegalAccessException {
		field.setAccessible(true);
		Object value = field.get(bean);
		if (value == null) {
			return null;
		}
		return getStringValue(value);
	}

	protected static String getStringValue(Object value) {
		if (value instanceof String) {
			return (String) value;
		}
		else if (value instanceof Date) {
			return DateTime.getTime((Date) value);
		}
		else if (value instanceof Boolean) {
			return Boolean.toString((Boolean) value);
		}
		else if (value instanceof Integer) {
			return Integer.toString((Integer) value);
		}
		else if (value instanceof Long) {
			return Long.toString((Long) value);
		}
		// else if (value instanceof List) {
		// @SuppressWarnings("rawtypes")
		// List list = (List) value;
		// return StringUtils.join(list, ",");
		// }
		else {
			throw new IllegalArgumentException("未知数据类型[" + value.getClass().getName() + "].");
		}
	}
}
