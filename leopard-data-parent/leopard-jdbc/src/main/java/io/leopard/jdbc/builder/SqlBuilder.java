package io.leopard.jdbc.builder;

import io.leopard.jdbc.StatementParameter;

public interface SqlBuilder {

	/**
	 * 获取SQL语句.
	 * 
	 * @return
	 */
	String getSql();

	/**
	 * 获取参数
	 * 
	 * @return
	 */
	StatementParameter getParam();
}
