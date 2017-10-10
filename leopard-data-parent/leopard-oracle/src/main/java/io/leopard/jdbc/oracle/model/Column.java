package io.leopard.jdbc.oracle.model;

/**
 * 列信息
 * 
 * @author 谭海潮
 *
 */
public class Column {
	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * 列名称
	 */
	private String columnName;

	/**
	 * 数据类型
	 */
	private String dataType;

	/**
	 * 数据长度
	 */
	private int dataLength;

	/**
	 * 注释
	 */
	private String comments;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public static class Comment {
		/**
		 * 表名
		 */
		private String tableName;

		/**
		 * 表类型
		 */
		private String columnName;

		/**
		 * 注释
		 */
		private String comments;

		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}

		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}

		public String getComments() {
			return comments;
		}

		public void setComments(String comments) {
			this.comments = comments;
		}

	}
}
