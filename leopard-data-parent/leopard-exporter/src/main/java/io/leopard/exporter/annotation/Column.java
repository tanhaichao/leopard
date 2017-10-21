package io.leopard.exporter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.leopard.exporter.ColumnTransverter;

/**
 * 列信息
 * 
 * @author 谭海潮
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

	// String value();

	/**
	 * 别名(旧表字段名称)
	 * 
	 * @return
	 */
	String alias() default "";

	/**
	 * 注释
	 * 
	 * @return
	 */
	String comment() default "";

	/**
	 * 列值转换器
	 * 
	 * @return
	 */
	Class<? extends ColumnTransverter> transverter() default ColumnTransverter.None.class;
}
