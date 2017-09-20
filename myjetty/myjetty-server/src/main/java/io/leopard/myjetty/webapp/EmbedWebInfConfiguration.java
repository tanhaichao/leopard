package io.leopard.myjetty.webapp;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;

import io.leopard.jetty.handler.ResourceLoaderImpl;

public class EmbedWebInfConfiguration extends WebInfConfiguration {

	protected List<String> hostList;

	private String war;

	public EmbedWebInfConfiguration(List<String> hostList, String war) {
		this.hostList = hostList;
		this.war = war;
	}

	@Override
	protected List<Resource> findJars(WebAppContext context) throws Exception {
		List<Resource> list = super.findJars(context);
		if (list == null) {
			list = new ArrayList<Resource>();
		}
		if (true) {
			List<Resource> extendResourceList = new ResourceLoaderImpl().findJars(context);
			System.err.println("EmbedWebInfConfiguration findJars:" + extendResourceList);
			if (extendResourceList != null) {
				list.addAll(extendResourceList);
			}
		}
		return list;
	}

	@Override
	public void preConfigure(WebAppContext context) throws Exception {
		System.err.println("EmbedWebInfConfiguration preConfigure...");
		super.preConfigure(context);
		// ClassPathService classPathService = new ClassPathServiceImpl();
		// classPathService.preConfigure(context, hostList, war);
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
