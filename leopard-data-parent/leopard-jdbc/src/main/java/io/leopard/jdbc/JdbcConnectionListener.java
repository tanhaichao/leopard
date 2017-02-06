package io.leopard.jdbc;

import java.sql.Connection;

/**
 * Jdbc连接监听器.
 * 
 * @author ahai
 * 
 */
public interface JdbcConnectionListener {

	/**
	 * 设置连接池配置信息.
	 * 
	 * @param host
	 * @param port
	 * @param timeout
	 * @return
	 */
	void setPoolConfig(String host, int port, int timeout, int maxPoolSize, String database);

	/**
	 * 打开连接
	 * 
	 * @return
	 */
	void open(Connection connection, long startTime);

	/**
	 * 关闭连接.
	 * 
	 * @return
	 */
	void close(Connection connection);

	/**
	 * 中断连接.
	 * 
	 * @return
	 */
	void broken();
}
