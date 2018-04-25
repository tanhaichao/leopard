package io.leopard.jdbc.datasource;

import org.junit.Assert;
import org.junit.Test;

import io.leopard.jdbc.datasource.MysqlDsnDataSource;

public class MysqlDsnDataSourceTest {

	@Test
	public void MysqlDsnDataSource() {
		MysqlDsnDataSource dataSource = new MysqlDsnDataSource();

		dataSource.setUrl("jdbc:mysql://127.0.0.2:3306/leopard");
		dataSource.init();

		Assert.assertEquals("127.0.0.2", dataSource.getHost());
		Assert.assertEquals("leopard", dataSource.getDatabase());
	}

}