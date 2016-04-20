package io.leopard.web4j.nobug.csrf;

import io.leopard.burrow.util.EncryptUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CsrfDaoYyuidImpl implements CsrfDao {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public void checkToken(String yyuid, String token) {
		String[] list = token.split("-");
		if (list.length != 3) {
			throw new CsrfTokenInvalidException("非法token[" + token + "].");
		}

		String prefix = list[1];
		long time = Long.parseLong(list[2]);
		String sha1 = EncryptUtil.sha1(time + " " + yyuid + " " + CsrfServiceImpl.publicKey); // 使用SHA1加密算法
		if (!sha1.startsWith(prefix)) {
			// + prefix + "," + sha1 + ","
			logger.error("prefix:" + prefix + " yyuid:" + yyuid + " time:" + time + " sha1:" + sha1 + " token:" + token);
			throw new CsrfTokenInvalidException("token[" + token + "]不正确.");
		}
	}

}
