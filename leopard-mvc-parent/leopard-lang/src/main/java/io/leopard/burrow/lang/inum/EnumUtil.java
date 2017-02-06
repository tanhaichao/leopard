package io.leopard.burrow.lang.inum;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 枚举工具类
 * 
 * @author 谭海潮
 *
 */
public class EnumUtil {

	protected static final Map<Object, Map<Object, Enum<?>>> cache = new ConcurrentHashMap<Object, Map<Object, Enum<?>>>();

	// public static <E extends Enum<E>> EnumSet<E> of(E e) {

	public static <E extends Enum<E>> String getDesc(Object key, Class<E> clazz) {
		// Onum<KEYTYPE, VALUETYPE> obj = EnumUtil.toEnum(key, clazz);
		E e = EnumUtil.get(key, clazz);
		if (e == null) {
			return null;
		}
		@SuppressWarnings("unchecked")
		Onum<Object, Object> onum = (Onum<Object, Object>) e;
		return (String) onum.getDesc();
	}

	/**
	 * 根据ID转换为枚举(元素不存在会抛异常).
	 * 
	 * @param id
	 * @param clazz
	 * @return
	 */
	public static <E extends Enum<E>> E toEnum(Object key, Class<E> clazz) {
		E inum = get(key, clazz);
		if (inum == null) {
			System.err.println("key:" + key.getClass().getName());
			throw new IllegalArgumentException("枚举元素[" + key + "]不存在[" + clazz.getName() + "].");
		}
		return inum;
	}

	/**
	 * 根据ID转换为枚举(元素不存在返回onum).
	 * 
	 * @param key
	 * @param clazz
	 * @param onum
	 * @return
	 */
	public static <E extends Enum<E>> E toEnum(Object key, Class<E> clazz, E onum) {
		E inum = get(key, clazz);
		if (inum == null) {
			return onum;
		}
		return inum;
	}

	/**
	 * 判断key是否存在.
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	public static <E extends Enum<E>> boolean contains(Object key, Class<E> clazz) {
		key = toLowerCase(key);
		Map<Object, Enum<?>> map = cache.get(key);
		if (map == null) {
			map = toMap(key, clazz);
		}
		return map.containsKey(key);
	}

	/**
	 * 根据ID转换为枚举(元素不存在则返回null，不抛异常)
	 * 
	 * @param id
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E extends Enum<E>> E get(Object key, Class<E> clazz) {
		key = toLowerCase(key);
		Map<Object, Enum<?>> map = cache.get(key);
		if (map == null) {
			map = toMap(key, clazz);
		}
		return (E) map.get(key);
	}

	public static synchronized <E extends Enum<E>> Map<Object, Enum<?>> toMap(Class<E> clazz) {
		return toMap(clazz.getName(), clazz);
	}

	@SuppressWarnings("unchecked")
	protected static synchronized <E extends Enum<E>> Map<Object, Enum<?>> toMap(Object key, Class<E> clazz) {
		Map<Object, Enum<?>> map = cache.get(key);
		if (map != null) {
			return map;
		}
		map = new HashMap<Object, Enum<?>>();
		EnumSet<E> set = EnumSet.allOf(clazz);
		Iterator<E> iterator = set.iterator();
		while (iterator.hasNext()) {
			Onum<Object, Object> value = (Onum<Object, Object>) iterator.next();
			Object id = value.getKey();
			id = toLowerCase(id);
			map.put(id, (Enum<?>) value);
		}
		// cache.put(key, map);
		cache.put(clazz.getName(), map);
		return map;
	}

	protected static Object toLowerCase(Object key) {
		if (key instanceof String) {
			return ((String) key).toLowerCase();// 统一转成小写保存
		}
		else {
			return key;
		}
	}

}
