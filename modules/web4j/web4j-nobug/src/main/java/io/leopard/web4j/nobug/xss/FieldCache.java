package io.leopard.web4j.nobug.xss;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FieldCache {

	private static Map<String, List<Field>> map = new ConcurrentHashMap<String, List<Field>>();

	protected static List<Field> listFields(Class<?> clazz) {
		String className = clazz.getName();
		List<Field> list = map.get(className);
		if (list != null) {
			return list;
		}
		return loadFields(clazz, className);
	}

	protected static synchronized List<Field> loadFields(Class<?> clazz, String className) {
		List<Field> list = map.get(className);
		if (list != null) {
			return list;
		}
		list = new ArrayList<Field>();
		while (true) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				list.add(field);
			}
			if (clazz.getSuperclass() == null) {
				break;
			}
			clazz = clazz.getSuperclass();
		}
		map.put(className, list);
		return list;
	}
}
