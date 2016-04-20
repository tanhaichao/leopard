package io.leopard.monitor.model;

/**
 * 报警配置.
 * 
 * @author 阿海
 * 
 */
public class AlarmInfo {

	private boolean sms;
	private Boolean windows;

	public boolean isSms() {
		return sms;
	}

	public void setSms(boolean sms) {
		this.sms = sms;
	}

	public Boolean getWindows() {
		return windows;
	}

	public void setWindows(Boolean windows) {
		this.windows = windows;
	}

}
