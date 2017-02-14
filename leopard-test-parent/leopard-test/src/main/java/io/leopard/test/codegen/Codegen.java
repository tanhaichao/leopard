package io.leopard.test.codegen;

import java.lang.reflect.Method;

import io.leopard.jdbc.Jdbc;

/**
 * 代码生成接口.
 * 
 * @author 谭海潮
 *
 */
public class Codegen {

	public static void generate(Jdbc jdbc, Class<?> entity) throws Exception {
		Class<?> codegenClazz = findCodegenClass();
		Method method = codegenClazz.getMethod("generate", Jdbc.class, Class.class);
		method.invoke(null, jdbc, entity);
	}

	public static void setCheckUtilClazz(Class<?> clazz) throws Exception {
		Class<?> codegenClazz = findCodegenClass();
		Method method = codegenClazz.getMethod("setCheckUtilClazz", Class.class);
		method.invoke(null, clazz);
	}

	protected static Class<?> findCodegenClass() throws ClassNotFoundException {
		return Class.forName("io.leopard.codegen.AutoCodegen");
	}
}
