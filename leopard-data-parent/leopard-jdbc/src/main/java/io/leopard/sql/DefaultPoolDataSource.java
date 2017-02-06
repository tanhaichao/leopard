package io.leopard.sql;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class DefaultPoolDataSource implements PoolDataSource {

	private PoolDataSource dataSource;

	public DefaultPoolDataSource() {
	}

	@Override
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return dataSource.getConnection(username, password);
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return dataSource.getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		dataSource.setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		dataSource.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return dataSource.getLoginTimeout();
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new SQLFeatureNotSupportedException();
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return this.dataSource.unwrap(iface);
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return this.dataSource.isWrapperFor(iface);
	}

	@Override
	public void setJdbcUrl(String jdbcUrl) {
		this.dataSource.setJdbcUrl(jdbcUrl);
	}

	@Override
	public void setMaxIdleTime(int maxIdleTime) {
		this.dataSource.setMaxIdleTime(maxIdleTime);
	}

	@Override
	public void setMaxPoolSize(int maxPoolSize) {
		dataSource.setMaxPoolSize(maxPoolSize);
	}

	@Override
	public void setUser(String user) {
		dataSource.setUser(user);
	}

	@Override
	public void setPassword(String password) {
		dataSource.setPassword(password);
	}

	@Override
	public void setTestConnectionOnCheckout(boolean testConnectionOnCheckout) {
		dataSource.setTestConnectionOnCheckout(testConnectionOnCheckout);
	}

	@Override
	public void setInitialPoolSize(int initialPoolSize) {
		dataSource.setInitialPoolSize(initialPoolSize);
	}

	@Override
	public void setMinPoolSize(int minPoolSize) {
		dataSource.setMinPoolSize(minPoolSize);
	}

	@Override
	public void setAcquireIncrement(int acquireIncrement) {
		dataSource.setAcquireIncrement(acquireIncrement);
	}

	@Override
	public void setAcquireRetryAttempts(int acquireRetryAttempts) {
		dataSource.setAcquireRetryAttempts(acquireRetryAttempts);
	}

	@Override
	public void setMaxStatements(int maxStatements) {
		dataSource.setMaxStatements(maxStatements);
	}

}
