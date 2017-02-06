package io.leopard.web.xparam;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用正则表达式解析URL作为Controller参数.
 * 
 * @author 阿海
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PathRegex {

	/**
	 * 正则表达式.如:"/([a-z]+)"
	 * 
	 * @return
	 */
	String value() default "";

}