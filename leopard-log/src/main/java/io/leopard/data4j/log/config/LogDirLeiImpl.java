package io.leopard.data4j.log.config;

import org.apache.commons.lang3.SystemUtils;

public class LogDirLeiImpl implements LogDirLei {

	
	@Override
	public String getDir() {
		// TODO ubuntu桌面环境判断
		if (SystemUtils.IS_OS_MAC) {
			return System.getProperty("user.home") + "/log/resin";
		}

		return "/log/resin";
	}

	private static LogDirLei logDirLei = new LogDirLeiImpl();

	public static String getLogDir() {
		return logDirLei.getDir();
	}
}
