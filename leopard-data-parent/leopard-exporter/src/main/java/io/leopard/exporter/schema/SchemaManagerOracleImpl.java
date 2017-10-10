package io.leopard.exporter.schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.oracle.OracleManager;
import io.leopard.jdbc.oracle.OracleManagerImpl;
import io.leopard.jdbc.oracle.model.UserTable;

public class SchemaManagerOracleImpl implements SchemaManager {

	private OracleManager oracleManager;

	public SchemaManagerOracleImpl(Jdbc jdbc) {
		this.oracleManager = new OracleManagerImpl(jdbc);
	}

	public SchemaManagerOracleImpl(OracleManager oracleManager) {
		this.oracleManager = oracleManager;
	}

	@Override
	public List<Table> listTables() {
		List<UserTable> userTableList = oracleManager.listUserTables();
		Map<String, List<io.leopard.jdbc.oracle.model.Column>> columnMap = oracleManager.listColumns();
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
