package io.leopard.data4j.env;

import java.util.ArrayList;
import java.util.List;

public class EnvLeiImpl implements EnvLei {

	private static EnvLei instance = new EnvLeiImpl();

	public static EnvLei getInstance() {
		return instance;
	}

	private static List<EnvLei> envLeiList = new ArrayList<EnvLei>();

	static {
		envLeiList.add(new EnvLeiWindowsImpl());
		envLeiList.add(new EnvLeiServerImpl());
		envLeiList.add(new EnvLeiMacImpl());
	}

	protected EnvLei getEnvLei() {
		for (EnvLei envLei : envLeiList) {
			if (envLei.isEnabled()) {
				return envLei;
			}
		}
		throw new RuntimeException("未知环境.");

		// if (envDaoYtestImpl.isEnabled()) {
		// // ytest
		// return envDaoYtestImpl;
		// }
		// else if (envDaoHudsonImpl.isEnabled()) {
		// // hudson
		// return envDaoHudsonImpl;
		// }
		// else if (envDaoPmdImpl.isEnabled()) {
		// // pmd
		// return envDaoPmdImpl;
		// }
		// else if (envDaoWindowsImpl.isEnabled()) {
		// // windows
		// return envDaoWindowsImpl;
		// }
		// else if (envDaoMacImpl.isEnabled()) {
		// // mac
		// return envDaoMacImpl;
		// }
		// else if (envDaoUbuntuImpl.isEnabled()) {
		// // ubuntu
		// return envDaoUbuntuImpl;
		// }
		// else if (envDaoServerImpl.isEnabled()) {
		// // 服务器
		// return envDaoServerImpl;
		// }
		// else {
		// throw new RuntimeException("未知环境.");
		// }
	}

	@Override
	public boolean isEnabled() {
		throw new UnsupportedOperationException("Not Implemented");
	}

	// @Override
	// public String getEnv() {
	// return this.getEnvLei().getEnv();
	// }

	@Override
	public String getRootDir() {
		EnvLei envLei = this.getEnvLei();
		String rootDir = envLei.getRootDir();
		return rootDir;
	}

	// @Override
	// public boolean isDevEnv() {
	// return this.getEnvLei().isDevEnv();
	// }

}
