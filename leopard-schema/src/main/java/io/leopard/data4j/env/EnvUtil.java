package io.leopard.data4j.env;

import org.apache.commons.lang.StringUtils;

public class EnvUtil {
	public static final String ENV_PROD = "prod";
	public static final String ENV_BENCHMARK = "benchmark";
	public static final String ENV_INTEGRATION = "integration";
	public static final String ENV_DEV = "dev";

	public static final String NAME_PROJECTNO = "LPROJECTNO";
	public static final String NAME_ENV = "LENV";

	// /**
	// * 获取当前项目名称.
	// *
	// * @return
	// */
	// public static String getProjectCode() {
	// String project = getenv(NAME_PROJECTNO);
	// if (StringUtils.isEmpty(project)) {
	// project = new ProjectInfoDaoImpl().getCode();
	// }
	// return project;
	// }

	// public static void setProjectName(String projectName) {
	// System.setProperty(NAME_PROJECTNO, projectName);
	// }

	public static String getProjectCode() {
		String project = getenv(NAME_PROJECTNO);
		return project;
	}

	/**
	 * 获取当前运行环境(dev|test|prod)
	 * 
	 * @return
	 */
	public static String getEnv() {
		String env = getenv(NAME_ENV);
		if (StringUtils.isEmpty(env)) {
			return EnvUtil.ENV_DEV;
		}
		return env;
	}

	public static boolean isDevEnv() {
		String env = EnvUtil.getEnv();
		// AssertData.notEmpty(env, "未配置环境变量" + NAME_ENV + ".");
		if (env == null || env.length() == 0) {
			throw new IllegalArgumentException("未配置环境变量" + NAME_ENV + ".");
		}
		return EnvUtil.ENV_DEV.equalsIgnoreCase(env);
	}

	protected static String getenv(String name) {
		String value = System.getenv(name);
		if (StringUtils.isEmpty(value)) {
			value = System.getProperty(name);
		}
		return value;
	}

	public static String getModuleName(String path) {
		if (path.endsWith("/")) {
			throw new IllegalArgumentException("非法路径格式[" + path + "].");
		}
		int index = path.lastIndexOf("/");
		String moduleName = path.substring(index + 1);
		return moduleName;
	}
}
