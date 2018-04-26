package io.leopard.jdbc;

import io.leopard.jdbc.JdbcMysqlImpl;
import io.leopard.jdbc.datasource.MysqlDsnDataSource;

public class JdbcFactory {

	public static JdbcMysqlImpl creaeJdbcMysqlImpl(String host, String database, String user, String password) {
		return creaeJdbcMysqlImpl(host, database, user, password, 0);
	}

	public static JdbcMysqlImpl creaeJdbcMysqlImpl(String host, String database, String user, String password, int idleConnectionTestPeriod) {
		return creaeJdbcMysqlImpl(host, 3306, database, user, password, idleConnectionTestPeriod);
	}

	public static JdbcMysqlImpl creaeJdbcMysqlImpl(String host, int port, String database, String user, String password) {
		return creaeJdbcMysqlImpl(host, port, database, user, password, 0);
	}

	public static JdbcMysqlImpl creaeJdbcMysqlImpl(String host, int port, String database, String user, String password, int idleConnectionTestPeriod) {
		String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useUnicode=true&characterEncoding=UTF8";
		MysqlDsnDataSource dataSource = new MysqlDsnDataSource();
		dataSource.setMaxPoolSize(15);
		dataSource.setUser(user);
		dataSource.setPassword(password);
		dataSource.setUrl(url);
		dataSource.setIdleConnectionTestPeriod(idleConnectionTestPeriod);
		try {
			dataSource.init();
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		JdbcMysqlImpl jdbcMysqlImpl = new JdbcMysqlImpl();
		jdbcMysqlImpl.setDataSource(dataSource);
		return jdbcMysqlImpl;
	}

	// public static JdbcMysqlImpl createJdbcH2Impl(String database) {
	// ComboPooledDataSource dataSource = new ComboPooledDataSource();
	// String jdbcUrl = "jdbc:h2:" + LogDirLeiImpl.getLogDir() + "/h2/" + database;
	// jdbcUrl += ";lock_mode=0";
	// // jdbcUrl += ";MVCC=true";
	// dataSource.setJdbcUrl(jdbcUrl);// MVCC=true
	// try {
	// dataSource.setDriverClass("org.h2.Driver");
	// }
	// catch (PropertyVetoException e) {
	// throw new RuntimeException(e.getMessage(), e);
	// }
	// dataSource.setUser("sa");
	// dataSource.setPassword("");
	//
	// {
	// dataSource.setInitialPoolSize(1);
	// dataSource.setMinPoolSize(1);
	// dataSource.setMaxPoolSize(3);
	// dataSource.setAcquireIncrement(1);
	// // dataSource.setAcquireRetryAttempts(1);
	// // dataSource.setMaxIdleTime(7200);
	// // dataSource.setMaxStatements(0);
	// }
	//
	// if (true) {
	// try {
	// dataSource.getConnection();
	// }
	// catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }
	// JdbcMysqlImpl jdbc = new JdbcMysqlImpl();
	// jdbc.setDataSource(dataSource);
	// return jdbc;
	// }
}
