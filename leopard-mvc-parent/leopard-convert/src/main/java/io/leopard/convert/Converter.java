package io.leopard.convert;

/**
 * 模型转换器
 * 
 * @author 谭海潮
 *
 */
public interface Converter<S, T> {

	T get(S source);
}
