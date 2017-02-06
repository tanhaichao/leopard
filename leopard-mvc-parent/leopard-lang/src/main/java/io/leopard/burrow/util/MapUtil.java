package io.leopard.burrow.util;

import io.leopard.burrow.lang.AssertUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MapUtil {

	// @SuppressWarnings("unchecked")
	// public static <TYPE, BEAN, NEWBEAN> Map<TYPE, NEWBEAN> toMap(Set<TYPE> keySet, List<BEAN> valueList, Class<NEWBEAN> clazz) {
	// Map<TYPE, BEAN> map = MapUtil.toMap(keySet, valueList);
	// Map<TYPE, NEWBEAN> result = new LinkedHashMap<TYPE, NEWBEAN>();
	// Iterator<Entry<TYPE, BEAN>> iterator = map.entrySet().iterator();
	// while (iterator.hasNext()) {
	// Entry<TYPE, BEAN> entry = iterator.next();
	// TYPE key = entry.getKey();
	// BEAN bean = entry.getValue();
	// result.put(key, (NEWBEAN) bean);
	// }
	// return result;
	// }

	/**
	 * 将键集、值列表合并成Map
	 * 
	 * @param keySet
	 *            键集
	 * @param valueList
	 *            值列表
	 * @return 合并后的Map
	 */
	public static <TYPE, BEAN> Map<TYPE, BEAN> toMap(Set<TYPE> keySet, List<BEAN> valueList) {
		AssertUtil.assertNotEmpty(keySet, "参数keySet不能为空.");
		AssertUtil.assertNotEmpty(valueList, "参数valueList不能为空.");
		if (keySet.size() != valueList.size()) {
			throw new IllegalArgumentException("两个list参数不一致.");
		}
		int index = 0;
		Map<TYPE, BEAN> map = new LinkedHashMap<TYPE, BEAN>();
		for (TYPE key : keySet) {
			BEAN bean = valueList.get(index);
			map.put(key, bean);
			index++;
		}
		return map;
	}

	// /**
	// * 转成value为布尔类型的map，空为false，非空为true
	// *
	// * @param map
	// * Map
	// * @return 转换后的Map
	// */
	// public static <KEYTYPE, BEAN> Map<KEYTYPE, Boolean> valueToBoolean(Map<KEYTYPE, BEAN> map) {
	// if (map == null) {
	// return null;
	// }
	// Map<KEYTYPE, Boolean> result = new LinkedHashMap<KEYTYPE, Boolean>();
	// Iterator<Entry<KEYTYPE, BEAN>> iterator = map.entrySet().iterator();
	// while (iterator.hasNext()) {
	// Entry<KEYTYPE, BEAN> entry = iterator.next();
	// KEYTYPE key = entry.getKey();
	// boolean hasData = entry.getValue() != null;
	// result.put(key, hasData);
	// }
	// return result;
	// }

	/**
	 * 转成value为数字类型的map,value为null时自动转换0.
	 * 
	 * @param map
	 *            Map
	 * @return 转换后的Map
	 */
	public static Map<String, Integer> toDefaultIntMap(Map<String, String> map) {
		Map<String, Integer> result = new LinkedHashMap<String, Integer>();
		for (Entry<String, String> entry : map.entrySet()) {
			String gameKey = entry.getKey();
			Integer value = NumberUtil.toInt(entry.getValue());
			result.put(gameKey, value);
		}
		return result;
	}

	/**
	 * 判断Map是否为空
	 * 
	 * @param map
	 *            Map
	 * @return Map对象为null或包含零个元素则返回true
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		if (map == null || map.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isNotEmpty(Map<?, ?> map) {
		if (map == null || map.isEmpty()) {
			return false;
		}
		else {
			return true;
		}
	}
}
