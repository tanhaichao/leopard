package io.leopard.jdbc;

import org.junit.Test;

public class GroupCountSqlParserTest {

	@Test
	public void parseGroupSql() {
		GroupCountSqlParser.parseGroupSql("select * from user group by uid");
	}

}