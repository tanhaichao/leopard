package io.leopard.web.mvc;

/**
 * 返回值转换器
 * @author 谭海潮
 *
 */
public interface ResponseBodyReturnValueConverter {
	
	Object convert(Object data);

}
