package io.leopard.data.kit.password;

import io.leopard.core.exception.forbidden.PasswordWrongException;

/**
 * 密码检验器.
 * 
 * @author 谭海潮
 *
 */
public interface PasswordVerifier {

	boolean verify(String username, String password, String salt, String dbEncryptedPassword);

	void check(String username, String password, String salt, String dbEncryptedPassword) throws PasswordWrongException;

	String makeToken(String dbEncryptedPassword);

}
