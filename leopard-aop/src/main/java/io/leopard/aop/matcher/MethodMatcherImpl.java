package io.leopard.aop.matcher;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MethodMatcherImpl implements MethodMatcher {

	private List<MethodMatcher> methodMatcherList = new ArrayList<MethodMatcher>();

	public MethodMatcherImpl() {
		methodMatcherList.add(new MethodMatcherJavaImpl());
	}

	@Override
	public Boolean matche(Method method, String methodName) {
		for (MethodMatcher methodMatcher : methodMatcherList) {
			Boolean matche = methodMatcher.matche(method, methodName);
			if (matche != null) {
				return matche;
			}
		}
		return false;
	}

}
