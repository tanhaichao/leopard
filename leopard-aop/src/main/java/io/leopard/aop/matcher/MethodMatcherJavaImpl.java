package io.leopard.aop.matcher;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class MethodMatcherJavaImpl implements MethodMatcher {

	private Set<String> methodSet = new HashSet<String>();

	public MethodMatcherJavaImpl() {
		methodSet.add("equals");
		methodSet.add("hashCode");
		methodSet.add("toString");
	}

	@Override
	public Boolean matche(Method method, String methodName) {
		return !methodSet.contains(methodName);
	}

}
