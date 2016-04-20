package io.leopard.web4j.allow;

import io.leopard.web4j.allow.dao.Allow;
import io.leopard.web4j.allow.dao.AllowDao;
import io.leopard.web4j.allow.dao.AllowDaoImpl;

public class AllowServiceImpl implements AllowService {

	private AllowDao allowDao = new AllowDaoImpl();

	@Override
	public boolean isAllow(String requestUri, String proxyIp) {
		Allow allow = new Allow();
		allow.setUri(requestUri);
		allow.setIp(proxyIp);
		Boolean exists = allowDao.exist(allow);
		if (exists == null) {
			return false;
		}
		return exists;
	}

	@Override
	public void checkAllow(String requestUri, String proxyIp) throws DeniedException {
		if ("127.0.0.1".equals(proxyIp)) {
			// 本机IP不做判断.
			return;
		}
		boolean hasPermission = this.isAllow(requestUri, proxyIp);
		if (!hasPermission) {
			throw new DeniedException("无权访问[" + proxyIp + "][" + requestUri + "].");
		}
	}

}
