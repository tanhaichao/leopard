package io.leopard.burrow;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 子模块的Controller
 * 
 * @author 谭海潮
 *
 */
@Target({ ElementType.PACKAGE })
@Retention(RetentionPolicy.SOURCE)
public @interface ModuleController {

	Class<?> value();
}
