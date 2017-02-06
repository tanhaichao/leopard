package io.leopard.sql;

import java.beans.PropertyVetoException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3p0DataSource implements PoolDataSource {

	ComboPooledDataSource dataSource = new ComboPooledDataSource();

	// dataSource.setJdbcUrl(jdbcUrl);
	// dataSource.setUser(user);
	// dataSource.setPassword(password);
	// dataSource.setTestConnectionOnCheckout(false);
	// dataSource.setInitialPoolSize(1);
	// dataSource.setMinPoolSize(1);
	// dataSource.setMaxPoolSize(maxPoolSize);
	// dataSource.setAcquireIncrement(1);
	// dataSource.setAcquireRetryAttempts(1);
	// dataSource.setMaxIdleTime(7200);
	// dataSource.setMaxStatements(0);
	public void setDriverClass(String driverClass) throws PropertyVetoException {
		this.dataSource.setDriverClass(driverClass);
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.dataSource.setJdbcUrl(jdbcUrl);
	}

	public void setMaxIdleTime(int maxIdleTime) {
		dataSource.setMaxIdleTime(maxIdleTime);

	}

	public void setMaxPoolSize(int maxPoolSize) {
		dataSource.setMaxPoolSize(maxPoolSize);
	}

	public void setUser(String user) {
		this.dataSource.setUser(user);
	}

	public void setPassword(String password) {
		dataSource.setPassword(password);
	}

	public void setTestConnectionOnCheckout(boolean testConnectionOnCheckout) {
		dataSource.setTestConnectionOnCheckout(testConnectionOnCheckout);
	}

	public void setInitialPoolSize(int initialPoolSize) {
		dataSource.setInitialPoolSize(initialPoolSize);
	}

	public void setMinPoolSize(int minPoolSize) {
		dataSource.setMinPoolSize(minPoolSize);
	}

	public void setAcquireIncrement(int acquireIncrement) {
		dataSource.setAcquireIncrement(acquireIncrement);
	}

	public void setAcquireRetryAttempts(int acquireRetryAttempts) {
		dataSource.setAcquireRetryAttempts(acquireRetryAttempts);
	}

	public void setMaxStatements(int maxStatements) {
		dataSource.setMaxStatements(maxStatements);
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
		throw new SQLFeatureNotSupportedException();
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public Connection getConnection() throws SQLException {
		return this.dataSource.getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return this.dataSource.getConnection(username, password);
	}

}
