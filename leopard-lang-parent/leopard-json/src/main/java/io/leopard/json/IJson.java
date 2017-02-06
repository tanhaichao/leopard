package io.leopard.json;

import java.util.List;
import java.util.Map;

public interface IJson {

	/**
	 * 将对象转成json.
	 * 
	 * @param obj 对象
	 * @return
	 */
	String toJson(Object obj);

	/**
	 * 将对象转成json.
	 * 
	 * @param obj 对象
	 * @return
	 */
	String toFormatJson(Object obj);

	/**
	 * 将Json转换成对象.
	 * 
	 * @param json
	 * @param valueTypes
	 * @return
	 */
	<T> T toObject(String json, Class<T> clazz);

	<T> T toObject(String json, Class<T> clazz, boolean ignoreUnknownField);

	<T> List<T> toListObject(String json, Class<T> clazz);

	<T> List<T> toListObject(String json, Class<T> clazz, boolean ignoreUnknownField);

	<T> List<T> toListObject(List<String> jsonList, Class<T> clazz);

	<T> List<T> toListObject(List<String> jsonList, Class<T> clazz, boolean ignoreUnknownField);

	Map<String, Object> toMap(String json);
}
