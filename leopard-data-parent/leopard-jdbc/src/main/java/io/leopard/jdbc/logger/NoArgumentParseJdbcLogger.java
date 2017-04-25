package io.leopard.jdbc.logger;

/**
 * 不解析参数
 * 
 * @author 谭海潮
 *
 */
public class NoArgumentParseJdbcLogger implements JdbcLogger {

	@Override
	public void update(int updatedCount, String sql, Object[] args) {
		// TODO Auto-generated method stub

	}

}
