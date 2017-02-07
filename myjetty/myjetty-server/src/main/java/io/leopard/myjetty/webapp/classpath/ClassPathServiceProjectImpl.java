package io.leopard.myjetty.webapp.classpath;

import java.io.File;
import java.util.List;

import org.eclipse.jetty.webapp.WebAppContext;

public class ClassPathServiceProjectImpl extends AbstractClassPathService {

	@Override
	public void preConfigure(WebAppContext context, List<String> hostList, String war) {
		// String war = "/work/feedback/feedback/feedback-web/target/feedback-web/";
		String contextWar = context.getWar();
		String projectName;
		String rootPath;
		{
			// /target
			int index = contextWar.lastIndexOf("/target");
			int index2 = contextWar.lastIndexOf("/", index - 1);

			rootPath = contextWar.substring(0, index2);
			projectName = rootPath.substring(rootPath.lastIndexOf("/") + 1);
			System.err.println("projectName:" + projectName + " rootPath:" + rootPath);
		}
		File dir = new File(rootPath);
		if (!dir.exists()) {
			return;
		}
		File[] files = dir.listFiles();
		for (File file : files) {
			String name = file.getName();
			if (!(file.isDirectory() && name.startsWith(projectName + "-"))) {
				continue;
			}
			String path = rootPath + "/" + name + "/target/classes/";
			this.addClassPath(context, newResource(path));
			// System.err.println("name:" + name + " path:" + path);
		}
	}

}
