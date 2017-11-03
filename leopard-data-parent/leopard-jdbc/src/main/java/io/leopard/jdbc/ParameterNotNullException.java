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
		super("参数值[" + parameterIndex + "]不能为NULL.");
		this.parameterIndex = parameterIndex;
	}

	public int getParameterIndex() {
		return parameterIndex;
	}

}
