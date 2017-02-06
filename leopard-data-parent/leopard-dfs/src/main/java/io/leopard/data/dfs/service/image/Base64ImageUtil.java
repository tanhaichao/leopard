package io.leopard.data.dfs.service.image;

import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

public class Base64ImageUtil {

	public static byte[] decodeFromString(String str) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		if (!str.startsWith("data:image")) {
			return null;
		}
		if (str.startsWith("data:image/png;base64,")) {
			return Base64Utils.decodeFromString(str.substring(22));
		}
		else if (str.startsWith("data:image/jpeg;base64,")) {
			return Base64Utils.decodeFromString(str.substring(23));
		}
		else {
			throw new IllegalArgumentException("未知图片类型.");
		}
	}
}
