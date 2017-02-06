package io.leopard.data4j.cache.api;

/**
 * 字段值增减接口.
 * 
 * @author 阿海
 * 
 */
public interface IIncr<KEYTYPE> {
	/**
	 * 增加或减少字段值.
	 * 
	 * @param key
	 *            主键
	 * @param count
	 * 
	 * @return 更新成功返回true，没有数据更新返回false(如记录不存在)
	 */
	Long incr(KEYTYPE key, int count);

}
