package io.leopard.jdbc.builder;

import io.leopard.jdbc.builder.InsertBuilder;

import org.junit.Assert;
import org.junit.Test;

public class InsertBuilderTest {

	@Test
	public void getSql() {
		InsertBuilder builder = new InsertBuilder("table");
		builder.setString("username", "hctan");
		builder.setString("nickname", "ahai");
		String sql = builder.getSql();
		System.out.println("sql:" + sql);
		Assert.assertEquals("INSERT INTO table(username, nickname) values(?, ?);", sql);

		try {
			new InsertBuilder("table").getSql();
			Assert.fail("怎么没有抛异常?");
		}
		catch (NullPointerException e) {

		}
	}

}