package io.leopard.web4j.admin;

import io.leopard.burrow.lang.LeopardValidUtil;

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
		return !LeopardValidUtil.isValidIp(ip);
	}

	// public void addIpBlock(String ipBlock) {
	// allowAdminIpBlockList.add(ipBlock);
	// }

	public boolean isAllowAdminIp(String ip) {
		for (String ipBlock : allowAdminIpBlockList) {
			if (ip.startsWith(ipBlock)) {
				return true;
			}
		}
		return allowAdminIpList.contains(ip);
	}
}
