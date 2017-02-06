package io.leopard.data4j.cache;

import io.leopard.data4j.cache.api.IGet;
import io.leopard.data4j.cache.api.IList;
import io.leopard.data4j.cache.api.ISimpleMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 多个DAO实现缓存的操作类.
 * 
 * @author 阿海
 * 
 */
public class CacheLoader {
	protected final static Log logger = LogFactory.getLog(CacheLoader.class);

	/**
	 * 按主键查询单条记录.
	 * 
	 * <pre>
	 * 流程(涉及IAdd和IGet两个标准方法): 
	 * 1、执行memcachedImpl.get方法。如果有数据就直接返回，没有数据则进入mysqlImpl. 
	 * 2、执行mysqlImpl.get方法。如果没有数据就直接返回null，有数据则先将数据添加到memcachedImpl(调用memcachedImpl.add方法)，然后返回。
	 * </pre>
	 * 
	 * 
	 * @param memcachedImpl
	 *            DAO的memcache实现类.
	 * 
	 * @param mysqlImpl
	 *            DAO的mysql实现类.
	 * @param key
	 *            主键，即get方法的参数值.
	 * @return
	 */
	public static <BEAN, KEYTYPE> BEAN get(IGet<BEAN, KEYTYPE> memcachedImpl, IGet<BEAN, KEYTYPE> mysqlImpl, KEYTYPE key) {
		// return CacheLoader1.get(memcachedImpl, mysqlImpl, key);

		BEAN bean = memcachedImpl.get(key);
		if (bean == null) {
			bean = rsyncToCache(memcachedImpl, mysqlImpl, key);
		}
		return bean;
	}

	/**
	 * 按主键查询单条记录.
	 * 
	 * <pre>
	 * 流程(涉及IAdd和IGet两个标准方法): 
	 * 1、执行memoryImpl.get方法。如果有数据就直接返回，没有数据则进入memcachedImpl. 
	 * 2、执行memcachedImpl.get方法。如果没有数据则进入mysqlImpl，有数据则先将数据添加到memoryImpl(调用memoryImpl.add方法)，然后返回。
	 * 3、执行mysqlImpl.get方法。如果没有数据就直接返回null，有数据则先将数据添加到memcachedImpl(调用memcachedImpl.add方法)，然后返回。
	 * </pre>
	 * 
	 * @param memoryImpl
	 *            DAO层内存实现类.
	 * 
	 * @param memcachedImpl
	 *            DAO的memcache实现类.
	 * 
	 * @param mysqlImpl
	 *            DAO的mysql实现类.
	 * @param key
	 *            主键，即get方法的参数值.
	 * @return
	 */
	public static <BEAN, KEYTYPE> BEAN get(IGet<BEAN, KEYTYPE> memoryImpl, IGet<BEAN, KEYTYPE> memcachedImpl, IGet<BEAN, KEYTYPE> mysqlImpl, KEYTYPE key) {
		// return CacheLoader1.get(memoryImpl, memcachedImpl, mysqlImpl, key);
		BEAN bean = memoryImpl.get(key);
		if (bean == null) {
			bean = rsyncToCache(memoryImpl, memcachedImpl, key);
			if (bean == null) {
				bean = rsyncToCache(memcachedImpl, mysqlImpl, key);
			}
		}
		return bean;
	}

