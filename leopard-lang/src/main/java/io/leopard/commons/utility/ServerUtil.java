package io.leopard.commons.utility;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServerUtil {// NOPMD
	private static final Log logger = LogFactory.getLog(ServerUtil.class);

	public ServerUtil() {

	}

	/**
	 * 获取eth0网卡IP地址.
	 * 
	 * @return
	 */
	public static String getServerIp() {
		return ServerIpUtil.getServerIp();
	}

	/**
	 * 根据域名获取指向的IP(无缓存，使用时请注意性能)
	 * 
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	public static String getIp(String host) {
		try {
			InetAddress inet = InetAddress.getByName(host);
			String ip = inet.getHostAddress();
			return ip;
		}
		catch (UnknownHostException e) {
			logger.info(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

}
