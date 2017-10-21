package io.leopard.exporter.exception;

/**
 * ID转换失败 
 * @author 谭海潮
 *
 */
public class IdTransformException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IdTransformException(String tableName, String id) {
		super("ID[" + tableName + "." + id + "]转换失败.");
	}

}
