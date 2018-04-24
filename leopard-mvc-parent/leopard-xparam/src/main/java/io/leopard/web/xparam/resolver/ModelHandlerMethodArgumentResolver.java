package io.leopard.web.xparam.resolver;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import io.leopard.json.Json;
import io.leopard.lang.datatype.Month;
import io.leopard.lang.datatype.OnlyDate;
import io.leopard.lang.inum.Bnum;
import io.leopard.lang.inum.EnumConstantInvalidException;
import io.leopard.lang.inum.EnumUtil;
import io.leopard.lang.inum.Inum;
import io.leopard.lang.inum.Snum;
import io.leopard.lang.inum.daynamic.DynamicEnum;
import io.leopard.lang.inum.daynamic.DynamicEnumUtil;
import io.leopard.lang.util.FieldUtil;

/**
 * 下划线参数名称解析.
 * 
 * @author 阿海
 *
 */
@Component
@Order(6)
public class ModelHandlerMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver implements XParamResolver {

	protected Log logger = LogFactory.getLog(this.getClass());

	private static Set<Class<?>> set = new HashSet<Class<?>>();
	static {
		set.add(MultipartFile.class);
		set.add(String.class);
		set.add(Boolean.class);
		set.add(Date.class);
		set.add(Integer.class);
		set.add(Long.class);
		set.add(Float.class);
		set.add(Double.class);
		set.add(List.class);
		set.add(Month.class);
		set.add(OnlyDate.class);
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> type = parameter.getParameterType();
		if (type.isEnum()) {
			return false;
		}
		if (type.isArray()) {
			return false;
		}
		if (type.isPrimitive()) {
			return false;
		}
		boolean supports = !set.contains(type);
		return supports;
	}

	@Override
	protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request.getNativeRequest();
		Class<?> clazz = parameter.getParameterType();

		Object bean = clazz.newInstance();

		for (Field field : FieldUtil.listFields(clazz)) {
			String fieldName = field.getName();
			Class<?> type = field.getType();
			Object obj;
			if (List.class.equals(type)) {
				Class<?> subType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
				obj = this.toList(fieldName, subType, req);
			}
			else if (Map.class.equals(type)) {
				throw new NotImplementedException("Map类型未实现.");
			}
			else if (Set.class.equals(type)) {
				throw new NotImplementedException("Set类型未实现.");
			}
			else {
				String value = RequestBodyParser.getParameter(req, fieldName);
				// logger.info("fieldName:" + fieldName + " value:" + value);
				if (value == null) {
					continue;
				}
				obj = toObject(value, type, fieldName);
			}
			field.setAccessible(true);
			field.set(bean, obj);
		}

