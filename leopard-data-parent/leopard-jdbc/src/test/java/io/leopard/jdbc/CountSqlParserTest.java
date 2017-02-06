package io.leopard.jdbc;

import org.junit.Test;

public class CountSqlParserTest {

	@Test
	public void parseLimitParamCount() {
		CountSqlParser parser = new CountSqlParser("where uid=? and user=?", null);
		// int count = parser.parseLimitParamCount("where uid=? and user=?");
		// System.out.println("count:" + count);
	}

	@Test
	public void getCountSql() {
		StatementParameter param = new StatementParameter();
		param.setLong(1L);
		param.setString("user");
		param.setInt(0);
		param.setInt(10);
		CountSqlParser parser = new CountSqlParser("select * from user where uid=? and user=? order by postime desc limit ?,?", param);

		System.out.println("sql:" + parser.getCountSql());
		System.out.println("start:" + parser.getStart());
		System.out.println("size:" + parser.getSize());
	}

	@Test
	public void CountSqlParser() {

	}

}