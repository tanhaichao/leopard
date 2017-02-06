package io.leopard.jdbc;

import io.leopard.jdbc.MysqlDsnDataSource;

import org.junit.Assert;
import org.junit.Test;

public class MysqlDsnDataSourceTest {

	@Test
	public void MysqlDsnDataSource() {
		MysqlDsnDataSource dataSource = new MysqlDsnDataSource();

		dataSource.setUrl("jdbc:mysql://127.0.0.2:3306/leopard");
		dataSource.init();

		Assert.assertEquals("127.0.0.2", dataSource.getHost());
		Assert.assertEquals("leopard", dataSource.getDatabase());
	}

	@Test
	public void parseUrl() {
		MysqlDsnDataSource dataSource = new MysqlDsnDataSource();
		try {
			dataSource.parseUrl("url");
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}

	}

}