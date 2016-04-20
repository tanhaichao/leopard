package io.leopard.monitor.model;

import io.leopard.burrow.refect.ClassTypeUtil;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;

public class BaseInfoBuilder {
	private final BaseInfo baseInfo = new BaseInfo();
	private final Map<String, Boolean> defaultFlag = new ConcurrentHashMap<String, Boolean>();

	public BaseInfo getBaseInfo() {
		return baseInfo;
	}
	

	public void setField(NamedNodeMap namedNodeMap, String fieldName) {
		Attr attr = (Attr) namedNodeMap.getNamedItem(fieldName);
		if (attr != null) {
			boolean isDefaultValue = !attr.getSpecified();
			boolean success = setDefaultFlag(fieldName, isDefaultValue);
			if (success) {

				// Json.print(baseInfo, "baseInfo");
				Object value = this.toBaseInfoValue(fieldName, attr.getNodeValue());
				try {
					BeanUtils.setProperty(baseInfo, fieldName, value);
				}
				catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		}
	}

	protected boolean setDefaultFlag(String fieldName, boolean isDefaultValue) {
		Boolean isDefaultFlag = defaultFlag.get(fieldName);
		if (isDefaultFlag == null || isDefaultFlag) {
			defaultFlag.put(fieldName, isDefaultValue);
			return true;
		}
		else if (isDefaultValue) {
			return false;
		}
		else {
			defaultFlag.put(fieldName, isDefaultValue);
			return true;
		}
	}

	protected boolean isDefaultValue(String fieldName) {
		Boolean flag = defaultFlag.get(fieldName);
		return (flag != null && flag == true);
	}

	protected Object toBaseInfoValue(String fieldName, String value) {
		Field field = FieldUtils.getField(BaseInfo.class, fieldName, true);
		// System.out.println("fieldName:" + fieldName + " field:" + field);
		Class<?> type = field.getType();
		if (ClassTypeUtil.isInteger(type)) {
			return Integer.parseInt(value);
		}
		else if (ClassTypeUtil.isLong(type)) {
			return Long.parseLong(value);
		}
		else if (ClassTypeUtil.isFloat(type)) {
			return Float.parseFloat(value);
		}
		else if (ClassTypeUtil.isDouble(type)) {
			return Double.parseDouble(value);
		}
		else if (type.equals(String.class)) {
			return value;
		}
		else {
			throw new IllegalArgumentException("未知类型[" + type.getName() + "].");
		}
	}
}
