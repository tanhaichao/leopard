package io.leopard.lang.inum.daynamic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.leopard.lang.inum.Onum;

public class DynamicEnum<KEYTYPE> implements Onum<KEYTYPE, String> {

	private static Map<String, Map<Object, EnumConstant>> ENUM_MAP = Collections.synchronizedMap(new LinkedHashMap<>());

	protected KEYTYPE key;

	protected String desc;

	protected EnumConstant constant;

	public DynamicEnum() {

	}

	public DynamicEnum(KEYTYPE key) {
		Map<Object, EnumConstant> constantMap = ENUM_MAP.get(this.getClass().getName());
		if (constantMap == null) {
			throw new NullPointerException("动态枚举[" + this.getClass().getName() + "]未初始化.");
		}
		if (!constantMap.containsKey(key)) {
			throw new IllegalArgumentException("枚举元素[" + key + "]不存在.");
		}
		this.key = key;
		this.constant = constantMap.get(key);
		this.desc = constant.getDesc();

		this.initParameters();
	}

	protected void initParameters() {
		java.lang.reflect.Field[] fields = this.getClass().getDeclaredFields();
		if (fields != null) {
			for (java.lang.reflect.Field field : fields) {
				field.setAccessible(true);
				String parameterName = resolveParameterName(field);
				Object value = constant.getParameter(parameterName);
				try {
					field.set(this, value);
				}
				catch (IllegalAccessException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 解析参数名称
	 * 
	 * @param field
	 * @return
	 */
	private String resolveParameterName(java.lang.reflect.Field field) {
		Parameter anno = field.getAnnotation(Parameter.class);
		if (anno == null) {
			return field.getName();
		}
		String fieldName = anno.value();
		if (fieldName == null || fieldName.length() == 0) {
			return field.getName();
		}
		return fieldName;
	}

	@Override
	public KEYTYPE getKey() {
		return key;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	public static <E extends DynamicEnum<?>> List<E> values(Class<E> clazz) {
		return DynamicEnumUtil.values(clazz);
	}

	public List<EnumConstant> all() {
		String enumKey = this.getClass().getName();
		return allOf(enumKey);
	}

	public static List<EnumConstant> allOf(Class<?> clazz) {
		return allOf(clazz.getName());
	}

	public static List<EnumConstant> allOf(String enumKey) {
		Map<Object, EnumConstant> constantMap = ENUM_MAP.get(enumKey);
		if (constantMap == null) {
			return null;
		}
		return new ArrayList<>(constantMap.values());
	}

	public static EnumConstant getConstant(String enumKey, Object constantKey) {
		Map<Object, EnumConstant> constantMap = ENUM_MAP.get(enumKey);
		if (constantMap == null) {
			return null;
		}
		return constantMap.get(constantKey);
	}

	public static EnumConstant putEnumConstant(Class<?> clazz, Object key, String desc) {
		Map<Object, EnumConstant> constantMap = ENUM_MAP.get(clazz.getName());
		if (constantMap == null) {
			constantMap = Collections.synchronizedMap(new LinkedHashMap<>());
			ENUM_MAP.put(clazz.getName(), constantMap);
		}
		EnumConstant constant = new EnumConstant();
		constant.setKey(key);
		constant.setDesc(desc);
		constantMap.put(key, constant);
		return constant;
	}

	@Override
	public String toString() {
		return "key:" + this.key + " desc:" + this.desc + " parameters:" + this.constant.getParameterMap();
	}
}
