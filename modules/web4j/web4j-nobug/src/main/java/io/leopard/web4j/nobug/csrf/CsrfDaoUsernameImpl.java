package io.leopard.web4j.nobug.csrf;

import io.leopard.burrow.util.EncryptUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CsrfDaoUsernameImpl implements CsrfDao {
	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public void checkToken(String username, String token) {
		String[] list = token.split("-");
		if (list.length != 3) {
			throw new CsrfTokenInvalidException("非法token[" + token + "].");
		}

		String prefix = list[0];
		long time = Long.parseLong(list[2]);
		String sha1 = EncryptUtil.sha1(time + " " + username + " " + CsrfServiceImpl.publicKey); // 使用SHA1加密算法
		if (!sha1.startsWith(prefix)) {
			// + prefix + "," + sha1 + ","
			logger.error("prefix:" + prefix + " username:" + username + " time:" + time + " sha1:" + sha1 + " token:" + token);
			throw new CsrfTokenInvalidException("token[" + token + "]不正确.");
		}
	}

}
