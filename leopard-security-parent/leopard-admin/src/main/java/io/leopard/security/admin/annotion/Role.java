package io.leopard.security.admin.annotion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 允许哪些角色访问接口.
 * 
 * 在Controller方法中使用.
 * 
 * @author 谭海潮
 *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Role {

	public static final String SUPER = "super";
	public static final String GENERAL = "general";

	String[] value() default {};
}
