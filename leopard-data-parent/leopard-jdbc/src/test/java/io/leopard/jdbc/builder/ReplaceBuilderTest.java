package io.leopard.jdbc.builder;

import io.leopard.jdbc.builder.ReplaceBuilder;

import org.junit.Assert;
import org.junit.Test;

public class ReplaceBuilderTest {

	@Test
	public void getSql() {
		ReplaceBuilder builder = new ReplaceBuilder("table");
		builder.setString("username", "hctan");
		builder.setString("nickname", "ahai");
		String sql = builder.getSql();
		System.out.println("sql:" + sql);
		Assert.assertEquals("REPLACE INTO table(username, nickname) values(?, ?);", sql);

		try {
			new ReplaceBuilder("table").getSql();
			Assert.fail("怎么没有抛异常?");
		}
		catch (NullPointerException e) {

		}
	}

}