package io.leopard.exporter.schema;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.leopard.exporter.mysql.MysqlManager;
import io.leopard.exporter.mysql.MysqlManagerImpl;
import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.oracle.model.UserTable;

public class SchemaManagerMysqlImpl implements SchemaManager {

	private MysqlManager mysqlManager;

	public SchemaManagerMysqlImpl(Jdbc jdbc) {
		this.mysqlManager = new MysqlManagerImpl(jdbc);
	}

	public SchemaManagerMysqlImpl(MysqlManager mysqlManager) {
		this.mysqlManager = mysqlManager;
	}

	@Override
	public List<Table> listTables() throws SQLException {
		List<UserTable> userTableList = mysqlManager.listUserTables();
		Map<String, List<io.leopard.jdbc.oracle.model.Column>> columnMap = mysqlManager.listColumns();
		List<Table> tableList = new ArrayList<Table>();
		for (UserTable userTable : userTableList) {
			Table table = new Table();
			table.setTableName(userTable.getTableName());
			table.setTableType(userTable.getTableType());
			table.setComments(userTable.getComments());
			List<Column> columnList = toColumnList(columnMap.get(userTable.getTableName()));
			table.setColumnList(columnList);
			tableList.add(table);
		}
		return tableList;
	}

	protected List<Column> toColumnList(List<io.leopard.jdbc.oracle.model.Column> oracleColumnList) {
		List<Column> columnList = new ArrayList<Column>();
		if (oracleColumnList != null) {
			for (io.leopard.jdbc.oracle.model.Column oracleColumn : oracleColumnList) {
				Column column = new Column();
				column.setColumnName(oracleColumn.getColumnName());
				column.setComments(oracleColumn.getComments());
				column.setDataLength(oracleColumn.getDataLength());
				column.setDataType(oracleColumn.getDataType());
				column.setNullable("Y".equalsIgnoreCase(oracleColumn.getNullable()));
				columnList.add(column);
			}
		}
		return columnList;
	}

	@Override
	public Table get(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

}
