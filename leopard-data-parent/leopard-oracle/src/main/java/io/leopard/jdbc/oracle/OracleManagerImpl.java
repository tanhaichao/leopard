package io.leopard.jdbc.oracle;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.oracle.model.UserTable;
import io.leopard.jdbc.oracle.model.UserTableComment;

public class OracleManagerImpl implements OracleManager {

	private Jdbc jdbc;

	public OracleManagerImpl(Jdbc jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public List<UserTableComment> listUserTableComments() {
		String sql = "select * from user_tab_comments";
		return jdbc.queryForList(sql, UserTableComment.class);
	}

	@Override
	public List<UserTable> listUserTables() {
		String sql = "select * from user_tables";
		return jdbc.queryForList(sql, UserTable.class);
	}

	@Override
	public List<UserTable> listUserTables2() {
		List<UserTable> tableList = this.listUserTables();
		List<UserTableComment> commentList = this.listUserTableComments();
		Map<String, UserTableComment> commentMap = new LinkedHashMap<>();
		for (UserTableComment comment : commentList) {
			String tableName = comment.getTableName();
			commentMap.put(tableName, comment);
		}
		for (UserTable table : tableList) {
			UserTableComment comment = commentMap.get(table.getTableName());
			String comments = comment.getComments();
			table.setComments(comments);
		}
		return tableList;
	}
}
