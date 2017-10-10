package io.leopard.exporter.schema;

/**
 * 列信息
 * 
 * @author 谭海潮
 *
 */
public class Column {

	/**
	 * 列名称
	 */
	private String columnName;

	/**
	 * 数据类型
	 */
	private String dataType;

	/**
	 * 是否允许null
	 */
	private boolean nullable;

	/**
	 * 数据长度
	 */
	private int dataLength;

	/**
	 * 注释
	 */
	private String comments;

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

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

}
