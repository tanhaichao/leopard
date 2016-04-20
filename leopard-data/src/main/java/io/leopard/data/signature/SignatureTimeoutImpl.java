package io.leopard.data.signature;

import io.leopard.burrow.util.DateTime;
import io.leopard.burrow.util.DateUtil;
import io.leopard.burrow.util.EncryptUtil;
import io.leopard.commons.utility.Base16;
import io.leopard.commons.utility.Base64;
import io.leopard.commons.utility.SystemUtil;
import io.leopard.core.exception.invalid.SignatureInvalidException;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 签名算法(超时实现)
 * 
 * @author 阿海
 * 
 */
public class SignatureTimeoutImpl implements Signature {
	protected static final String SPLIT = ",,";// 字段分隔符号
	private final int maxTime;// 20分钟
	protected final String publicKey;

	private boolean useBase16 = false;// 是否使用Base16

	public SignatureTimeoutImpl(String publicKey, int maxTime) {
		this(publicKey, maxTime, false);
	}

	public SignatureTimeoutImpl(String publicKey, int maxTime, boolean useBase16) {
		this.publicKey = publicKey;
		this.maxTime = maxTime;
		this.useBase16 = useBase16;
	}

	protected String baseEncode(String content) {
		if (useBase16) {
			return Base16.encode(content);
		}
		else {
			return Base64.encode(content);
		}
	}

	protected String baseDecode(String content) {
		if (useBase16) {
			return Base16.decode(content);
		}
		else {
			return Base64.decode(content);
		}
	}

	@Override
	public String encode(String user, Date posttime) {
		user = user.trim().toLowerCase();// 去掉用户名前后的空白字符并转成小写。
		String userinfo = user + SPLIT + DateUtil.getTime(posttime);
		// System.out.println("用户:" + user + " 时间:" + DateUtil.getTime(posttime));
		// System.out.println("sha1前的明文:" + userinfo);
		String sha1 = EncryptUtil.sha1(userinfo + publicKey); // 使用SHA1加密算法
		String content = userinfo + SPLIT + sha1;

		// System.out.println("bash64前的明文:" + content);
		// 将明文信息和密文信息一起使用base64编码
		String base64Encode = baseEncode(content);
		return base64Encode;
	}

	@Override
	public SignatureInfo decode(String encodeString) {
		// AssertUtil.assertNotEmpty(encodeString, "参数key不能为空.");
		if (StringUtils.isEmpty(encodeString)) {
			throw new SignatureInvalidException("参数key不能为空.");
		}
		String content = baseDecode(encodeString);
		String[] list = content.split(SPLIT);
		if (list.length != 3) {
			throw new SignatureInvalidException("解密出错[" + encodeString + "].");
		}
		String user = list[0];
		String posttime = list[1];
		String sha1 = list[2];
		return this.decodeValid(user, posttime, sha1);
	}

	protected SignatureInfo decodeValid(String user, String posttime, String sha1) {
		if (true) {
			String userinfo = user + SPLIT + posttime;
			String serverSha1 = EncryptUtil.sha1(userinfo + publicKey); // 使用SHA1加密算法
			if (!sha1.equalsIgnoreCase(serverSha1)) {
				throw new SignatureInvalidException("解密出错，参数sha1不正确.");
			}
		}

		long timestamp = DateTime.getTimestamp(posttime);
		int seconds = (int) ((SystemUtil.currentTimeMillis() - timestamp) / 1000l);
		// System.out.println("seconds:" + seconds + " timestamp:" + timestamp + " time:" + SystemUtil.currentTimeMillis());
		if (seconds > this.maxTime) {
			throw new SignatureInvalidException("解密出错，key已过期[posttime:" + posttime + "].");
		}

		SignatureInfo signatureInfo = new SignatureInfo();
		signatureInfo.setUser(user);
		signatureInfo.setPosttime(DateUtil.toDate(posttime));
		signatureInfo.setSha1(sha1);
		return signatureInfo;
	}

}