		return bean;
	}

	protected Object toList(String fieldName, Class<?> subType, HttpServletRequest req) {
		String json;
		{
			String[] values = ParamListHandlerMethodArgumentResolver.getParameterValues(req, fieldName);
			// logger.info("toList fieldName:" + fieldName + " values:" + StringUtils.join(values, ", "));
			if (values == null) {
				return null;
			}
			json = values[0];
		}
		return UnderlineJson.toListObject(json, subType);

		// if (subType.equals(String.class)) {
		// List<String> list = new ArrayList<String>();
		// for (String value : values) {
		// list.add(value);
		// }
		// return list;
		// }
		// else if (subType.equals(Integer.class)) {
		// throw new NotImplementedException("List<Integer>未实现.");
		// }
		// else if (subType.equals(Long.class)) {
		// throw new NotImplementedException("List<Long>未实现.");
		// }
		// else if (subType.equals(Float.class)) {
		// throw new NotImplementedException("List<Float>未实现.");
		// }
		// else if (subType.equals(Double.class)) {
		// throw new NotImplementedException("List<Double>未实现.");
		// }
		// else if (subType.equals(Boolean.class)) {
		// throw new NotImplementedException("List<Boolean>未实现.");
		// }
		// else if (subType.equals(Date.class)) {
		// throw new NotImplementedException("List<Date>未实现.");
		// }
		// return ParamListHandlerMethodArgumentResolver.toList(subType, values);
	}

	private static Object toObject(String value, Class<?> type, String fieldName) {
		if (String.class.equals(type)) {
			return value;
		}
		else if (boolean.class.equals(type)) {
			return "true".equals(value);
		}
		else if (int.class.equals(type)) {
			return NumberUtils.toInt(value);
		}
		else if (Integer.class.equals(type)) {
			if (StringUtils.isEmpty(value)) {
				return null;
			}
			return NumberUtils.toInt(value);
		}
		else if (long.class.equals(type)) {
			return NumberUtils.toLong(value);
		}
		else if (Long.class.equals(type)) {
			if (StringUtils.isEmpty(value)) {
				return null;
			}
			return NumberUtils.toLong(value);
		}
		else if (float.class.equals(type)) {
			return NumberUtils.toFloat(value);
		}
		else if (Float.class.equals(type)) {
			if (StringUtils.isEmpty(value)) {
				return null;
			}
			return NumberUtils.toFloat(value);
		}
		else if (double.class.equals(type)) {
			return NumberUtils.toDouble(value);
		}
		else if (Double.class.equals(type)) {
			if (StringUtils.isEmpty(value)) {
				return null;
			}
			return NumberUtils.toDouble(value);
		}
		else if (Month.class.equals(type)) {
			if (StringUtils.isEmpty(value)) {
				return null;
			}
			return new Month(value);
		}
		else if (OnlyDate.class.equals(type)) {
			if (StringUtils.isEmpty(value)) {
				return null;
			}
			return new OnlyDate(value);
		}
		else if (Date.class.equals(type)) {
			long time = NumberUtils.toLong(value);
			if (time <= 0) {
				return null;
			}
			return new Date(time);
		}
		else if (DynamicEnum.class.isAssignableFrom(type)) {
			return toDynamicEnum(value, type);
		}
		else if (type.isEnum()) {
			// throw new RuntimeException("枚举类型未实现[" + type.getName() + " value:" + value + "].");
			return toEnum(value, type, fieldName);
		}
		else {
			return UnderlineJson.toObject(value, type);
		}
		// throw new IllegalArgumentException("未知数据类型[" + type.getName() + "].");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Enum<?> toEnum(String value, Class<?> type, String fieldName) {
		if (value == null) {
			return null;
		}
		if (value.startsWith("{")) {// json
			Map<String, Object> map = Json.toMap(value);
			Object key = map.get("key");
			if (key == null) {
				return null;
			}
			value = key.toString();
		}

		if (Snum.class.isAssignableFrom(type)) {
			try {
				return EnumUtil.toEnum(value, (Class<Enum>) type);
			}
			catch (EnumConstantInvalidException e) {
				if ("".equals(value)) {
					return null;
				}
				else {
					throw new NumberFormatException("参数(" + fieldName + ")枚举元素[" + value + "]不存在[" + type.getSimpleName() + "].");
					// throw e;
				}
			}
		}
		else if (Inum.class.isAssignableFrom(type)) {
			int key;
			try {
				key = Integer.parseInt(value);
			}
			catch (NumberFormatException e) {
				throw new NumberFormatException("参数(" + fieldName + ")枚举[" + type.getSimpleName() + "]值必须为数字[" + value + "].");
			}
			return EnumUtil.toEnum(key, (Class<Enum>) type);
		}
		else if (Bnum.class.isAssignableFrom(type)) {
			byte key = Byte.parseByte(value);
			return EnumUtil.toEnum(key, (Class<Enum>) type);
		}
		else {
			throw new EnumConstantInvalidException("未知枚举类型[" + type.getName() + "].");
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected static DynamicEnum<?> toDynamicEnum(String value, Class<?> type) {
		if (value == null) {
			return null;
		}
		if (value.startsWith("{")) {// json
			Map<String, Object> map = Json.toMap(value);
			Object key = map.get("key");
			if (key == null) {
				return null;
			}
			value = key.toString();
		}

		if (Snum.class.isAssignableFrom(type)) {
			try {
				return DynamicEnumUtil.toEnum(value, (Class<? extends DynamicEnum>) type);
			}
			catch (EnumConstantInvalidException e) {
				if ("".equals(value)) {
					return null;
				}
				else {
					throw e;
				}
			}
		}
		else if (Inum.class.isAssignableFrom(type)) {
			int key = Integer.parseInt(value);
			return DynamicEnumUtil.toEnum(key, (Class<? extends DynamicEnum>) type);
		}
		else if (Bnum.class.isAssignableFrom(type)) {
			byte key = Byte.parseByte(value);
			return DynamicEnumUtil.toEnum(key, (Class<? extends DynamicEnum>) type);
		}
		else {
			throw new EnumConstantInvalidException("未知枚举类型[" + type.getName() + "].");
		}
	}
}
