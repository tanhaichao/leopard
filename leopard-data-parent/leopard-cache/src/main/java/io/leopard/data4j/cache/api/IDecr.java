package io.leopard.data4j.cache.api;

public interface IDecr<KEYTYPE> extends IIncr<KEYTYPE> {

	/**
	 * 增加或减少字段值.
	 * 
	 * @param key
	 *            主键
	 * @param count
	 * 
	 * @return 更新成功返回true，没有数据更新返回false(如记录不存在、或减到0时等)
	 */
	Long decr(KEYTYPE key, int count);

}
