package io.leopard.jdbc.oracle;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.oracle.model.Column;
import io.leopard.jdbc.oracle.model.UserTable;

public class OracleManagerImpl implements OracleManager {

	private Jdbc jdbc;

	public OracleManagerImpl(Jdbc jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public List<UserTable> listUserTables() {
		List<UserTable> tableList = jdbc.queryForList("select * from user_tables", UserTable.class);
		List<UserTable.Comment> commentList = jdbc.queryForList("select * from user_tab_comments", UserTable.Comment.class);
		Map<String, UserTable.Comment> commentMap = new LinkedHashMap<>();
		for (UserTable.Comment comment : commentList) {
			String tableName = comment.getTableName();
			commentMap.put(tableName, comment);
		}
		for (UserTable table : tableList) {
			UserTable.Comment comment = commentMap.get(table.getTableName());
			table.setTableType(comment.getTableType());
			table.setComments(comment.getComments());
		}
		return tableList;
	}

	@Override
	public List<Column> listColumns(String tableName) {
		String sql = "select * from user_tab_columns where Table_Name=?";
		return jdbc.queryForList(sql, Column.class, tableName);
	}
}
