package io.leopard.data4j.cache.api;

import java.util.Date;

/**
 * 更新状态字段.
 * 
 * @author 阿海
 * 
 * @param <BEAN>
 *            返回值类型:如User、Article、Comment等
 * 
 * @param <KEYTYPE>
 *            主键类型:如Integer、Long、String等
 */
public interface IStatus<BEAN, KEYTYPE> extends IUnDelete<BEAN, KEYTYPE>, IGetIncludeDeleted<BEAN, KEYTYPE>, IList<BEAN, KEYTYPE> {

	/**
	 * 更新状态字段.
	 * 
	 * @param key
	 *            主键
	 * @param status
	 *            状态值
	 * @param username
	 *            操作用户名
	 * @param lmodify
	 *            操作时间
	 * @param reason
	 *            操作理由
	 * @return 更新成功返回true，没有记录更新返回false，出错抛异常.
	 */
	boolean updateStatus(KEYTYPE key, int status, String username, Date lmodify, String reason);

}
