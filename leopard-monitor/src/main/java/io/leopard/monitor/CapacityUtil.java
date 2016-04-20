package io.leopard.monitor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 容量计算.
 * 
 * @author ahai
 * 
 */
public class CapacityUtil {

	// /**
	// * 修改单位.
	// *
	// * @param size
	// * @param reference
	// * @return
	// */
	// public static long modifyUnit(long size, String reference) {
	// String regex = "([0-9]+)(K|M|G)";
	// Pattern p = Pattern.compile(regex);
	// Matcher m = p.matcher(reference);
	// if (!m.find()) {
	// throw new IllegalArgumentException("不合法的容量[" + reference + "].");
	// }
	// char unit = m.group(2).charAt(0);
	// switch (unit) {
	// case 'K':
	// return size / 1024;
	// case 'M':
	// return size / 1024 / 1024;
	// case 'G':
	// return size / 1024 / 1024 / 1024;
	// default:
	// throw new IllegalArgumentException("未知单位[" + reference + "].");
	// }
	// }

	public static long parse(String str) {
		if (StringUtils.isEmpty(str)) {
			return 0;
		}
		str = str.toUpperCase();
		String regex = "([0-9]+)(K|M|G)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		if (!m.find()) {
			throw new IllegalArgumentException("不合法的容量[" + str + "].");
		}

		long size = Long.parseLong(m.group(1));
		char unit = m.group(2).charAt(0);
		switch (unit) {
		case 'K':
			return size * 1024;
		case 'M':
			return size * 1024 * 1024;
		case 'G':
			return size * 1024 * 1024 * 1024;
		default:
			throw new IllegalArgumentException("未知单位[" + str + "].");
		}
	}

	/**
	 * 转换为兆.
	 * 
	 * @param size
	 * @return
	 */
	public static long toMega(long size) {
		return size / 1024 / 1024;
	}

	/**
	 * 转换成人类可读的单位.
	 * 
	 * @param size
	 * @return
	 */
	public static String human(long size) {
		long size2 = size;
		int count = 0;
		for (; size2 >= 1024;) {
			size2 = size2 / 1024;
			count++;
			if (count >= 2) {
				break;
			}
		}
		// System.out.println("size2:" + size2 + " count:" + count);

		switch (count) {
		case 0:
			return size2 + "B";
		case 1:
			return size2 + "K";
		case 2:
			return size2 + "M";
			// case 3:
			// return size2 + "G";
			// case 4:
			// return size2 + "T";
		default:
			throw new IllegalArgumentException("非法单位[" + count + "]");
		}

	}
}
