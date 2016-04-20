package io.leopard.monitor.log;

public class LogInfo {

	private String filename;
	private String level;

	public LogInfo() {

	}

	public LogInfo(String filename, String level) {

		this.filename = filename;
		this.level = level;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}
