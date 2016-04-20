package io.leopard.data4j.env;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.SystemUtils;
import org.springframework.util.StringUtils;

/**
 * hudson机器环境.
 * 
 * @author 阿海
 * 
 */
public class EnvLeiHudsonImpl implements EnvLei {

	@Override
	public boolean isEnabled() {
		if (!SystemUtils.IS_OS_LINUX) {
			return false;
		}
		String dir = "/data/jenkins/";
		File file = new File(dir);
		return file.exists();
	}

	// @Override
	// public String getEnv() {
	// return EnvUtil.ENV_DEV;
	// }

	@Override
	public String getRootDir() {
		// URL url = ClassLoader.getSystemResource("");
		// String path = url.toString();

		String path = ClassLoaderUtil.getCurrentPath();
		// String path =
		// "/data/hudson_home/jobs/news/workspace/news-data/target/generated-classes/cobertura/applicationContext.xml";

		String rootDir = this.parseRootDir(path);
		if (StringUtils.isEmpty(rootDir)) {
			RuntimeException e = new RuntimeException("不是合法的hudson路径[" + path + "].");
			e.printStackTrace();
			throw e;
		}
		return rootDir;
	}

	protected String parseRootDir(String path) {
		String regex = "/data/jenkins/jobs/([^/]+)/workspace";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(path);
		if (m.find()) {
			return m.group();
		}
		else {
			return parseRootDir2(path);
		}
	}

	protected String parseRootDir2(String path) {
		String regex = "/data/jenkins/workspace/([^/]+)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(path);
		if (m.find()) {
			return m.group();
		}
		else {
			return null;
		}
	}

	// @Override
	// public boolean isDevEnv() {
	// return true;
	// }

}
