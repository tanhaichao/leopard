package io.leopard.jdbc.builder;

import io.leopard.jdbc.builder.UpdateBuilder;

import org.junit.Assert;
import org.junit.Test;

public class UpdateBuilderTest {

	@Test
	public void getSql() {
		UpdateBuilder builder = new UpdateBuilder("table");
		builder.setFloat("money", 1.1f);
		builder.where.setString("username", "hctan");
		String sql = builder.getSql();

		Assert.assertEquals("hctan", builder.getParam().getString(1));
		System.out.println("sql:" + sql);
		Assert.assertEquals("UPDATE table SET money=? WHERE username=?;", sql);
	}

	@Test
	public void getSql3() {
		try {
			new UpdateBuilder("table").getSql();
			Assert.fail("怎么没有抛异常?");
		}
		catch (NullPointerException e) {

		}
	}
}