package io.leopard.web4j.allow;

public interface AllowService {

	/**
	 * 根据uri，ip判断是否有权限.
	 * 
	 * @param requestUri
	 * @param proxyIp
	 * @return
	 */
	boolean isAllow(String requestUri, String proxyIp);

	/**
	 * 跟uri，ip检查是否有权限.
	 * 
	 * @param requestUri
	 * @param proxyIp
	 * @throws DeniedException
	 */
	void checkAllow(String requestUri, String proxyIp) throws DeniedException;
}
