package io.leopard.web.mvc.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 枚举序列化
 * 
 * @author 谭海潮
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@JacksonAnnotationsInside
@JsonSerialize(using = OnumFieldSerializer.class)
public @interface OnumField {

	Class<?> value();
}
