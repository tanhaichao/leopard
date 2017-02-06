package io.leopard.data4j.cache.api;

/**
 * 更新数据接口.
 * @author Administrator
 *
 * @param <BEAN>  返回值类型:如User、Article、Comment等
 * @param <KEYTYPE> 主键类型：如Integer等
 */
public interface IUpdate<BEAN, KEYTYPE> extends IDelete<BEAN, KEYTYPE> {
	
	/**
	 * 更新数据.
	 * @param bean 需要更新的数据model
	 * @return 更新成功返回true，没有记录更新返回false，出错抛异常.
	 */
	boolean update(BEAN bean);
}
