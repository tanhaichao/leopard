package io.leopard.data4j.env;

import org.apache.commons.lang.SystemUtils;

/**
 * windows开发环境
 * 
 * @author 阿海
 * 
 */
public class EnvLeiWindowsImpl implements EnvLei {

	@Override
	public boolean isEnabled() {
		return SystemUtils.IS_OS_WINDOWS;
	}

	// @Override
	// public String getEnv() {
	// return EnvUtil.ENV_DEV;
	// }

	protected String getPath() {
		String path = ClassLoaderUtil.getCurrentPath();
		if (path == null) {
			String osName = System.getProperty("os.name");
			boolean isWindows = SystemUtils.IS_OS_WINDOWS;
			throw new NullPointerException("怎么会拿不到classpath[isWindows:" + isWindows + ",osName:" + osName + "]?");
		}
		// System.err.println("url:" + url.toString());
		return path;
	}

	@Override
	public String getRootDir() {

		String path;
		try {
			path = this.getPath();
		}
		catch (NullPointerException e) {
			throw e;
		}
		path = path.replaceFirst("file:/[A-Z]:/", "/");
		path = path.replaceFirst("file:/", "/");
		int index = path.indexOf("/target/");
		if (index == -1) {
			throw new RuntimeException("非法classes目录[" + path + "].");
		}
		path = path.substring(0, index);
		// System.err.println("pathpath:" + path);
		// if (true) {
		// throw new NullPointerException("").");
		// }
		path = parseRootDir(path);
		return path;
	}

	// /work/news/news/news-dao
	protected String parseRootDir(String path) {
		String moduleName = EnvUtil.getModuleName(path);
		if (moduleName.indexOf("-") == -1) {
			return path;
		}
		int index = path.lastIndexOf("/");
		if (index == -1) {
			throw new IllegalArgumentException("非法路径格式[" + path + "].");
		}
		String rootDir = path.substring(0, index);
		return rootDir;
	}

	// @Override
	// public boolean isDevEnv() {
	// return true;
	// }
}
