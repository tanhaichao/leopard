package io.leopard.json;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

//import org.codehaus.jackson.map.ObjectWriter;

public class Json {
	private static final Class<?>[] classes = { JsonJacksonImpl.class, JsonFastJsonImpl.class };

	// private static final String[] classNames = { "io.leopard.json.JsonJacksonImpl", "io.leopard.json.JsonFastJsonImpl" };

	private static final IJson instance = newInstance();

	public static IJson getInstance() {
		return instance;
	}

	private static IJson newInstance() {
		for (Class<?> clazz : classes) {
			try {
				return (IJson) clazz.newInstance();
			}
			catch (NoClassDefFoundError e) {

			}
			catch (Exception e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		throw new NoClassDefFoundError("jackson或fastjson包找不到.");
	}

	public static String toFormatJson(Object obj) {
		return instance.toFormatJson(obj);
	}

	public static String toJson(Object obj) {
		return instance.toJson(obj);
	}

	public static <T> T toObject(String json, Class<T> clazz) {
		return instance.toObject(json, clazz);
	}

	public static <T> T toObject(String json, Class<T> clazz, boolean ignoreUnknownField) {
		return instance.toObject(json, clazz, ignoreUnknownField);
	}

	public static Map<String, Object> toMap(String json) {
		return instance.toMap(json);
	}

	public static <T> List<T> toListObject(String content, Class<T> clazz) {
		return instance.toListObject(content, clazz);
	}

	public static <T> List<T> toListObject(String content, Class<T> clazz, boolean ignoreUnknownField) {
		return instance.toListObject(content, clazz, ignoreUnknownField);
	}

	public static <T> List<T> toListObject(List<String> jsonList, Class<T> clazz) {
		return instance.toListObject(jsonList, clazz);
	}

	public static <T> List<T> toListObject(List<String> jsonList, Class<T> clazz, boolean ignoreUnknownField) {
		return instance.toListObject(jsonList, clazz, ignoreUnknownField);
	}

	public static void print(Object obj) {
		String json = toJson(obj);
		System.out.println("json:" + json);
	}

	public static void print(Object obj, String name) {
		String json = toJson(obj);
		System.out.println("json info " + name + "::" + json);
	}

	public static void printFormat(Object obj, String name) {
		String json = toFormatJson(obj);
		System.out.println("json info " + name + "::" + json);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void printMap(Map map, String name) {
		if (map == null) {
			System.out.println("json info " + name + "::null");
			return;
		}
		if (map.size() == 0) {
			System.out.println("json info " + name + "::");
			return;
		}
		Iterator<Entry> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry entry = iterator.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			System.out.println(name + " key:" + key + " json:" + toJson(value));
		}
	}

	// public static void printPaging(@SuppressWarnings("rawtypes") Paging paging, String name) {
	// System.out.println("hasNextPage:" + paging.hasNextPage());
	// System.out.println("count:" + paging.getCount());
	// printList(paging, name);
	// }

	@SuppressWarnings({ "rawtypes" })
	public static void printList(List list, String name) {
		if (list == null) {
			System.out.println("json info " + name + "::null");
			return;
		}
		if (list.size() == 0) {
			System.out.println("json info " + name + "::");
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			Object element = list.get(i);
			System.out.println("json info(" + i + ") " + name + "::" + toJson(element));
		}

	}

	// protected static String toJson(ObjectWriter writer, Object obj, String fullMethodName) {
	// if (obj == null) {
	// return null;
	// }
	// try {
	// return writer.writeValueAsString(obj);
	// }
	// catch (Exception e) {
	// throw new JsonException(e.getMessage(), e);
	// }
	// }
}
