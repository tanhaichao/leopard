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
 * 跳过LeopardFilter
 * @author 阿海
 *
 */
public @interface SkipFilter {

}
