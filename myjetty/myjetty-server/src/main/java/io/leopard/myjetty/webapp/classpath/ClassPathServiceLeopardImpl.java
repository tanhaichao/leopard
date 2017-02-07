package io.leopard.myjetty.webapp.classpath;

import java.io.File;
import java.util.List;

import org.eclipse.jetty.webapp.WebAppContext;

public class ClassPathServiceLeopardImpl extends AbstractClassPathService {

	@Override
	public void preConfigure(WebAppContext context, List<String> hostList, String war) {
		// D:\work\leopard\leopard\leopard-commons\target\classes
		String rootPath = "/work/ytest/leopard/";
		File dir = new File(rootPath);
		if (!dir.exists()) {
			return;
		}
		File[] files = dir.listFiles();
		for (File file : files) {
			String name = file.getName();
			if (!(file.isDirectory() && name.startsWith("leopard-"))) {
				continue;
			}
			String path = rootPath + name + "/target/classes/";
			this.addClassPath(context, newResource(path));
			// System.err.println("name:" + name + " path:" + path);
		}
	}

}
