package io.leopard.jdbc.oracle.model;

public class UserTable {

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

	public static class Comment {
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

		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}

		public String getTableType() {
			return tableType;
		}

		public void setTableType(String tableType) {
			this.tableType = tableType;
		}

		public String getComments() {
			return comments;
		}

		public void setComments(String comments) {
			this.comments = comments;
		}

	}

}
