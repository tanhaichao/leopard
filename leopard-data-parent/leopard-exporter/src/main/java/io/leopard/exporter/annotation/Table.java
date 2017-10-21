package io.leopard.exporter.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 表明
 * 
 * @author 谭海潮
 *
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Table {

	/**
	 * 新表名称
	 * 
	 * @return
	 */
	String value() default "";

	/**
	 * 旧表名称
	 * 
	 * @return
	 */
	String oldTable() default "";
}
