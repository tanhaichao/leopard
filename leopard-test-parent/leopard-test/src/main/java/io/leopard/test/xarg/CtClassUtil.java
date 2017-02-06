package io.leopard.test.xarg;

import java.lang.reflect.Method;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

public class CtClassUtil {

	private static final ClassPool pool = ClassPool.getDefault();

	static {
		pool.insertClassPath(new ClassClassPath(CtClassUtil.class));
	}

	public static CtClass getClass(Class<?> clazz) {
		try {
			return pool.get(clazz.getName());
		}
		catch (NotFoundException e) {
			Throwable error = e.getCause();
			if (error instanceof RuntimeException) {
				throw (RuntimeException) error;
			}
			if (error instanceof Exception) {
				throw new RuntimeException(error.getMessage(), error);
			}
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static CtMethod getMethod(Class<?> clazz, Method method) throws NotFoundException {
		CtClass ctClass = CtClassUtil.getClass(clazz);
		Class<?>[] types = method.getParameterTypes();
		CtMethod ctMethod;
		if (types == null) {
			ctMethod = ctClass.getDeclaredMethod(method.getName());
		}
		else {
			CtClass[] params = new CtClass[types.length];
			for (int i = 0; i < params.length; i++) {
				params[i] = CtClassUtil.getClass(types[i]);
			}
			ctMethod = ctClass.getDeclaredMethod(method.getName(), params);
		}
		return ctMethod;
	}

	/**
	 * 获取方法的参数名称.
	 * 
	 * @param clazz
	 * @param method
	 * @return
	 */
	public static String[] getParameterNames(Method method) {
		// AssertUtil.assertNotNull(method, "参数method不能为空.");
		Class<?> clazz = method.getDeclaringClass();
		return getParameterNames(clazz, method);
	}

	public static String[] getParameterNames(Class<?> clazz, Method method) {

		CtMethod ctMethod;
		try {
			ctMethod = CtClassUtil.getMethod(clazz, method);
		}
		catch (NotFoundException e) {
			Throwable error = e.getCause();
			if (error instanceof RuntimeException) {
				throw (RuntimeException) error;
			}
			if (error instanceof Exception) {
				throw new RuntimeException(error.getMessage(), error);
			}
			throw new RuntimeException(e.getMessage(), e);
		}
		String[] names;
		try {
			names = getParameterNames(ctMethod);
		}
		catch (NotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		// System.err.println("getParameterNames methodName:" + method.toGenericString() + " names:" + StringUtils.join(names, ", "));
		return names;
	}

	/**
	 * 获取方法的参数名称.
	 * 
	 * @param ctMethod
	 * @return
	 * @throws NotFoundException
	 */
	public static String[] getParameterNames(CtMethod ctMethod) throws NotFoundException {
		MethodInfo methodInfo = ctMethod.getMethodInfo();
		CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
		// logger.info("methodInfo.getConstPool().getSize():");
		LocalVariableAttribute attribute = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
		// String[] names = new String[attribute.tableLength() - 1];
		String[] paramNames = new String[ctMethod.getParameterTypes().length];

		int pos = 0;
		if (true) {
			int size = attribute.tableLength();
			if (size > 0) {
				String[] names = new String[size - 1];
				for (int i = 0; i < names.length; i++) {
					names[i] = attribute.variableName(i);
					if ("this".equals(names[i])) {
						pos = i + 1;
						break;
					}
				}
				// logger.info(methodInfo.getName() + " pos:" + pos + " allNames:" + StringUtils.join(names, ", "));
			}
		}

		// logger.info(methodInfo.getName() + " pos:" + pos);
		for (int i = 0; i < paramNames.length; i++) {
			// paramNames[i] = attribute.variableName(i + 1);
			try {
				paramNames[i] = attribute.variableName(i + pos);
				// logger.info("paramNames[" + i + "]:" + paramNames[i]);
			}
			catch (RuntimeException e) {
				throw e;
			}
		}
		// System.err.println("paramNames:" + StringUtils.join(paramNames));
		return paramNames;
	}

}
