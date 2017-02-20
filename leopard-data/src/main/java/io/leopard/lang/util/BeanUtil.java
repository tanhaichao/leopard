package io.leopard.lang.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import io.leopard.json.Json;
import io.leopard.lang.Paging;
import io.leopard.lang.PagingImpl;

public class BeanUtil {

	public static <T> List<T> convert(List<?> list, Class<T> clazz) {
		if (list == null) {
			return null;
		}
		List<T> result = new ArrayList<T>();
		for (Object obj : list) {
			result.add(convert(obj, clazz));
		}
		return result;
	}

	public static <T> Paging<T> convert(Paging<?> paging, Class<T> clazz) {
		if (paging == null) {
			return null;
		}
		Paging<T> result = new PagingImpl<T>(paging);
		for (Object obj : paging.getList()) {
			result.add(convert(obj, clazz));
		}
		return result;
	}

	public static <T> T convert(Object obj, Class<T> clazz) {
		if (obj == null) {
			return null;
		}
		T target;
		try {
			target = clazz.newInstance();
		}
		catch (InstantiationException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		BeanUtils.copyProperties(obj, target);
		return target;
		// String json = Json.toJson(obj);
		// return Json.toObject(json, clazz, true);
	}

	public static <T> List<T> convertIncludeSub(List<?> list, Class<T> clazz) {
		if (list == null) {
			return null;
		}
		String json = Json.toJson(list);
		List<T> list2 = Json.toListObject(json, clazz, true);

		for (int i = 0; i < list.size(); i++) {
			Object bean = list.get(i);
			T result = list2.get(i);

		}
		return list2;
		// TODO ahai 支持子类属性自动拷贝
		// return convert2(list, clazz);
	}

	public static <T> List<T> convert2(List<?> list, Class<T> clazz) {
		if (list == null) {
			return null;
		}
		String json = Json.toJson(list);
		return Json.toListObject(json, clazz, true);
	}

	public static <T> T convert2(Object obj, Class<T> clazz) {
		if (obj == null) {
			return null;
		}
		String json = Json.toJson(obj);
		return Json.toObject(json, clazz, true);
	}
}
