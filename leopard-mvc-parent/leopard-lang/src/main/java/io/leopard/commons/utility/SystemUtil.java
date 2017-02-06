package io.leopard.commons.utility;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * SystemUtil(功能未确定).
 * <p>
 * 
 * @author 阿海
 * 
 */
public final class SystemUtil {
	private static final Log logger = LogFactory.getLog(SystemUtil.class);

	/**
	 * 执行shell命令</br>
	 * 
	 * @param cmd
	 *            需要执行的命令
	 * @return 输出执行后的结果
	 */
	public static String execShell2(final String cmd) {
		String msg = "";
		// cmd = "/bin/sh " + cmd;
		try {
			Process pro = Runtime.getRuntime().exec(cmd);
			if (true) {
				InputStream input = pro.getInputStream();
				msg = IOUtils.toString(input);
				input.close();
			}
			if (msg == null || msg.length() == 0) {
				if (true) {
					InputStream input = pro.getErrorStream();
					msg = IOUtils.toString(input);
					input.close();
				}
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return msg;
	}

	/**
	 * 执行Shell程序</br>
	 * 
	 * @param cmd
	 * @return 输出信息.
	 */
	public static String execShell(final String cmd) {
		return execShell(cmd, true);
	}

	/**
	 * 执行Shell命令</br>
	 * 
	 * @param cmd
	 *            Shell命令
	 * @param wait
	 *            是否输出结果
	 * @return 返回的结果
	 */
	public static String execShell(final String cmd, final boolean wait) {
		String msg = "";
		try {
			// cmd = "/bin/sh " + cmd;
			Process pro = Runtime.getRuntime().exec(cmd);
			if (wait) {
				InputStream input = pro.getErrorStream();
				msg = IOUtils.toString(input);
			}
		}
		catch (Exception e) {
			msg = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return msg;
	}

	public static String getStackMessage(final StackTraceElement[] stacks) {
		if (stacks == null) {
			return null;
		}
		int size = stacks.length;
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < size; i++) {
			StackTraceElement ste = stacks[i];
			if (i > 1) {
				sb.append("\t");
			}
			sb.append(ste.toString()).append("\n");
		}
		return sb.toString();
	}

	public static long currentTimeMillis() {
		return System.currentTimeMillis();
	}

	// public static String getenv(String name) {
	// return System.getenv(name);
	// }

	/**
	 * 休眠</br>
	 * 
	 * @param mills
	 *            休眠时间，单位毫秒
	 */
	public static void sleep(long mills) {
		try {
			Thread.sleep(mills);
		}
		catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 判断是否非Windows环境.
	 * 
	 * @return
	 */
	public static boolean isNotWindows() {
		return !SystemUtils.IS_OS_WINDOWS;
	}

	public static boolean isWindows() {
		return SystemUtils.IS_OS_WINDOWS;
	}

	public static boolean isLinux() {
		return SystemUtils.IS_OS_LINUX;
	}

	/**
	 * 判断是否非Linux环境.
	 * 
	 * @return
	 */
	public static boolean isNotLinux() {
		return !SystemUtils.IS_OS_LINUX;
	}
}
