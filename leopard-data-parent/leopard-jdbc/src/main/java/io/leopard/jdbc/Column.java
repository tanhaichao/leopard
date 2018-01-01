package io.leopard.jdbc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 列信息(只在开发环境使用)
 * 
 * @author 谭海潮
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

	/**
	 * 字段长度
	 * 
	 * @return
	 */
	int length() default 0;

	/**
	 * 是否text类型
	 */
	boolean text() default false;

	/**
	 * 是否HTML类型
	 */
	boolean html() default false;

	/**
	 * 字段名称
	 * 
	 * @return
	 */
	String name() default "";

}
