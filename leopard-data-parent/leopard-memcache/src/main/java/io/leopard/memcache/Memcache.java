package io.leopard.memcache;

import java.util.List;

/**
 * Memcache接口.
 * 
 * @author 阿海
 * 
 */
public interface Memcache {

	// String[] getServers();

	/**
	 * 根据key删除缓存.
	 * 
	 * @param key
	 * @return 删除成功返回true
	 */
	boolean remove(String key);

	/**
	 * 添加缓存，若已存在则覆写原来的数据.
	 * 
	 * @param key
	 * @param value
	 * @return 添加成功返回true
	 */
	boolean put(String key, String value);

	/**
	 * 添加缓存，并设置有效时间，若已存在则覆写原来的数据.
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 * @return 添加成功返回true
	 */
	boolean put(String key, String value, int seconds);

	/**
	 * 添加缓存，若已存在则覆写原来的数据.
	 * 
	 * @param key
	 * @param value
	 * @return 添加成功返回true
	 */
	boolean put(String key, int value);

	/**
	 * 添加缓存，并设置有效时间，若已存在则覆写原来的数据.
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 * @return 添加成功返回true
	 */
	boolean put(String key, int value, int seconds);

	/**
	 * 根据key获取缓存数据.
	 * 
	 * @param key
	 * @return 返回存储的对象，若不存在则返回null
	 */
	String get(String key);

	// /**
	// * 根据key获取缓存数据，返回指定类型的对象.
	// *
	// * @param key
	// * @param clazz
	// * @return 返回存储的对象，若不存在则返回null
	// */
	// <BEAN> BEAN get(String key, Class<BEAN> clazz);

	/**
	 * 返回多个key对应的数据.
	 * 
	 * @param keys
	 * @return 返回存储对象的List
	 */
	List<String> mget(String[] keys);

	// /**
	// * 返回多个key对应类型的对象.
	// *
	// * @param keys
	// * @param clazz
	// * @return 返回存储的对象的List
	// */
	// <BEAN> List<BEAN> mget(String[] keys, Class<BEAN> clazz);

	// /**
	// * 添加缓存对象.
	// * @param key
	// * @param obj
	// * @return 添加成功则返回true
	// */
	// boolean putObject(String key, Object obj);

	// /**
	// * 添加缓存对象，并设置有效时间.
	// * @param key
	// * @param obj
	// * @param seconds
	// * @return 添加成功返回true
	// */
	// boolean putObject(String key, Object obj, int seconds);
	//
	// /**
	// * 根据key获取缓存对象.
	// * @param key
	// * @return 存储的对象
	// */
	// Object getObject(String key);

	/**
	 * 清除所有缓存数据.
	 * 
	 * @return 成功则返回true
	 */
	boolean flushAll();

	/**
	 * 将key对应的数据加1.
	 * 
	 * @param key
	 * @return 成功则返回加1后的数据，否则返回-1
	 */
	long incr(String key);

	/**
	 * 初始化计数器并增加增量.
	 * 
	 * @param key
	 *            计数器的key
	 * @param num
	 *            增量
	 * @return 计数器的值
	 */
	long addOrIncr(String key, long num);

	/**
	 * 根据key返回对应的int值.
	 * 
	 * @param key
	 * @return 存储的int值
	 */
	int getInt(String key);

	/**
	 * 添加缓存，若已存在缓存数据则添加失败.
	 * 
	 * @param key
	 * @param value
	 * @return 添加成功则返回true
	 */
	boolean add(String key, String value);

	/**
	 * 添加缓存，并设置有效时间，若已存在缓存数据则添加失败.
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 * @return 添加成功则返回true
	 */
	boolean add(String key, String value, int seconds);

}
