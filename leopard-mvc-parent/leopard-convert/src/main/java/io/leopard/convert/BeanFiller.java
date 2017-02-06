package io.leopard.convert;

/**
 * Bean属性填充器
 * 
 * @author 谭海潮
 *
 */
public interface BeanFiller {

	boolean supports(Class<?> sourceClass, Class<?> targetClass);

	void fill(Object source, Object target);
}
