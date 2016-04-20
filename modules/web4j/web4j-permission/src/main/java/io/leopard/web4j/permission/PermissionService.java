package io.leopard.web4j.permission;

import io.leopard.core.exception.ForbiddenException;

/**
 * 权限判断.
 * 
 * @author 阿海
 * 
 */
public interface PermissionService {

	/**
	 * 根据uri，ip判断是否有权限.
	 * 
	 * @param requestUri
	 * @param proxyIp
	 * @return
	 */
	boolean hasPermission(String requestUri, String proxyIp);

	/**
	 * 跟uri，ip检查是否有权限.
	 * 
	 * @param requestUri
	 * @param proxyIp
	 * @throws ForbiddenException
	 */
	void checkPermission(String requestUri, String proxyIp) throws ForbiddenException;

	/**
	 * 检查拨测服务器IP.
	 * 
	 * @param proxyIp
	 * @throws ForbiddenException
	 */
	void checkMonitorServer(String proxyIp) throws ForbiddenException;

	/**
	 * 是否拨测服务器IP.
	 * 
	 * @param proxyIp
	 * @return
	 */
	boolean isMonitorServer(String proxyIp);

}
