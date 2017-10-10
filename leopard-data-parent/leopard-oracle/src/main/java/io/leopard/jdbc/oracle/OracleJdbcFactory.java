package io.leopard.jdbc.oracle;

import javax.sql.DataSource;

import io.leopard.jdbc.JdbcMysqlImpl;

public class OracleJdbcFactory {

	public static JdbcMysqlImpl creaeJdbcOracleImpl(String host, String database, String user, String password) {
		String jdbcUrl = "jdbc:oracle:thin:@" + host + ":1521:" + database;// "?useUnicode=true&characterEncoding=UTF8";
		System.err.println(jdbcUrl);
		DataSource dataSource = OracleProxyDataSource.createDataSource("oracle.jdbc.driver.OracleDriver", jdbcUrl, user, password, 5);

		JdbcMysqlImpl jdbcMysqlImpl = new JdbcMysqlImpl();
		jdbcMysqlImpl.setDataSource(dataSource);
		return jdbcMysqlImpl;
	}

}
