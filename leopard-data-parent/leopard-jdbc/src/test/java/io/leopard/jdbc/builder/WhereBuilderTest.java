package io.leopard.jdbc.builder;

import io.leopard.jdbc.builder.WhereBuilder;

import org.junit.Assert;
import org.junit.Test;

public class WhereBuilderTest {

	@Test
	public void getSql() {
		WhereBuilder builder = new WhereBuilder();
		builder.setString("username", "hctan");
		String sql = builder.getSql();
		System.out.println("sql:" + sql);
		Assert.assertEquals("username=?", sql);
	}

	@Test
	public void getSql2() {
		WhereBuilder builder = new WhereBuilder();
		builder.setString("username", "hctan");
		builder.setInt("num", 1);
		String sql = builder.getSql();
		System.out.println("sql:" + sql);
		Assert.assertEquals("username=? and num=?", sql);
	}

	@Test
	public void getSql3() {
		try {
			new WhereBuilder().getSql();
			Assert.fail("怎么没有抛异常?");
		}
		catch (NullPointerException e) {

		}
	}
}