package io.leopard.data.kit.password;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.leopard.core.exception.forbidden.PasswordWrongException;

public class Sha1PasswordVerifier implements PasswordVerifier {
	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public boolean verify(String username, String password, String salt, String dbEncryptedPassword) {
		String encryptedPassword;
		if (password.length() == 40) {
			encryptedPassword = password;
		}
		else {
			encryptedPassword = PasswordUtil.encryptPassword(password, salt);
		}
		boolean correctPassword = encryptedPassword.equalsIgnoreCase(dbEncryptedPassword);
		if (!correctPassword) {
			logger.info("username:" + username + " password:" + password + " salt:" + salt + " encryptedPassword:" + encryptedPassword + " dbPassword:" + dbEncryptedPassword);
		}
		return correctPassword;
	}

	@Override
	public void check(String username, String password, String salt, String dbPassword) throws PasswordWrongException {
		throw new NotImplementedException();
	}

	@Override
	public String makeToken(String dbEncryptedPassword) {
		throw new NotImplementedException();
	}

}
