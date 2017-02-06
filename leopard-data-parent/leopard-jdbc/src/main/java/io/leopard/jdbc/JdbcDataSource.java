package io.leopard.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JdbcDataSource implements DataSource {

	protected Log logger = LogFactory.getLog(JdbcDataSource.class);

	// <bean id="masterDataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource" destroy-method="close">
	// <property name="driverClass" value="${jdbc.driverClassName}" />
	// <property name="jdbcUrl" value="jdbc:mysql://${jdbc.host}:3306/notice?useUnicode=true&amp;characterEncoding=UTF8" />
	// <property name="user" value="${jdbc.username}" />
	// <property name="password" value="${jdbc.password}" />
	// <property name="testConnectionOnCheckout" value="false" />
	// <property name="initialPoolSize" value="2" />
	// <property name="minPoolSize" value="2" />
	// <property name="maxPoolSize" value="15" />
	// <property name="acquireIncrement" value="1" />
	// <property name="acquireRetryAttempts" value="1" />
	// <property name="maxIdleTime" value="7200" />
	// <property name="maxStatements" value="0" />
	// </bean>

	protected DataSource dataSource;

	protected String host;
	protected String database;
	protected String user;
	protected String password;
	protected String driverClass;
	protected int port = 3306;
	protected int maxPoolSize = 15;

	public void setHost(String host) {
		// System.out.println("JdbcDataSourceImpl setHost:" + host);
		// if (host.indexOf("${") != -1) {
		// throw new IllegalArgumentException("怎么spring占位符[" + host + "]没有被解析?");
		// }
		this.host = host;
	}

	public void setDatabase(String database) {
		// System.out.println("setDatabase:" + database);
		this.database = database;
	}

	public void setUser(String user) {
		// System.out.println("setUser:" + user);
		this.user = user;
	}

	public void setPassword(String password) {
		// System.out.println("setPassword:" + password);
		this.password = password;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public String getHost() {
		return host;
	}

	public String getDatabase() {
		return database;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public int getPort() {
		return port;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
		// System.err.println("driverClass:" + driverClass);
	}

	public void init() {
		// String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useUnicode=true&characterEncoding=UTF8";
		// ComboPooledDataSource dataSource = new ComboPooledDataSource();
		// dataSource.setDriverClass("org.gjt.mm.mysql.Driver");
		// dataSource.setJdbcUrl(jdbcUrl);
		// dataSource.setUser(user);
		// dataSource.setPassword(password);
		// dataSource.setTestConnectionOnCheckout(false);
		// dataSource.setInitialPoolSize(2);
		// dataSource.setMinPoolSize(2);
		// dataSource.setMaxPoolSize(maxPoolSize);
		// dataSource.setAcquireIncrement(1);
		// dataSource.setAcquireRetryAttempts(1);
		// dataSource.setMaxIdleTime(7200);
		// dataSource.setMaxStatements(0);
		String jdbcUrl = ProxyDataSource.getJdbcUrl(host, port, database);
		this.dataSource = ProxyDataSource.createDataSource(driverClass, jdbcUrl, user, password, maxPoolSize);
	}

	public void destroy() {
		// System.out.println("JdbcDataSourceImpl destroy");
		if (dataSource != null) {
			if (dataSource instanceof ProxyDataSource) {
				((ProxyDataSource) dataSource).close();
			}
			else {
				throw new RuntimeException("未知DataSource类型[" + dataSource.getClass().getName() + "].");
			}
		}
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

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return dataSource.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return dataSource.isWrapperFor(iface);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return dataSource.getConnection(username, password);
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// throw new NotImplementedException();
		throw new SQLFeatureNotSupportedException("Not Implemented.");
	}
}
