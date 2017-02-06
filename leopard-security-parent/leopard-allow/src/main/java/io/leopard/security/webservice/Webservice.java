package io.leopard.security.webservice;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 将接口标注为Webservice接口.
 * 
 * 在Controller方法里使用
 * 
 * @author 谭海潮
 *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Webservice {

}
