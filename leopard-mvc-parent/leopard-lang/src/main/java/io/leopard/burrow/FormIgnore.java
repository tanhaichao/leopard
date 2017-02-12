package io.leopard.burrow;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Form模型时忽略属性.
 * 
 * @author 谭海潮
 *
 */
@Target({ TYPE })
@Retention(RUNTIME)
public @interface FormIgnore {

}
