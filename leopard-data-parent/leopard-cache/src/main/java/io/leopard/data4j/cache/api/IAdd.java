package io.leopard.data4j.cache.api;

/**
 * 记录添加接口.
 * 
 * @author 阿海
 * 
 * @param <BEAN>
 *            参数类型:如User、Article、Comment等
 * 
 */
public interface IAdd<BEAN> {

	/**
	 * 添加记录接口.
	 * 
	 * @param bean
	 *            参数类型:如User、Article、Comment等
	 * @return 添加成功返回true，记录已存在不想抛异常可返回false(如使用insert ignore into)，其他情况抛异常.
	 */
	boolean add(BEAN bean);

}
