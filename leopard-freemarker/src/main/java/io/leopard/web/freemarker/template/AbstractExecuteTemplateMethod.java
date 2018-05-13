package io.leopard.web.freemarker.template;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import freemarker.ext.beans.StringModel;
import io.leopard.web.freemarker.template.AbstractTemplateMethod;

public abstract class AbstractExecuteTemplateMethod extends AbstractTemplateMethod {

	@Override
	public Object execute(HttpServletRequest request, Object... args) throws Exception {
		Method method = this.findExecuteMethod();
		if (method == null) {
			throw new RuntimeException("找不到execute方法.");
		}
		Class<?>[] types = method.getParameterTypes();
		Object[] methodArgs = new Object[types.length];
		for (int i = 0; i < types.length; i++) {
			if (types[i].equals(String.class)) {
				methodArgs[i] = args[i];
			}
			else {
				methodArgs[i] = ((StringModel) args[i]).getWrappedObject();
			}
		}
		try {
			return method.invoke(this, methodArgs);
		}
		catch (Exception e) {
			logger.error("url:" + request.getRequestURL().toString());
			throw e;
		}
	}

	protected Method findExecuteMethod() {
		Method[] methods = this.getClass().getMethods();
		for (Method method : methods) {
			if ("execute".equals(method.getName())) {
				return method;
			}
		}
		return null;
	}

}
