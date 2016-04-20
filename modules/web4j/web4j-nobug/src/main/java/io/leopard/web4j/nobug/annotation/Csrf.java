package io.leopard.web4j.nobug.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * CSRF&JSON劫持漏洞防范.
 *
 * @author 阿海
 *
 */
public @interface Csrf {

	
	boolean enable() default true;

	/**
	 * 当enable==false时，JsonView则输出JS代码检查当前页面哪些hostname允许执行callback函数?
	 * 
	 * @return
	 */
	String[] allowDomain() default {};

	String[] ajaxDomain() default {};

}
