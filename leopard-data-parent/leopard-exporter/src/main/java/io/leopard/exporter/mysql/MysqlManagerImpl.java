package io.leopard.exporter.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.oracle.model.Column;
import io.leopard.jdbc.oracle.model.UserTable;

public class MysqlManagerImpl implements MysqlManager {
	private Jdbc jdbc;

	public MysqlManagerImpl(Jdbc jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public List<UserTable> listUserTables() throws SQLException {
		Connection conn = jdbc.getDataSource().getConnection();
		// ResultSet rs = conn.getMetaData().getTables(null, "%", "%", new String[] { "TABLE" });
		ResultSet rs = conn.getMetaData().getTables(null, null, null, new String[] { "TABLE" });
		List<UserTable> tableList = new ArrayList<UserTable>();
		while (rs.next()) {
			UserTable table = new UserTable();
			String tableName = rs.getString("TABLE_NAME");
			// String comment = rs.getString("REMARKS");
			String comment = getTableComment(jdbc, tableName);
			table.setTableName(tableName);
			table.setTableType(rs.getString("TABLE_TYPE"));
			table.setComments(comment);
			tableList.add(table);
		}
		rs.close();
		conn.close();
		return tableList;
	}

	@Override
	public Map<String, List<Column>> listColumns() throws SQLException {
		Connection conn = jdbc.getDataSource().getConnection();
		ResultSet rs = conn.getMetaData().getColumns(null, "%", "%", "%");
		List<Column> columnList = listColumns(rs);
		rs.close();
		conn.close();

		Map<String, List<Column>> columnMap = new LinkedHashMap<>();
		for (Column column : columnList) {
			String tableName = column.getTableName();
			List<Column> tableColumnList = columnMap.get(tableName);
			if (tableColumnList == null) {
				tableColumnList = new ArrayList<>();
				columnMap.put(tableName, tableColumnList);
			}
			tableColumnList.add(column);
		}
		return columnMap;
	}

	@Override
	public List<Column> listColumns(String tableName) throws SQLException {
		Connection conn = jdbc.getDataSource().getConnection();
		ResultSet rs = conn.getMetaData().getColumns(null, "%", tableName, "%");
		List<Column> columnList = listColumns(rs);
		rs.close();
		conn.close();
		return columnList;
	}

	public List<Column> listColumns(ResultSet rs) throws SQLException {
		List<Column> columnList = new ArrayList<Column>();
		while (rs.next()) {
			String columnName = rs.getString("COLUMN_NAME");// 列名称
			String typeName = rs.getString("TYPE_NAME");// 数据源依赖的类型名称，对于 UDT，该类型名称是完全限定的
			String comment = rs.getString("REMARKS");// 描述列的注释（可为 null）
			String defaultValue = rs.getString("COLUMN_DEF");// 该列的默认值，当值在单引号内时应被解释为一个字符串（可为 null）
			boolean autoincrement = "YES".equalsIgnoreCase(rs.getString("IS_AUTOINCREMENT"));// 指示此列是否自动增加 YES --- 如果该列自动增加 ,NO --- 如果该列不自动增加
			int columnSize = rs.getInt("COLUMN_SIZE");// 列的大小
			int decimalDigits = rs.getInt("DECIMAL_DIGITS");// 小数部分的位数。对于 DECIMAL_DIGITS 不适用的数据类型，则返回 Null。
			int nullable = rs.getInt("NULLABLE");// 是否允许使用 NULL。

			// System.err.println("comment:" + comment);
			Column column = new Column();
			column.setTableName(rs.getString("TABLE_NAME"));
			column.setColumnName(columnName);
			// column.setTypeName(typeName);
			column.setDataLength(columnSize);
			column.setDataType(typeName);
			// column.setDecimalDigits(decimalDigits);
			column.setNullable(nullable == 1 ? "Y" : "N");
			column.setComments(comment);
			// column.setAutoincrement(autoincrement);
			// column.setDefaultValue(defaultValue);
			// System.out.println(Json.toJson(column));
			columnList.add(column);
		}
		return columnList;
	}

	protected String getTableComment(Jdbc jdbc, String tableName) {
		String tableSql = this.showCreateTable(jdbc, tableName);
		String comment = parseTableComment(tableSql);
		// System.out.println("getTableComment table:" + tableName + " comment:" + comment + " tableSql:" + tableSql);
		return comment;
	}

	private String showCreateTable(Jdbc jdbc, String tableName) {
		List<Map<String, Object>> list = jdbc.queryForMaps("show create table " + tableName);
		for (Map<String, Object> map : list) {
			String sql = (String) map.get("Create Table");
			return sql;
		}
		return null;
	}

	protected static String parseTableComment(String tableSql) {
		String comment = null;
		int index = tableSql.indexOf("COMMENT='");
		if (index < 0) {
			return null;
		}
		comment = tableSql.substring(index + 9);
		comment = comment.substring(0, comment.length() - 1);
		return comment;
	}
}
