package io.leopard.test;

import java.io.IOException;

public class ModuleParserLeiImpl implements ModuleParserLei {

	private String folder;

	public ModuleParserLeiImpl() {
		String path = this.parsePath();
		this.folder = parseFolder(path);

	}

	protected String parsePath() {
		// URL url = ClassLoader.getSystemResource(".").toString();
		String path;
		try {
			// this.getClass().getClassLoader().getResources(".").nextElement().toString()
			// TODO 使用jar包可能会有问题
			path = this.getClass().getClassLoader().getResources(".").nextElement().toString();
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}// ClassLoaderUtil.getCurrentPath();
		System.err.println("path:" + path);
		path = path.replaceFirst("file:/[A-Z]:/", "/");
		path = path.replaceFirst("file:/", "/");
		int index = path.indexOf("/target/");
		if (index == -1) {
			throw new RuntimeException("非法classes目录[" + path + "].");
		}
		path = path.substring(0, index);
		return path;
	}

	protected String parseFolder(String path) {
		if (path.endsWith("/")) {
			throw new IllegalArgumentException("非法路径格式[" + path + "].");
		}
		int index = path.lastIndexOf("/");
		if (index == -1) {
			throw new IllegalArgumentException("非法路径格式[" + path + "].");
		}
		String folder = path.substring(index + 1);
		return folder;
	}

	@Override
	// /work/news/news/news-dao
	public String getModuleName() {
		String moduleName = parseModuleName(folder);
		int index = moduleName.lastIndexOf("-");
		if (index == -1) {
			throw new IllegalArgumentException("非法模块名称[" + moduleName + "].");
		}
		return moduleName.substring(index + 1);
	}

	protected String parseModuleName(String folder) {
		if (folder.endsWith("/")) {
			throw new IllegalArgumentException("非法路径格式[" + folder + "].");
		}
		return folder.substring(folder.lastIndexOf("/") + 1);
	}

	@Override
	public boolean isSingleModule() {
		String moduleName = this.parseModuleName(folder);
		int index = moduleName.lastIndexOf("-");
		return (index == -1);
	}

}
