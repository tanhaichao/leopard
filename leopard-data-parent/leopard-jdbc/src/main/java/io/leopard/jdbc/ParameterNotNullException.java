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

	public ParameterNotNullException(int parameterIndex) {
		this("参数值[" + parameterIndex + "]不能为NULL.", parameterIndex);
	}

	public ParameterNotNullException(String message, int parameterIndex) {
		super(message);
		this.parameterIndex = parameterIndex;

	}

	public int getParameterIndex() {
		return parameterIndex;
	}

}
