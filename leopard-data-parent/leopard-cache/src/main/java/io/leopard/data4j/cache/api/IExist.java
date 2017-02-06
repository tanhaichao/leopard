package io.leopard.data4j.cache.api;

/**
 * 判断记录是否存在.
 * 
 * @author 阿海
 * 
 */
public interface IExist<KEYTYPE> {

	/**
	 * 判断记录是否存在(不包含已删除的记录).
	 * 
	 * @param key
	 *            主键
	 * 
	 * @return 如果记录存在则返回true，已删除或不存在的记录则返回false，出错抛异常.
	 */
	boolean exist(KEYTYPE key);

}
