package io.leopard.jdbc;

import io.leopard.jdbc.JdbcDaoSupport;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcDaoSupportTest {
	JdbcDaoSupport jdbcDao = new JdbcDaoSupport();

	@Test
	public void JdbcDaoSupport() {

	}

	@Test
	public void setDataSource() {
		DataSource dataSource = Mockito.mock(DataSource.class);
		jdbcDao.setDataSource(dataSource);

		Assert.assertNotNull(jdbcDao.getDataSource());
	}

	@Test
	public void setJdbcTemplate() {
		JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);
		jdbcDao.setJdbcTemplate(jdbcTemplate);
		Assert.assertNotNull(jdbcDao.getJdbcTemplate());

		jdbcDao.getExceptionTranslator();
	}

	@Test
	public void getConnection() {
		DataSource dataSource = Mockito.mock(DataSource.class);
		jdbcDao.setDataSource(dataSource);
		jdbcDao.getConnection();
	}

	@Test
	public void releaseConnection() {
		DataSource dataSource = Mockito.mock(DataSource.class);
		jdbcDao.setDataSource(dataSource);
		Connection conn = Mockito.mock(Connection.class);
		jdbcDao.releaseConnection(conn);
	}
}