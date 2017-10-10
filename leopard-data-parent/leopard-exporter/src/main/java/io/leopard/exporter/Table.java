package io.leopard.exporter;

/**
 * 表明
 * 
 * @author 谭海潮
 *
 */
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
