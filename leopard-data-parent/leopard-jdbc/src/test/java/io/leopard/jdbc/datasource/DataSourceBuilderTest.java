package io.leopard.jdbc.datasource;

import java.beans.PropertyVetoException;

import org.junit.Assert;
import org.junit.Test;

public class DataSourceBuilderTest {

	@Test
	public void getJdbcUrl() {
		String url = DataSourceBuilder.getJdbcUrl("host", 3306, "leopard");
		// System.out.println("url:" + url);
		Assert.assertEquals("jdbc:mysql://host:3306/leopard?useUnicode=true&characterEncoding=UTF8", url);
	}

	@Test
	public void createDataSource() throws PropertyVetoException {
		String jdbcUrl = DataSourceBuilder.getJdbcUrl("host", 3306, "leopard");
		new DataSourceBuilder().createDataSource("org.gjt.mm.mysql.Driver", jdbcUrl, "user", "password", 10, 60);
	}

	@Test
	public void parseUrl() {
		try {
			DataSourceBuilder.parseUrl("url");
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}

	}
}