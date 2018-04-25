package io.leopard.web.passport;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 通行证类别(如：用户、公司、店铺等)
 * 
 * @author 谭海潮
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PassportGroup {

	/**
	 * session属性名称
	 * 
	 * @return
	 */
	String value() default "sessUid";

	/**
	 * 是否存入session
	 */
	boolean session() default true;

}
