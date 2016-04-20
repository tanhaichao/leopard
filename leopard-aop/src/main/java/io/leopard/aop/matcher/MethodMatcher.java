package io.leopard.aop.matcher;

import java.lang.reflect.Method;

public interface MethodMatcher {

	Boolean matche(Method method, String methodName);
}
