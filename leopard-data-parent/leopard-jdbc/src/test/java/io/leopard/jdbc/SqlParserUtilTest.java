package io.leopard.jdbc;

import io.leopard.jdbc.SqlParserUtil;
import io.leopard.jdbc.StatementParameter;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class SqlParserUtilTest {

	public static class User {
		private String username;
		private String nickname;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

	}

	@Test
	public void SqlParserUtil() {
		new SqlParserUtil();
	}

	@Test
	public void toUpdateParameter() {
		String sql = "update user set nickname=? where username=?;";
		User user = new User();
		user.setNickname("ahai");
		user.setUsername("hctan");
		StatementParameter param = SqlParserUtil.toUpdateParameter(sql, user);
		Assert.assertEquals(2, param.size());
		Assert.assertEquals("ahai", param.getString(0));
		Assert.assertEquals("hctan", param.getString(1));
	}

	@Test
	public void toInsertParameter() {
		String sql = "insert into user(username) values(?);";
		User user = new User();
		user.setUsername("hctan");
		StatementParameter param = SqlParserUtil.toInsertParameter(sql, user);
		Assert.assertEquals(1, param.size());
		Assert.assertEquals("hctan", param.getString(0));
	}

	@Test
	public void toPropertyName() {
		Assert.assertEquals("gameId", SqlParserUtil.toPropertyName("game_id"));
	}

	@Test
	public void toStatementParameter() {

	}

	@Test
	public void setValue() {
		StatementParameter param = new StatementParameter();
		{
			SqlParserUtil.setValue(param, int.class, 1);
			Assert.assertEquals(1, param.getInt(0));
		}
		{
			SqlParserUtil.setValue(param, long.class, 2L);
			Assert.assertEquals(2L, param.getLong(1));
		}
		{
			SqlParserUtil.setValue(param, Float.class, 3F);
			Assert.assertEquals(3F, param.getFloat(2), 0);
		}
		{
			SqlParserUtil.setValue(param, double.class, 4D);
			Assert.assertEquals(4D, param.getDouble(3), 0);
		}
		{
			SqlParserUtil.setValue(param, boolean.class, true);
			Assert.assertTrue(param.getBool(4));
		}
		{
			SqlParserUtil.setValue(param, Date.class, new Date(1));
			Assert.assertEquals(1L, param.getDate(5).getTime());
		}
		try {
			SqlParserUtil.setValue(param, Object.class, new Object());
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
	}

}