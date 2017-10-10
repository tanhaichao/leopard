package io.leopard.exporter.schema;

import java.util.List;

public class Table {

	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * 表类型
	 */
	private String tableType;

	/**
	 * 注释
	 */
	private String comments;

	private List<Column> columnList;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public List<Column> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
	}

}
