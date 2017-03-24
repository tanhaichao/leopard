package io.leopard.json.serialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 属性详情信息
 * 
 * @author 谭海潮
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@JacksonAnnotationsInside
@JsonSerialize(using = FieldDetailJsonSerializer.class)
public @interface FieldDetail {

	/**
	 * 序列化类名
	 */
	String value();

}
