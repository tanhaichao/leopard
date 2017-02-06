package io.leopard.security.admin;

import java.util.ArrayList;
import java.util.List;

public class AdminAllowIp {
	private final List<String> allowAdminIpList = new ArrayList<String>();
	private final List<String> allowAdminIpBlockList = new ArrayList<String>();

	public void addIp(String ip) {
		boolean isIpBlock = this.isIpBlock(ip);
		if (isIpBlock) {
			allowAdminIpBlockList.add(ip);
		}
		else {
			allowAdminIpList.add(ip);
		}
	}

	protected boolean isIpBlock(String ip) {
		// return !LeopardValidUtil.isValidIp(ip);
		return true;
	}

	public boolean isAllowAdminIp(String ip) {
		for (String ipBlock : allowAdminIpBlockList) {
			if (ip.startsWith(ipBlock)) {
				return true;
			}
		}
		return allowAdminIpList.contains(ip);
	}
}
