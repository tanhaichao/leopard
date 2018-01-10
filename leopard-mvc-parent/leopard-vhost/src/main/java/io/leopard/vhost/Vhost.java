package io.leopard.vhost;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 虚拟主机
 * 
 * @author 谭海潮
 *
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Vhost {

	/**
	 * 域名
	 * 
	 * @return
	 */
	String[] value() default {};

}
