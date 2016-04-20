package io.leopard.aop.matcher;

/**
 * Bean匹配器
 * 
 * @author 阿海
 *
 */
public interface BeanMatcher {
	Boolean matche(Object bean, String beanName, String className);
}