package io.leopard.myjetty.webapp;

import java.util.List;

public class Directory {

	// private String projectId;
	// private String rootDir;
	// private String webModuleDir;
	private String war;
	private List<String> hostList;

	public String getWar() {
		return war;
	}

	public void setWar(String war) {
		this.war = war;
	}

	public List<String> getHostList() {
		return hostList;
	}

	public void setHostList(List<String> hostList) {
		this.hostList = hostList;
	}

}
