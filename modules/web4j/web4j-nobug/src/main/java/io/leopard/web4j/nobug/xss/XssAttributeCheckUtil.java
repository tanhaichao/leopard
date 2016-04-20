package io.leopard.web4j.nobug.xss;

import io.leopard.web4j.nobug.annotation.NoXss;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class XssAttributeCheckUtil {

	protected static final Log logger = LogFactory.getLog(XssAttributeCheckUtil.class);

	public static final String IGNORE_XSS_ATTRIBUTE_NAME = "xss-ignore";

	private static final Set<String> IGNORE_NAME_SET = new HashSet<String>();
	static {
		IGNORE_NAME_SET.add("javax.servlet.include.request_uri");
		IGNORE_NAME_SET.add("javax.servlet.include.context_path");
		IGNORE_NAME_SET.add("javax.servlet.include.servlet_path");
		IGNORE_NAME_SET.add("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping");
		IGNORE_NAME_SET.add("org.springframework.web.context.request.async.WebAsyncManager.WEB_ASYNC_MANAGER");
		IGNORE_NAME_SET.add("org.springframework.web.servlet.DispatcherServlet.INPUT_FLASH_MAP");
		IGNORE_NAME_SET.add("org.springframework.web.servlet.DispatcherServlet.OUTPUT_FLASH_MAP");
		IGNORE_NAME_SET.add("org.springframework.web.servlet.View.pathVariables");
		IGNORE_NAME_SET.add("org.springframework.web.servlet.support.RequestContext.CONTEXT");
		IGNORE_NAME_SET.add("org.springframework.web.servlet.DispatcherServlet.CONTEXT");
		IGNORE_NAME_SET.add("org.springframework.web.servlet.DispatcherServlet.LOCALE_RESOLVER");
		IGNORE_NAME_SET.add("org.springframework.web.servlet.HandlerMapping.uriTemplateVariables");
		IGNORE_NAME_SET.add("javax.servlet.jsp.jstl.fmt.localizationContext.request");
		IGNORE_NAME_SET.add("javax.servlet.jsp.jstl.fmt.timeZone.request");
		IGNORE_NAME_SET.add("org.apache.catalina.jsp_file");
		IGNORE_NAME_SET.add(IGNORE_XSS_ATTRIBUTE_NAME);
	}

	public static void checkAttribute(String name, Object value) {
		if (IGNORE_NAME_SET.contains(name)) {
			return;
		}

		checkObject(value);
	}

	public static void checkObject(Object value) {
		if (value == null) {
			return;
		}
		if (value instanceof String) {
			checkString((String) value);
		}
		if (isSafeValue(value)) {
			return;
		}

		else if (value instanceof List) {
			// System.err.println("list:" + name);
			checkList(value);
		}
		else if (value instanceof Set) {
			checkSet(value);
		}
		else if (value instanceof Map) {
			checkMap(value);
		}
		else if (CustomBeanUtil.isCustomBean(value.getClass())) {
			checkBean(value);
		}
	}

	/**
	 * 是否安全的数据类型.
	 * 
	 * @param value
	 * @return
	 */
	protected static boolean isSafeValue(Object value) {
		if (value instanceof Integer) {
			return true;
		}
		if (value instanceof Long) {
			return true;
		}
		if (value instanceof Float) {
			return true;
		}
		if (value instanceof Double) {
			return true;
		}
		if (value instanceof Date) {
			return true;
		}
		return false;
	}

	protected static boolean isSafeType(Class<?> clazz) {
		if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
			return true;
		}
		if (clazz.equals(long.class) || clazz.equals(Long.class)) {
			return true;
		}
		if (clazz.equals(float.class) || clazz.equals(Float.class)) {
			return true;
		}
		if (clazz.equals(double.class) || clazz.equals(Double.class)) {
			return true;
		}
		if (clazz.equals(Date.class)) {
			return true;
		}
		return false;
	}

	protected static void checkList(Object value) {
		@SuppressWarnings("rawtypes")
		List list = (List) value;
		for (Object element : list) {
			if (element instanceof String) {
				// System.err.println("element:" + element);
				checkString((String) element);
			}
			else if (isSafeValue(element)) {
				continue;
			}
			else if (CustomBeanUtil.isCustomBean(element.getClass())) {
				checkBean(element);
			}
		}
	}

	protected static void checkSet(Object value) {
		@SuppressWarnings("rawtypes")
		Set set = (Set) value;
		for (Object element : set) {
			if (element instanceof String) {
				// System.err.println("element:" + element);
				checkString((String) element);
			}
			else if (isSafeValue(element)) {
				continue;
			}
			else if (CustomBeanUtil.isCustomBean(element.getClass())) {
				checkBean(element);
			}
		}
	}

	protected static void checkMap(Object data) {
		@SuppressWarnings("unchecked")
		Map<Object, Object> map = (Map<Object, Object>) data;
		for (Entry<Object, Object> entry : map.entrySet()) {
			Object key = entry.getKey();
			Object value = entry.getValue();

			// System.err.println("checkMap key:" + key + " value:" + Json.toJson(value));
			checkObject(key);
			checkObject(value);
		}
	}

	protected static void checkBean(Object bean) {
		List<Field> fieldList = FieldCache.listFields(bean.getClass());
		for (Field field : fieldList) {
			Class<?> type = field.getType();
			// System.err.println("checkBean type:" + type.getName() + " name:" + field.getName() + " value:" + FieldUtil.getFieldValue(bean, field));
			if (type.equals(String.class)) {

				field.setAccessible(true);
				String value;
				try {
					value = (String) field.get(bean);
				}
				catch (IllegalAccessException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
				// String value = (String) FieldUtil.getFieldValue(bean, field);
				try {
					checkString(value);
				}
				catch (XssException e) {
					NoXss noXss = field.getAnnotation(NoXss.class);
					if (noXss == null) {
						logger.error("has xss json:" + value);
						throw e;
					}
				}
			}
		}
	}

	protected static void checkString(String value) {
		// System.err.println("value:" + value);
		if (value == null) {
			return;
		}
		// value = value.replace("<a href='javascript:void(0)' ", "");// TODO ahai 暂时方案,兼容信息推送系统
		// value = value.replace("onclick='AnalyzeClick", "");// TODO ahai 暂时方案,兼容信息推送系统

		boolean hasXss = XssCheckerImpl.getInstance().check(value);
		if (hasXss) {
			logger.error("has xss value:" + value);
			throw new XssException("页面属性有XSS风险.");
		}

	}

}
