package io.leopard.myjetty.maven.plugin;

public class Vhost {

	private String host;
	private String projectId;
	private String root;
	private String war;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getWar() {
		return war;
	}

	public void setWar(String war) {
		this.war = war;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	@Override
	public String toString() {
		return "Vhost [host=" + host + ", projectId=" + projectId + ", war=" + war + "]";
	}

}
