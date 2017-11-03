package io.leopard.jdbc;

/**
 * SQL参数不能为null
 * 
 * @author 谭海潮
 *
 */
public class ParameterNotNullException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int parameterIndex;

	private String columnName;

	public ParameterNotNullException(int parameterIndex) {
		this("参数值[" + parameterIndex + "]不能为NULL.", parameterIndex, null);
	}

	public ParameterNotNullException(int parameterIndex, String columnName) {
		this("参数值[" + parameterIndex + "]不能为NULL.", parameterIndex, columnName);
	}

	public ParameterNotNullException(String message, int parameterIndex, String columnName) {
		super(message);
		this.parameterIndex = parameterIndex;
		this.columnName = columnName;
	}

	public int getParameterIndex() {
		return parameterIndex;
	}

	public String getColumnName() {
		return columnName;
	}

}
