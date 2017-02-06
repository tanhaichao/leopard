package io.leopard.rpc;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.leopard.core.exception.StatusCodeException;
import io.leopard.httpnb.HttpException;
import io.leopard.httpnb.HttpHeader;
//import io.leopard.burrow.util.DateTime;
//import io.leopard.core.exception.StatusCodeException;
import io.leopard.httpnb.Httpnb;
import io.leopard.httpnb.Param;
import io.leopard.json.Json;
import io.leopard.json.JsonException;
//import io.leopard.commons.utility.HttpUtils;
//import io.leopard.commons.utility.HttpsUtils;

public class RpcClient {
	private static Log logger = LogFactory.getLog(RpcClient.class);

	// public static String doGet(String url, long timeout) {
	// return doPost(url, timeout);
	// }

	public static Object doPostForObject(String url, long timeout) {
		return doPostForObject(url, (Map<String, Object>) null, timeout);
	}

	public static Object doPostForObject(String url, String json, long timeout) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("json", json);
		return doPostForObject(url, params, timeout);
	}

	public static Object doPostForData(String url, Map<String, Object> params, long timeout) {
		Map<String, Object> map = doPostForMap(url, params, timeout);
		return map.get("data");
	}

	public static <T> List<T> doPostForList(String url, Map<String, Object> params, Class<T> clazz, long timeout) {
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) doPostForObject(url, params, timeout);

		List<T> result = new ArrayList<T>();
		for (Map<String, Object> map : list) {
			String json = Json.toJson(map);
			T bean = Json.toObject(json, clazz, true);
			result.add(bean);
		}
		// return Json.toListObject(data, clazz, true);
		return result;
	}

	public static Object doPostForObject(String url, Map<String, Object> params, long timeout) {
		Map<String, Object> map = doPostForMap(url, params, timeout);
		Object obj = map.get("data");
		// System.out.println("obj:" + obj + " type:" + obj.getClass().getName());
		return obj;
	}

	public static String doPost(String url, long timeout, Map<String, Object> map) {
		HttpHeader header = new RpcHttpHeader(timeout);
		Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			header.addParam(new Param(entry.getKey(), entry.getValue()));
		}
		try {
			HttpURLConnection conn = header.openConnection(url);
			return Httpnb.execute(conn, null);
		}
		catch (IOException e) {
			throw new HttpException(e, header);
		}
	}

	@SuppressWarnings("unchecked")
	protected static Map<String, Object> doPostForMap(String url, Map<String, Object> params, long timeout) {
		String json = doPost(url, timeout, params);
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
		if (!status.equals("success")) {
			String message = (String) map.get("message");
			logger.error("json:" + json);
			logger.error("url:" + url + " params:" + params);
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
			return getTime((Date) value);
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

	private static final SimpleDateFormat GET_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * long型毫秒数转成yyyy-MM-dd HH:mm:ss类型的字符串型日期
	 * 
	 * @param millis 毫秒数
	 * @return 日期
	 */
	public static synchronized String getTime(Date date) {
		if (date == null) {
			return null;
		}
		return GET_TIME_FORMAT.format(date);
	}
}
