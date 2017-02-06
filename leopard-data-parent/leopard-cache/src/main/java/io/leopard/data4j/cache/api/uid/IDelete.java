package io.leopard.data4j.cache.api.uid;

import io.leopard.data4j.cache.api.IGet;

import java.util.Date;

/**
 * 删除接口.
 * 
 * @author 阿海
 * 
 * @param <BEAN>
 *            IGet接口返回值类型:如User、Article、Comment等
 * 
 * @param <KEYTYPE>
 *            主键类型:如Integer、Long、String等
 * 
 */
public interface IDelete<BEAN, KEYTYPE> extends IGet<BEAN, KEYTYPE> {

	/**
	 * 删除记录.(如记录有del字段，则使用标记删除)
	 * 
	 * @param key
	 *            主键
	 * 
	 * @param opuid
	 *            操作人uid
	 * 
	 * @param lmodify
	 *            操作时间
	 * @return 删除成功返回true，没有对应的记录返回false，出错抛异常.
	 */
	boolean delete(KEYTYPE key, long opuid, Date lmodify);

}
