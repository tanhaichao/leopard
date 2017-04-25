package io.leopard.jdbc.logger;

/**
 * 日志
 * 
 * @author 谭海潮
 *
 */
public interface JdbcLogger {

	/**
	 * 更新日志
	 * 
	 * @param updatedCount 更新数量
	 * @param sql
	 * @param args 参数
	 */
	void update(int updatedCount, String sql, Object[] args);
}
