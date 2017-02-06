package io.leopard.jdbc;

import org.springframework.jdbc.BadSqlGrammarException;

public class CreateTableUtil {

	public static interface GetSql {
		String getSql();
	}

	public static void createTable(Jdbc jdbc, String tableName, GetSql getSql) {
		boolean tableExist = checkTableExist(jdbc, tableName);
		if (tableExist) {
			return;
		}
		jdbc.update(getSql.getSql());
	}

	/**
	 * 检查表结构是否已存在.
	 * 
	 * @return
	 */
	protected static boolean checkTableExist(Jdbc jdbc, String tableName) {
		String sql = "select 1 from " + tableName + " limit 1;";
		try {
			jdbc.queryForInt(sql);
			return true;
		}
		catch (BadSqlGrammarException e) {
			return false;
		}

	}

}
