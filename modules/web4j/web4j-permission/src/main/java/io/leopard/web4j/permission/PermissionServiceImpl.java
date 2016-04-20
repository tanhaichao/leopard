package io.leopard.web4j.permission;

import io.leopard.burrow.lang.ContextImpl;
import io.leopard.burrow.util.ObjectUtil;
import io.leopard.core.exception.ForbiddenException;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl extends ContextImpl implements PermissionService {

	@Autowired
	private PermissionDao permissionDaoCacheImpl;

	// @Autowired
	// private ConfigHandler loginHandler;

	@Override
	public void checkPermission(String requestUri, String proxyIp) throws ForbiddenException {
		if ("127.0.0.1".equals(proxyIp)) {
			// 本机IP不做判断.
			return;
		}
		boolean hasPermission = this.hasPermission(requestUri, proxyIp);
		if (!hasPermission) {
			throw new ForbiddenException("无权访问[" + proxyIp + "][" + requestUri + "].");
		}

	}

	@Override
	public boolean hasPermission(String uri, String ip) {
		Permission permission = permissionDaoCacheImpl.get(new PermissionKey(uri, ip));
		if (permission == null) {
			logger.error("uri:" + uri + " ip:" + ip);
		}
		return ObjectUtil.isNotNull(permission);
	}

	@Override
	public boolean isMonitorServer(String proxyIp) {
		// return this.loginHandler.isMonitorServer(proxyIp);
		// FIXME ahai 代码已被注释
		throw new NotImplementedException();
	}

	@Override
	public void checkMonitorServer(String proxyIp) throws ForbiddenException {
		if (!isMonitorServer(proxyIp)) {
			throw new ForbiddenException("不是拨测系统服务器IP[" + proxyIp + "].");
		}
	}
}
