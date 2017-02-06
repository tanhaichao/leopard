package io.leopard.jdbc;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class JdbcDataSourceTest {

	JdbcDataSource dataSource = newInstance();

	protected JdbcDataSource newInstance() {
		DataSource source = Mockito.mock(DataSource.class);
		JdbcDataSource dataSource = new JdbcDataSource();
		// LeopardMockito.setProperty(dataSource, source);
		return dataSource;
	}

	@Test
	public void JdbcDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setHost("127.0.0.2");
		dataSource.setPort(3306);
		dataSource.setDatabase("leopard");
		dataSource.setUser("user");
		dataSource.setPassword("password");
		dataSource.setMaxPoolSize(10);
		// dataSource.init();

		Assert.assertEquals("127.0.0.2", dataSource.getHost());
		Assert.assertEquals(3306, dataSource.getPort());
		Assert.assertEquals("user", dataSource.getUser());
		Assert.assertEquals("leopard", dataSource.getDatabase());
		Assert.assertEquals("password", dataSource.getPassword());
		Assert.assertEquals(10, dataSource.getMaxPoolSize());
	}

	@Test
	public void getLogWriter() throws SQLException {
		dataSource.getLogWriter();
	}

	@Test
	public void setLogWriter() throws SQLException {
		dataSource.setLogWriter(null);
	}

	@Test
	public void setLoginTimeout() throws SQLException {
		dataSource.setLoginTimeout(1);
	}

	@Test
	public void getLoginTimeout() throws SQLException {
		dataSource.getLoginTimeout();
	}

	@Test
	public void unwrap() throws SQLException {
		dataSource.unwrap(String.class);
	}

	@Test
	public void isWrapperFor() throws SQLException {
		dataSource.isWrapperFor(String.class);
	}

	@Test
	public void getConnection() throws SQLException {
		dataSource.getConnection();
		dataSource.getConnection("username", "password");
	}

	@Test
	public void destroy() {
		// dataSource.destroy();
	}

}