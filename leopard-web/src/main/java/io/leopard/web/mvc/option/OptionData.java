package io.leopard.web.mvc.option;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.StringUtils;

import io.leopard.burrow.lang.inum.EnumUtil;
import io.leopard.burrow.lang.inum.Onum;
import io.leopard.burrow.util.StringUtil;

public class OptionData {

	private static Map<String, OptionInfo> data = new HashMap<>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void load(String id, String className) {
		Class<? extends Enum> clazz;
		try {
			clazz = (Class<? extends Enum>) Class.forName(className);
		}
		catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		if (StringUtils.isEmpty(id)) {
			id = StringUtil.firstCharToLowerCase(clazz.getName());
		}

		Map<Object, Object> data = toData(clazz);
		OptionInfo info = new OptionInfo();
		info.setId(id);
		info.setClassName(className);
		info.setClazz(clazz);
		info.setData(data);
		put(id, info);
	}

	@SuppressWarnings("unchecked")
	private static Map<Object, Object> toData(@SuppressWarnings("rawtypes") Class<? extends Enum> clazz) {
		Map<Object, Onum<Object, Object>> map = EnumUtil.toMap(clazz);
		Map<Object, Object> data = new LinkedHashMap<>();
		for (Entry<Object, Onum<Object, Object>> entry : map.entrySet()) {
			Object key = entry.getKey();
			Object desc = entry.getValue().getDesc();
			// System.err.println("put key:"+key+" desc:"+desc);
			data.put(key, desc);
		}
		return data;
	}

	public static void put(String id, OptionInfo info) {
		data.put(id, info);
	}

	public static Map<Object, Object> getData(String id) {
		OptionInfo info = data.get(id);
		if (info == null) {
			return null;
		}
		return info.getData();
	}
}