	/**
	 * 获取没有数据(value==null)的key.
	 * 
	 * @param map
	 * @return
	 */
	public static <BEAN, KEYTYPE> Set<KEYTYPE> getNoDataKeySet(Map<KEYTYPE, BEAN> map) {
		Set<KEYTYPE> keySet = null;
		Iterator<Entry<KEYTYPE, BEAN>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<KEYTYPE, BEAN> entry = iterator.next();
			BEAN bean = entry.getValue();
			if (bean == null) {
				if (keySet == null) {
					keySet = new LinkedHashSet<KEYTYPE>();
				}
				keySet.add(entry.getKey());
			}
		}
		return keySet;
	}

	public static <BEAN, KEYTYPE> Map<KEYTYPE, BEAN> map(ISimpleMap<BEAN, KEYTYPE> mysqlImpl, Set<KEYTYPE> keySet) {
		Map<KEYTYPE, BEAN> map = new LinkedHashMap<KEYTYPE, BEAN>();
		if (isEmptySet(keySet)) {
			return map;
		}
		for (KEYTYPE key : keySet) {
			map.put(key, mysqlImpl.get(key));
		}
		return map;
	}

	/**
	 * 按照keySet批量查询记录(返回值按照keySet顺序).
	 * 
	 * <pre>
	 * 流程(涉及IAdd、IGet、IMap三个方法):
	 * 1、执行memoryImpl.map(keySet)方法，如果keySet全部都有对应的数据则直接返回，没有对应的数据keySet元素组成一个新的KeySet进入memcachedImpl获取数据合并返回.
	 * 2、执行memcachedImpl.map(keySet)，如果keySet全部都有对应的数据则add到memoryImpl并返回，没有对应的数据keySet元素进入mysqlImpl获取数据合并返回.
	 * 3、执行mysqlImpl.map(keySet)，如果keySet全部都有对应的数据则add到memcachedImpl并返回。
	 * </pre>
	 * 
	 * @param memoryImpl
	 *            DAO的内存实现类.
	 * @param memcachedImpl
	 *            DAO的memcached实现类.
	 * @param mysqlImpl
	 *            DAO的Mysql实现类.
	 * 
	 * @param keySet
	 *            主键ID集合.(如usernameSet、articleIdSet)
	 * 
	 * @return 按照keySet顺序返回对应的数据.
	 */
	public static <BEAN, KEYTYPE> Map<KEYTYPE, BEAN> map(ISimpleMap<BEAN, KEYTYPE> memoryImpl, ISimpleMap<BEAN, KEYTYPE> memcachedImpl, ISimpleMap<BEAN, KEYTYPE> mysqlImpl, Set<KEYTYPE> keySet) {
		if (isEmptySet(keySet)) {
			return new LinkedHashMap<KEYTYPE, BEAN>();
		}
		Map<KEYTYPE, BEAN> map = memoryImpl.map(keySet);
		Set<KEYTYPE> noDataKeySet = getNoDataKeySet(map);// 内存中没有数据的key
		if (noDataKeySet == null) {
			return map;
		}

		Map<KEYTYPE, BEAN> map2 = map(memcachedImpl, mysqlImpl, noDataKeySet);
		Iterator<Entry<KEYTYPE, BEAN>> iterator = map2.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<KEYTYPE, BEAN> entry = iterator.next();
			BEAN bean = entry.getValue();
			if (bean != null) {
				KEYTYPE key = entry.getKey();
				map.put(key, bean);

				memoryImpl.add(bean);
			}
		}
		return map;
	}

	public static <BEAN, KEYTYPE> Map<KEYTYPE, BEAN> map(ISimpleMap<BEAN, KEYTYPE> memcachedImpl, ISimpleMap<BEAN, KEYTYPE> mysqlImpl, Set<KEYTYPE> keySet) {
		if (isEmptySet(keySet)) {
			return new LinkedHashMap<KEYTYPE, BEAN>();
		}
		Map<KEYTYPE, BEAN> map = memcachedImpl.map(keySet);

		Set<KEYTYPE> noDataKeySet = getNoDataKeySet(map);// 内存中没有数据的key
		if (noDataKeySet == null) {
			return map;
		}

		Map<KEYTYPE, BEAN> map2 = mysqlImpl.map(noDataKeySet);
		Iterator<Entry<KEYTYPE, BEAN>> iterator = map2.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<KEYTYPE, BEAN> entry = iterator.next();
			BEAN bean = entry.getValue();
			if (bean != null) {
				KEYTYPE key = entry.getKey();
				map.put(key, bean);
				memcachedImpl.add(bean);
			}
		}
		// Iterator<Entry<KEYTYPE, BEAN>> iterator = map.entrySet().iterator();
		// while (iterator.hasNext()) {
		// Entry<KEYTYPE, BEAN> entry = iterator.next();
		// KEYTYPE key = entry.getKey();
		// BEAN bean = entry.getValue();
		// // System.err.println("get key:" + key + " bean:" + bean);
		// if (bean == null) {
		// bean = mysqlImpl.get(key);
		// if (bean != null) {
		// memcachedImpl.add(bean);
		// map.put(key, bean);
		// // System.err.println("add key:" + key + " bean:" + bean);
		// // System.err.println("get key:" + key + " bean:" + map.get(key));
		// }
		// }
		// }
		return map;
	}

	public static <BEAN, KEYTYPE> List<BEAN> list(ISimpleMap<BEAN, KEYTYPE> memcachedImpl, ISimpleMap<BEAN, KEYTYPE> mysqlImpl, List<KEYTYPE> keyList) {
		List<BEAN> result = new ArrayList<BEAN>();
		if (isEmptyList(keyList)) {
			return result;
		}
		Set<KEYTYPE> keySet = toSet(keyList);
		Map<KEYTYPE, BEAN> map = CacheLoader.map(memcachedImpl, mysqlImpl, keySet);
		for (KEYTYPE key : keyList) {
			BEAN bean = map.get(key);
			result.add(bean);
		}
		return result;
	}

	public static <BEAN, KEYTYPE> List<BEAN> list(ISimpleMap<BEAN, KEYTYPE> memoryImpl, ISimpleMap<BEAN, KEYTYPE> memcachedImpl, ISimpleMap<BEAN, KEYTYPE> mysqlImpl, List<KEYTYPE> keyList) {
		List<BEAN> result = new ArrayList<BEAN>();
		if (isEmptyList(keyList)) {
			return result;
		}
		Set<KEYTYPE> keySet = toSet(keyList);
		Map<KEYTYPE, BEAN> map = CacheLoader.map(memoryImpl, memcachedImpl, mysqlImpl, keySet);
		for (KEYTYPE key : keyList) {
			BEAN bean = map.get(key);
			result.add(bean);
		}
		return result;
	}

	public static <BEAN, KEYTYPE> List<BEAN> list(io.leopard.data4j.cache.api.uid.IList<BEAN, KEYTYPE> mysqlImpl, List<KEYTYPE> keyList) {
		List<BEAN> list = new ArrayList<BEAN>();
		if (isEmptyList(keyList)) {
			return list;
		}
		for (KEYTYPE key : keyList) {
			list.add(mysqlImpl.get(key));
		}
		return list;
	}

	public static <BEAN, KEYTYPE> List<BEAN> list(IList<BEAN, KEYTYPE> mysqlImpl, List<KEYTYPE> keyList) {
		List<BEAN> list = new ArrayList<BEAN>();
		if (isEmptyList(keyList)) {
			return list;
		}
		for (KEYTYPE key : keyList) {
			list.add(mysqlImpl.get(key));
		}
		return list;
	}

	// private void start() {
	// Map<Integer, String> map = new LinkedHashMap<Integer, String>();
	//
	// map.put(1, "1");
	// map.put(2, "2");
	// // map.remove(1);
	// map.put(1, "1");
	// map.put(1, "1");
	// map.put(1, "3");
	// map.put(1, null);
	// map.put(4, null);
	// map.put(5, null);
	//
	// Iterator<Entry<Integer, String>> iterator = map.entrySet().iterator();
	// while (iterator.hasNext()) {
	// Entry<Integer, String> entry = iterator.next();
	// System.out.println(entry.getKey() + ":" + entry.getValue());
	// }
	//
	// Set<Integer> noDataKeySet = CacheLoader.getNoDataKeySet(map);
	// System.out.println("noDataKeySet:" + noDataKeySet);
	//
	// }

	protected static <BEAN, KEYTYPE> BEAN rsyncToCache(IGet<BEAN, KEYTYPE> cacheImpl, IGet<BEAN, KEYTYPE> sourceImpl, KEYTYPE key) {
		BEAN bean = sourceImpl.get(key);
		if (bean != null) {
			try {
				cacheImpl.add(bean);
			}
			catch (RuntimeException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return bean;
	}

	// public static void main(String[] args) {
	// new CacheLoader().start();
	// }

	/**
	 * 判断List是否为空
	 * 
	 * @param list
	 *            List
	 * @return 若空返回true
	 */
	private static boolean isEmptyList(List<?> list) {
		if (list == null || list.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * 判断一个set是否为null</br>
	 * 
	 * @param set
	 * @return
	 */
	private static boolean isEmptySet(Set<?> set) {
		if (set == null || set.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}

	private static <KEYTYPE> Set<KEYTYPE> toSet(List<KEYTYPE> list) {
		if (isEmptyList(list)) {
			return null;
		}
		Set<KEYTYPE> set = new LinkedHashSet<KEYTYPE>();
		for (KEYTYPE element : list) {
			set.add(element);
		}
		return set;
	}
}
