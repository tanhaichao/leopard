package io.leopard.web4j.nobug.csrf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TokenVerifier {
	protected Log logger = LogFactory.getLog(this.getClass());

	private boolean onlyLog = true;// 只记录日志

	public void setOnlyLog(boolean onlyLog) {
		this.onlyLog = onlyLog;
	}

	private CsrfDao csrfDao;

	public TokenVerifier(CsrfDao csrfDao) {
		this.csrfDao = csrfDao;
	}

	public void verify(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getParameter("csrf-token");
		if (token == null || token.length() == 0 || "null".equals(token)) {
			String proxyIp = CsrfRequestUtil.getProxyIp(request);
			logger.debug("checkToken ip:" + proxyIp + " token为什么会为空?");
			// throw new CsrfTokenInvalidException("token不能为空.");
			return;
		}

		if (onlyLog) {
			try {
				checkToken(request, response, token);
			}
			catch (CsrfTokenInvalidException e) {
				// ErrorUtil.error(logger, "csrf token error:" + e.getMessage(), e);
				logger.error(e.getMessage(), e);
			}
		}
		else {
			checkToken(request, response, token);
		}

	}

	protected void checkToken(HttpServletRequest request, HttpServletResponse response, String token) {
		String account = this.csrfDao.getAccount(request);
		// long sessYyuid =
		// Long.parseLong(sessYyuidPageParameter.getValue(request, response));

		String[] list = token.split("-");
		if (list.length != 3) {
			throw new CsrfTokenInvalidException("非法token[" + token + "].");
		}

		String prefix = list[1];
		long time = Long.parseLong(list[2]);
		String sha1 = csrfDao.encrypt(time + " " + account + " " + CsrfServiceImpl.publicKey); // 使用SHA1加密算法
		if (!sha1.startsWith(prefix)) {
			// + prefix + "," + sha1 + ","
			logger.error("prefix:" + prefix + " account:" + account + " time:" + time + " sha1:" + sha1 + " token:" + token);
			throw new CsrfTokenInvalidException("token[" + token + "]不正确.");
		}
	}
}
