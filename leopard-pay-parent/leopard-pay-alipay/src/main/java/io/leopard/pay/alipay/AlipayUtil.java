package io.leopard.pay.alipay;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;

/**
 * 支付宝工具类
 * 
 * @author 谭海潮
 *
 */
public class AlipayUtil {

	public static String getReturnUrl(HttpServletRequest request, String uri) {
		boolean isHttps = "true".equals(request.getHeader("isHttps"));
		StringBuilder sb = new StringBuilder(48);
		int port = request.getServerPort();
		String scheme;
		if (isHttps) {
			scheme = "https";
			if (port == 80) {
				port = 443;
			}
		}
		else {
			scheme = "http";
		}

		sb.append(scheme);
		sb.append("://");
		sb.append(request.getServerName());
		if (port == 80 && "http".equals(scheme)) {
			//
		}
		else if (port == 443 && "https".equals(scheme)) {
			//
		}
		else {
			sb.append(':');
			sb.append(port);
		}
		sb.append(uri);
		return sb.toString();

	}

	/**
	 * 签名,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 */
	public static String signature(final SortedMap<String, String> sortedParams, final String privateKey) {
		Set<String> ignoredKeySet = new HashSet<String>();
		ignoredKeySet.add("sign");
		ignoredKeySet.add("sign_type");

		final String queryStr = buildQueryStr(sortedParams, ignoredKeySet);
		// 将key的值拼接在字符串后面，调用MD5算法生成signature, 注意这里和微信那个是不一样的, 这里直接把key 添加到查询字符串后面
		final String content = queryStr + privateKey;
		try {
			return md5Hex(content);
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 拼接查询字符串: k1=v1&k2=v2&..., 会排除掉值为null 的那些key
	 *
	 * @param formMap 键值对
	 * @param ignoredKeys 要忽略的key, 如果为null, 则全部都拼接到字符串
	 * @return 查询字符串
	 */
	public static String buildQueryStr(final Map<String, String> formMap, final Set<String> ignoredKeys) {
		final StringBuilder builder = new StringBuilder();
		for (Entry<String, String> entry : formMap.entrySet()) {
			final String key = entry.getKey();
			final String value = entry.getValue();
			if (value == null) {
				continue;
			}
			if (ignoredKeys.contains(key)) {
				continue;
			}
			builder.append(key).append('=').append(value.trim()).append('&');
		}
		if (builder.length() > 0) {
			builder.deleteCharAt(builder.length() - 1);
		}
		return builder.toString();
	}

	/**
	 * 获取字符串的MD5, 这个方法使用UTF-8 的编码获取byte, 最后会把MD5 字符串转成大写形式
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static String md5Hex(String msg) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		final byte[] bytes = msg.getBytes("UTF-8");
		final byte[] array = MessageDigest.getInstance("MD5").digest(bytes);
		final StringBuilder hex = new StringBuilder();
		for (final byte aByte : array) {
			hex.append(Integer.toHexString((aByte & 0xFF) | 0x100).substring(1, 3));
		}
		return hex.toString().toUpperCase();
	}

}
