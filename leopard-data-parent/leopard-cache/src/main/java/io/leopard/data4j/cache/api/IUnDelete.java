package io.leopard.data4j.cache.api;

import java.util.Date;

/**
 * 恢复删除接口.
 * 
 * @author 阿海
 * 
 * @param <BEAN>
 *            返回值类型:如User、Article、Comment等
 * 
 * @param <KEYTYPE>
 *            主键类型:如Integer、Long、String等
 */
public interface IUnDelete<BEAN, KEYTYPE> extends IDelete<BEAN, KEYTYPE> {

	/**
	 * 恢复删除接口.
	 * 
	 * @param key
	 *            主键
	 * @param username
	 *            操作用户名
	 * @param lmodify
	 *            操作时间.
	 * @return 恢复成功返回true，没有记录更新返回false，出错抛异常.
	 */
	boolean undelete(KEYTYPE key, String username, Date lmodify);

}
