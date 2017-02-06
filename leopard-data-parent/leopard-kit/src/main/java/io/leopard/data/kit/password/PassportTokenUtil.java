package io.leopard.data.kit.password;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.leopard.util.Base64;

public class PassportTokenUtil {
	protected static Log logger = LogFactory.getLog(PassportTokenUtil.class);

	public static String makeToken(String encryptedPassword) {
		if (encryptedPassword.length() != 40 && encryptedPassword.length() != 32) {
			throw new IllegalArgumentException("已加密的密码[" + encryptedPassword + "]为什么不是40位?");
		}
		String str = encryptedPassword + ":" + System.currentTimeMillis();
		return Base64.encode(str);
	}

	public static String getEncryptedPassword(String token) {
		try {
			String str = Base64.decode(token);
			String encryptedPassword = str.split(":")[0];
			return encryptedPassword;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
}
