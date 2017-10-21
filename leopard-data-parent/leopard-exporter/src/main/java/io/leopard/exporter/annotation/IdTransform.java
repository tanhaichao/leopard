package io.leopard.exporter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ID变换
 * 
 * @author 谭海潮
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IdTransform {

	/**
	 * 表名
	 * 
	 * @return
	 */
	String value();
}
