package io.leopard.myjetty.webapp;

import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;

import io.leopard.myjetty.webapp.classpath.ClassPathService;
import io.leopard.myjetty.webapp.classpath.ClassPathServiceImpl;

public class EmbedWebInfConfiguration extends WebInfConfiguration {

	private List<String> hostList;

	private String war;

	public EmbedWebInfConfiguration(List<String> hostList, String war) {
		this.hostList = hostList;
		this.war = war;
	}

	@Override
	public void preConfigure(WebAppContext context) throws Exception {
		ClassPathService classPathService = new ClassPathServiceImpl();
		classPathService.preConfigure(context, hostList, war);

		super.preConfigure(context);
	}

	@Override
	public void configure(WebAppContext context) throws Exception {
		super.configure(context);
		initJvmDns(context);

		try {
			this.initNiucloud(context);
		}
		catch (Exception e) {

		}
	}

	// @Override
	// public void postConfigure(WebAppContext context) throws Exception {
	// new Exception("postConfigure").printStackTrace();
	// super.postConfigure(context);
	// }

	protected void initJvmDns(WebAppContext context) {
		String className = "io.leopard.javahost.AutoUnitRunnable";
		try {
			Runnable runnable = (Runnable) ((WebAppClassLoader) context.getClassLoader()).loadClass(className).newInstance();
			runnable.run();
		}
		catch (Exception e) {
			// e.printStackTrace();
		}
	}

	protected void initNiucloud(WebAppContext context) {
		String filename = war.replace("\\", "/");
		Pattern p = Pattern.compile("/data/src/(.*?)/");
		Matcher m = p.matcher(filename);
		if (!m.find()) {
			throw new RuntimeException("解析projectId出错[" + war + "].");
		}
		String projectId = m.group(1);

		String className = "io.xiaoniu.niujetty.datasource.InitRunnable";
		try {
			Class<?> clazz = ((WebAppClassLoader) context.getClassLoader()).loadClass(className);
			Runnable runnable = (Runnable) clazz.newInstance();
			Method method = clazz.getDeclaredMethod("setProjectId", String.class);
			method.invoke(runnable, projectId);
			runnable.run();
		}
		catch (Exception e) {
			// e.printStackTrace();
		}
	}
}
