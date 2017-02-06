package io.leopard.jdbc;

import java.beans.PropertyVetoException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * DataSource代理，为了出错时输出Jdbc配置信息，方便查找原因.
 * 
 * @author 阿海
 * 
 */
public class ProxyDataSource implements DataSource {

	protected Log logger = LogFactory.getLog(ProxyDataSource.class);

	protected JdbcConnectionListener jdbcConnectionListener;

	private ComboPooledDataSource dataSource;

	public ProxyDataSource(ComboPooledDataSource dataSource) {
		this.dataSource = dataSource;
		try {
			this.initJdbcConnectionListener();
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	protected void initJdbcConnectionListener() {
		String host;
		int port;
		int maxPoolSize = this.dataSource.getMaxPoolSize();
		int timeout = 0;
		String database;

		String jdbcUrl = this.dataSource.getJdbcUrl();
		// jdbc:mysql://${jdbc.host}:3306/notice?useUnicode=true
		String regex = "jdbc:mysql://(.*?):([0-9]+)/([A-Za-z0-9_]+)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(jdbcUrl);
		if (m.find()) {
			host = m.group(1);
			port = Integer.parseInt(m.group(2));
			database = m.group(3);
		}
		else {
			throw new RuntimeException("解析jdbcUrl出错[" + jdbcUrl + "].");
		}

		String className = System.getProperty(JdbcConnectionListener.class.getName());
		if (StringUtils.isEmpty(className)) {
			return;
		}
		Class<?> clazz;
		try {
			clazz = Class.forName(className);
		}
		catch (ClassNotFoundException e) {
			return;
		}

		try {
			jdbcConnectionListener = (JdbcConnectionListener) clazz.newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		jdbcConnectionListener.setPoolConfig(host, port, timeout, maxPoolSize, database);
	}

	public void close() {
		this.dataSource.close();
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return dataSource.getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		this.dataSource.setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		this.dataSource.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return this.dataSource.getLoginTimeout();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// throw new NotImplementedException();
		throw new SQLException("Not Implemented.");
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return this.dataSource.getConnection(username, password);
	}

	@Override
	public Connection getConnection() throws SQLException {
		long startTime = System.nanoTime();
		Connection conn = null;
		try {
			conn = this.dataSource.getConnection();
			// System.err.println("getConnection conn:" + conn);
		}
		catch (SQLException e) {
			this.printInfo();
			throw e;
		}
		finally {
			if (jdbcConnectionListener != null) {
				jdbcConnectionListener.open(conn, startTime);
			}
		}

		return new ProxyConnection(conn) {
			@Override
			public void close() throws SQLException {
				try {
					super.close();
				}
				finally {
					if (jdbcConnectionListener != null) {
						jdbcConnectionListener.close(this.getConn());
					}
				}
			}
		};
	}

	protected String parseHost(String jdbcUrl) {
		String regex = "jdbc:mysql://(.*?):";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(jdbcUrl);
		if (m.find()) {
			return m.group(1);
		}
		throw new RuntimeException("解析host出错[" + jdbcUrl + "].");
	}

	protected void printInfo() {
		String jdbcUrl = this.dataSource.getJdbcUrl();
		String user = this.dataSource.getUser();
		String password = this.dataSource.getPassword();

		String host = this.parseHost(jdbcUrl);
		String ip = getIp(host);
		String message = "连接MySQL出错，配置信息,host:" + host + " ip:" + ip + " jdbcUrl:" + jdbcUrl + " user:" + user + " password:" + password;

		System.err.println(message);
	}

	public static String getIp(String host) {
		InetAddress inetAddress;
		try {
			inetAddress = InetAddress.getByName(host);
		}
		catch (UnknownHostException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return inetAddress.getHostAddress();
	}

	public static String getJdbcUrl(String host, int port, String database) {
		String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useUnicode=true&characterEncoding=UTF8";// UTF8,utf8mb4
		return jdbcUrl;
	}

	public static ProxyDataSource createDataSource(String driverClass, String jdbcUrl, String user, String password, int maxPoolSize) {
		if (StringUtils.isEmpty(driverClass)) {
			// System.err.println("驱动程序没有设置.");
			driverClass = "org.gjt.mm.mysql.Driver";
		}

		System.err.println("createDataSource jdbcUrl:" + jdbcUrl);
		// ComboPooledDataSource dataSource = new ComboPooledDataSource();
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			// dataSource.setDriverClass("org.mariadb.jdbc.Driver");
			// dataSource.setDriverClass("org.gjt.mm.mysql.Driver");
			dataSource.setDriverClass(driverClass);
		}
		catch (PropertyVetoException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setUser(user);
		dataSource.setPassword(password);
		dataSource.setTestConnectionOnCheckout(false);
		dataSource.setInitialPoolSize(1);
		dataSource.setMinPoolSize(1);
		dataSource.setMaxPoolSize(maxPoolSize);
		dataSource.setAcquireIncrement(1);
		dataSource.setAcquireRetryAttempts(1);
		dataSource.setMaxIdleTime(7200);
		dataSource.setMaxStatements(0);
		return new ProxyDataSource(dataSource);
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new SQLFeatureNotSupportedException("Not Implemented.");
	}

}
