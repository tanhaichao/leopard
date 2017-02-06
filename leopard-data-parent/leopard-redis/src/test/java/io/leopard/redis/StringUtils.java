package io.leopard.redis;

import java.util.Collection;

public class StringUtils {

	public static String join(Collection<?> list, String split) {
		StringBuilder sb = new StringBuilder();
		int count = 0;
		for (Object element : list) {
			if (count > 0) {
				sb.append(split);
			}
			sb.append(element);
			count++;
		}
		return sb.toString();
	}
}
