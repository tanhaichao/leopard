package io.leopard.data.kit.password;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;

import io.leopard.core.exception.forbidden.PasswordWrongException;
import io.leopard.util.EncryptUtil;

public class Md5PasswordVerifier implements PasswordVerifier {

	@Override
	public boolean verify(String username, String password, String salt, String dbEncryptedPassword) {
		String encryptedPassword;
		if (password.length() == 32) {
			encryptedPassword = password;
		}
		else {
			String str;
			if (StringUtils.isEmpty(salt)) {
				str = password;
			}
			else {
				str = password + "@" + salt;
			}
			encryptedPassword = EncryptUtil.md5(str).toLowerCase();
		}
		return encryptedPassword.equalsIgnoreCase(dbEncryptedPassword);
	}

	@Override
	public void check(String username, String password, String salt, String dbPassword) throws PasswordWrongException {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public String makeToken(String dbEncryptedPassword) {
		throw new NotImplementedException("not impl.");
	}

}
