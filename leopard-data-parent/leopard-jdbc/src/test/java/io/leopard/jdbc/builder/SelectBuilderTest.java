package io.leopard.jdbc.builder;

import io.leopard.jdbc.builder.SelectBuilder;

import org.junit.Assert;
import org.junit.Test;

public class SelectBuilderTest {

	@Test
	public void getSql() {
		SelectBuilder builder = new SelectBuilder("select * from table order by posttime desc;");
		builder.setString("username", "hctan");
		builder.setBool("del", true);
		String sql = builder.getSql();
		System.out.println("sql:" + sql);
		Assert.assertEquals("select * from table  where username=? and del=? order by posttime desc;", sql);
	}

	@Test
	public void getSql2() {
		SelectBuilder builder = new SelectBuilder("select * from table order by posttime desc limit ?;", 10);
		builder.setString("username", "hctan");
		builder.setLong("yyuid", 1L);
		String sql = builder.getSql();
		Assert.assertEquals(10, builder.getParam().getInt(2));
		System.out.println("sql:" + sql);
		Assert.assertEquals("select * from table  where username=? and yyuid=? order by posttime desc limit ?;", sql);
	}

	// @Test
	// public void getSql3() {
	// SelectBuilder builder = new
	// SelectBuilder("select * from table order by posttime desc limit ?;", 10);
	// builder.setString("username", "hctan");
	// builder.setDouble("score", 1.1D);
	// builder.setTimeRange("posttime", DateUtil.toDate("2013-01-01 00:00:00"),
	// DateUtil.toDate("2013-01-01 10:00:00"));
	// String sql = builder.getSql();
	// System.out.println("sql:" + sql);
	// Assert.assertEquals("select * from table  where username=? and score=? and posttime>=? and posttime<=? order by posttime desc limit ?;",
	// sql);
	// }

	// @Test
	// public void getSql4() {
	// SelectBuilder builder = new
	// SelectBuilder("select * from table order by posttime desc limit ?;", 10);
	// builder.setString("username", "hctan");
	// builder.setDate("posttime", new Date());
	// builder.setStartTime("posttime", DateUtil.toDate("2013-01-01 00:00:00"));
	// String sql = builder.getSql();
	// System.out.println("sql:" + sql);
	// Assert.assertEquals("select * from table  where username=? and posttime=? and posttime>=? order by posttime desc limit ?;",
	// sql);
	// }

	// @Test
	// public void getSql5() {
	// SelectBuilder builder = new
	// SelectBuilder("select * from table order by posttime desc limit ?;", 10);
	// builder.setString("username", "hctan");
	// builder.setTimestamp("posttime", new
	// Timestamp(System.currentTimeMillis()));
	// builder.setEndTime("posttime", DateUtil.toDate("2013-01-01 00:00:00"));
	// String sql = builder.getSql();
	// System.out.println("sql:" + sql);
	// Assert.assertEquals("select * from table  where username=? and posttime=? and posttime<=? order by posttime desc limit ?;",
	// sql);
	// }
}