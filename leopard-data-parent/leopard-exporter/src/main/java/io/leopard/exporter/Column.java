package io.leopard.exporter;

/**
 * 列信息
 * 
 * @author 谭海潮
 *
 */
public @interface Column {

	// String value();

	String alias() default "";

	String comment() default "";

}
